package net.danlew.wordle

import net.danlew.wordle.algorithm.FirstWordSolver
import net.danlew.wordle.algorithm.KnuthSolver
import net.danlew.wordle.algorithm.Solver
import kotlin.math.roundToInt
import kotlin.system.measureNanoTime

fun main() {
  val wordList = loadWordList("wordlist")
  val targets = loadWordList("targets")

  FirstWordSolver.run(wordList, targets)

  val knuthSolver = KnuthSolver("arise")
  knuthSolver.run(wordList, targets)
}

private fun Solver.run(wordList: List<String>, targets: List<String>) {
  val time = measureNanoTime {
    println("Computing ${this.javaClass.simpleName} Histogram... (could take a while)!")
    val histogram = runAllGames(wordList, targets, false)
    println(formatHistogram(histogram))
  }
  println("Completed in ${(time / 1e9).roundToInt()} second(s)!")

  println()

  val hardModeTime = measureNanoTime {
    println("Computing ${this.javaClass.simpleName} Histogram (hard mode)... (could take a while)!")
    val histogram = runAllGames(wordList, targets, true)
    println(formatHistogram(histogram))
  }
  println("Completed in ${(hardModeTime / 1e9).roundToInt()} second(s)!")

  println()
}