package net.danlew.wordle.algorithm

import net.danlew.wordle.GuessResult

/**
 * Dumb solver that will often fail.
 */
object FirstWordSolver : Solver {

  override fun nextGuess(wordPool: List<String>, previousGuesses: List<GuessResult>): String {
    return wordPool[0]
  }

}