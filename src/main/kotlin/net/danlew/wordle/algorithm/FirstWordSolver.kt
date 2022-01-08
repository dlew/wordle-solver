package net.danlew.wordle.algorithm

import net.danlew.wordle.GuessResult

/**
 * Dumb solver that will often fail.
 */
object FirstWordSolver : Solver {

  override fun nextGuess(wordPool: List<String>, targets: List<String>, previousGuesses: List<GuessResult>): String {
    return targets[0]
  }

}