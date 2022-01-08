package net.danlew.wordle

/** @return true if the last guess *could* match the target */
fun GuessResult.couldMatch(target: String): Boolean {
  require(guess.length == target.length)

  val misplacedGuessLetters = mutableListOf<Char>()
  val unusedGuessLetters = mutableListOf<Char>()
  val remainingTargetLetters = mutableListOf<Char>()

  for (index in guess.indices) {
    when (hints[index]) {
      Hint.CORRECT -> {
        if (guess[index] != target[index]) {
          return false
        }
      }
      Hint.MISPLACED -> {
        if (guess[index] == target[index]) {
          return false
        }

        misplacedGuessLetters.add(guess[index])
        remainingTargetLetters.add(target[index])
      }
      Hint.UNUSED -> {
        if (guess[index] == target[index]) {
          return false
        }

        unusedGuessLetters.add(guess[index])
        remainingTargetLetters.add(target[index])
      }
    }
  }

  // Use up remaining misplaced letters
  misplacedGuessLetters.forEach { misplacedGuessLetter ->
    if (!remainingTargetLetters.remove(misplacedGuessLetter)) {
      return false
    }
  }

  // Ensure none of the unused letters are in the target word
  // (This can't be done earlier due to double-letter possibilities)
  return remainingTargetLetters.none { it in unusedGuessLetters }
}