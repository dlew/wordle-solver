package net.danlew.wordle

import net.danlew.wordle.algorithm.Solver

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
  val results = MutableList(maxGuesses + 1) { 0 }

  for (target in targets) {
    val numGuesses = runGame(target, wordList, targets, hardMode, maxGuesses)
    results[numGuesses]++
  }

  return results
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