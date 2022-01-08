package net.danlew.wordle

import net.danlew.wordle.Hint.*
import org.junit.Assert.*
import org.junit.Test

class WordleLogicKtTest {

  @Test
  fun guessValid() {
    assertTrue(validGuess(guess = "abc", wordList = listOf("abc")))
  }

  @Test
  fun guessInvalidBecauseNotInWordList() {
    assertFalse(validGuess(guess = "123456", wordList = listOf("abcdef")))
  }

  @Test
  fun guessInvalidBecauseHardMode() {
    assertFalse(
      validGuess(
        guess = "ca",
        wordList = listOf("ab", "ca"),
        hardMode = true,
        lastGuess = GuessResult(
          guess = "ab",
          listOf(CORRECT, UNUSED)
        )
      )
    )

    // It would have worked if not for hard mode
    assertTrue(
      validGuess(
        guess = "ca",
        wordList = listOf("ab", "ca"),
        hardMode = false,
        lastGuess = GuessResult(
          guess = "ab",
          listOf(CORRECT, UNUSED)
        )
      )
    )
  }

  @Test
  fun nothingCorrect() {
    assertEquals(
      List(2) { UNUSED },
      evaluateGuess("aa", "bb")
    )
  }

  @Test
  fun oneCorrect() {
    assertEquals(
      listOf(CORRECT, UNUSED),
      evaluateGuess("aa", "ab")
    )
  }

  @Test
  fun oneWrongPlace() {
    assertEquals(
      listOf(UNUSED, MISPLACED),
      evaluateGuess("ab", "bc")
    )
  }

  @Test
  fun mixedEvaluation() {
    assertEquals(
      listOf(UNUSED, CORRECT, UNUSED, MISPLACED, UNUSED),
      evaluateGuess("abcde", "zbdxy")
    )
  }

  @Test
  fun correctLettersEvaluatedFirst() {
    assertEquals(
      listOf(CORRECT, UNUSED, UNUSED),
      evaluateGuess("aab", "axy")
    )
  }

  @Test
  fun hintsNotGivenTwice() {
    assertEquals(
      listOf(MISPLACED, UNUSED, UNUSED, UNUSED),
      evaluateGuess("aabc", "ddea")
    )
  }

  @Test(expected = IllegalArgumentException::class)
  fun incorrectLengths() {
    evaluateGuess("one", "three")
  }

  @Test
  fun solved() {
    assertTrue(listOf(CORRECT, CORRECT, CORRECT).solved)
    assertFalse(listOf(CORRECT, UNUSED, CORRECT).solved)
    assertFalse(listOf(CORRECT, MISPLACED, CORRECT).solved)
  }
}