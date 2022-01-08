package net.danlew.wordle

import net.danlew.wordle.algorithm.KnuthSolver

fun main() {
  val wordList = loadWordList("wordlist")
  val targets = loadWordList("targets")

  val knuthSolver = KnuthSolver("arise")

  println("Computing Knuth Solver Histogram... (could take a while)!")
  val histogram = knuthSolver.runAllGames(wordList, targets, false)
  println("Knuth Solver Histogram: $histogram")

  println("Computing Knuth Solver Histogram (hard mode)... (could take a while)!")
  val hardModeHistogram = knuthSolver.runAllGames(wordList, targets, true)
  println("Knuth Solver Histogram (hard mode): $hardModeHistogram")
}
