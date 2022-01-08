package net.danlew.wordle

data class GuessResult(
  val guess: String,
  val hints: List<Hint>
)
