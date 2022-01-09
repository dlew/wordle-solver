package net.danlew.wordle

fun formatHistogram(histogram: List<Int>): String {
  val sb = StringBuilder()

  histogram.drop(1).forEachIndexed { index, i ->
    sb.append("Solved in ${index + 1} guess(es): $i\n")
  }

  sb.append("Failed to solve: ${histogram[0]}\n")
  sb.append("Average # guesses: ${average(histogram)}")

  return sb.toString()
}

/**
 * Finds average # guesses, ignoring failures
 */
fun average(histogram: List<Int>): Double {
  val sumOfCounts = histogram.mapIndexed { index, i -> index * i }.sum()
  val sumOfRuns = histogram.drop(1).sum()
  return sumOfCounts.toDouble() / sumOfRuns
}
