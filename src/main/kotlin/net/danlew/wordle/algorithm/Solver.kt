package net.danlew.wordle.algorithm

import net.danlew.wordle.GuessResult

interface Solver {

  /**
   * Generate the next guess based on previous game state.
   *
   * @param wordPool The list of available words to guess
   * @param targets The list of possible answers (a subset of [wordPool])
   */
  fun nextGuess(wordPool: List<String>, targets: List<String>, previousGuesses: List<GuessResult>): String

}