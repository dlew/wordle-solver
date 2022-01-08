package net.danlew.wordle

import net.danlew.wordle.algorithm.FirstWordSolver

fun main() {
  val wordList = loadWordList("wordlist")

  val histogram = FirstWordSolver.runAllGames(wordList, false)
  println("First Word Solver Histogram: $histogram")

  val hardModeHistogram = FirstWordSolver.runAllGames(wordList, true)
  println("First Word Solver Histogram (hard mode): $hardModeHistogram")
}
