package net.danlew.wordle

import net.danlew.wordle.algorithm.FirstWordSolver

fun main() {
  val wordList = loadWordList("wordlist")
  val targets = loadWordList("targets")

  val histogram = FirstWordSolver.runAllGames(wordList, targets, false)
  println("First Word Solver Histogram: $histogram")

  val hardModeHistogram = FirstWordSolver.runAllGames(wordList, targets, true)
  println("First Word Solver Histogram (hard mode): $hardModeHistogram")
}
