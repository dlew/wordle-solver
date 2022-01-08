package net.danlew.wordle.algorithm

import net.danlew.wordle.GuessResult

interface Solver {

  fun nextGuess(wordPool: List<String>, previousGuesses: List<GuessResult>): String

}