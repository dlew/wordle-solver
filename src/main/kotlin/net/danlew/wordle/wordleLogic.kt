package net.danlew.wordle

fun filterValidHardModeGuesses(wordList: List<String>, lastGuess: GuessResult): List<String> {
  return wordList.filter { guess -> validHardModeGuess(guess, lastGuess) }
}

fun validHardModeGuess(guess: String, lastGuess: GuessResult): Boolean {
  require(guess.length == lastGuess.guess.length)

  val unusedGuessLetters = mutableListOf<Char>()

  // Verify all correct letters are still present
  for (index in guess.indices) {
    if (lastGuess.hints[index] == Hint.CORRECT) {
      if (guess[index] != lastGuess.guess[index]) {
        return false
      }
    }
    else {
      unusedGuessLetters.add(guess[index])
    }
  }

  // Verify misplaced letters are somewhere else
  val misplacedLetters = lastGuess.hints.mapIndexedNotNull { index, hint ->
    if (hint == Hint.MISPLACED) lastGuess.guess[index] else null
  }

  if (!unusedGuessLetters.containsAll(misplacedLetters)) {
    return false
  }

  return true
}

fun evaluateGuess(guess: String, target: String): List<Hint> {
  require(guess.length == target.length) { "guess and target are different lengths" }

  val length = guess.length

  val result = MutableList(length) { Hint.UNUSED }
  val unusedTargetLetters = target.toMutableList()

  // Find all correct letters first
  for (index in 0 until length) {
    val letter = guess[index]
    if (letter == target[index]) {
      result[index] = Hint.CORRECT
      unusedTargetLetters.remove(letter)
    }
  }

  // Find all letters in the wrong place
  for (index in 0 until length) {
    if (result[index] == Hint.CORRECT) continue

    val letter = guess[index]
    if (letter in unusedTargetLetters) {
      result[index] = Hint.MISPLACED
      unusedTargetLetters.remove(letter)
    }
  }

  return result
}

val List<Hint>.solved: Boolean
  get() = all { it == Hint.CORRECT }
