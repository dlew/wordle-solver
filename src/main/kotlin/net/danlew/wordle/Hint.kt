package net.danlew.wordle

enum class Hint {
  UNUSED,    // Letter not in the word
  MISPLACED, // Correct letter, wrong spot
  CORRECT    // Correct letter, correct spot
}