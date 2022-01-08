package net.danlew.wordle.algorithm

import net.danlew.wordle.*

/**
 * Solver based on Knuth's Mastermind algorithm
 */
class KnuthSolver(
  /**
   * The first guess to make lacking any previous guesses.
   *
   * If null, it's calculated from scratch (warning: takes hours).
   */
  private val firstGuess: String?
) : Solver {

  override fun nextGuess(wordPool: List<String>, targets: List<String>, previousGuesses: List<GuessResult>): String {
    if (previousGuesses.isEmpty() && firstGuess != null) {
      return firstGuess
    }

    // Filter out targets which aren't possible anymore
    var possibleTargets = targets
    previousGuesses.forEach { previousGuess ->
      possibleTargets = possibleTargets.filter { target -> previousGuess.couldMatch(target) }
    }

    // For each guess, calculate the score (minimum number of eliminated for each guess vs. each target)
    // Then take the one with the highest score possible
    val targetSize = possibleTargets.size

    // If there's only one possibility left, nail it!
    if (targetSize == 1) {
      return possibleTargets[0]
    }

    val guessRankings = wordPool.map { guess ->
      var score = Int.MAX_VALUE
      val noGuessDupes = guess.hasNoDuplicateCharacters()
      for (possibleTarget in possibleTargets) {
        val guessResult = GuessResult(guess, evaluateGuess(guess, possibleTarget))
        val numResults = possibleTargets.count { guessResult.couldMatch(it, noGuessDupes) }
        score = minOf(score, targetSize - numResults)
      }
      return@map GuessScore(guess, score)
    }.sortedByDescending { it.score }

    // Find the first word that's in possibleTargets that has the most eliminations (so we can bias
    // towards nailing the correct answer). Otherwise, pick the first non-target word.
    val maxScore = guessRankings.first().score
    val guesses = guessRankings.takeWhile { it.score == maxScore }
    return (guesses.find { it.guess in possibleTargets } ?: guesses.first()).guess
  }

  private fun String.hasNoDuplicateCharacters() = chars().distinct().count().toInt() == length

  private data class GuessScore(val guess: String, val score: Int)

}