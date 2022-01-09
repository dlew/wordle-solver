package net.danlew.wordle

import net.danlew.wordle.algorithm.Solver
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 * Runs every word in [wordList] as the target against a [Solver].
 *
 * @return a histogram of how many guesses it took for each word, where the 0th index are failures.
 */
fun Solver.runAllGames(
  wordList: List<String>,
  targets: List<String>,
  hardMode: Boolean,
  maxGuesses: Int = 6
): List<Int> {
  // Chunk up work and then run in parallel to reduce processing time
  val availableProcessors = Runtime.getRuntime().availableProcessors()

  val threadPool = Executors.newFixedThreadPool(availableProcessors)

  val tasks = targets.chunked(availableProcessors).map {
    Callable {
      val results = MutableList(maxGuesses + 1) { 0 }
      for (target in it) {
        val numGuesses = runGame(target, wordList, targets, hardMode, maxGuesses)
        results[numGuesses]++
      }
      return@Callable results
    }
  }

  val result = threadPool.invokeAll(tasks)
    .map { it.get() }
    .fold(List(maxGuesses + 1) { 0 }) { acc, ints ->
      acc.mapIndexed { index, i -> i + ints[index] }
    }

  threadPool.shutdown()

  return result
}

/**
 * Runs a game against a [Solver]
 *
 * @return the number of guesses to solve, or 0 if it did not solve it
 */
fun Solver.runGame(
  target: String,
  wordList: List<String>,
  targets: List<String>,
  hardMode: Boolean,
  maxGuesses: Int = 6
): Int {
  var wordPool = wordList
  var targetPool = targets
  val guessResults = mutableListOf<GuessResult>()
  for (round in 0 until maxGuesses) {
    val guess = nextGuess(wordPool, targetPool, guessResults)
    val hints = evaluateGuess(guess, target)

    if (hints.solved) {
      return round + 1
    }

    guessResults.add(GuessResult(guess, hints))

    // Narrow the word pool to all remaining valid guesses
    if (hardMode) {
      wordPool = filterValidHardModeGuesses(wordPool, guessResults.last())
      targetPool = filterValidHardModeGuesses(targetPool, guessResults.last())
    }
  }

  return 0
}