package net.danlew.wordle

import net.danlew.wordle.Hint.*
import org.junit.Assert.*
import org.junit.Test

class WordleLogicKtTest {

  @Test
  fun filterValidHardModeGuessesCorrectly() {
    assertEquals(
      listOf("abc", "acb"),
      filterValidHardModeGuesses(
        listOf("abc", "def", "acb", "cab"),
        lastGuess = GuessResult(
          guess = "abc",
          listOf(CORRECT, UNUSED, MISPLACED)
        )
      )
    )
  }

  @Test
  fun guessValidInHardMode() {
    assertTrue(
      validHardModeGuess(
        guess = "ac",
        lastGuess = GuessResult(
          guess = "ab",
          listOf(CORRECT, UNUSED)
        )
      )
    )
  }

  @Test
  fun guessInvalidInHardMode() {
    assertFalse(
      validHardModeGuess(
        guess = "ca",
        lastGuess = GuessResult(
          guess = "ab",
          listOf(CORRECT, UNUSED)
        )
      )
    )

    assertFalse(
      validHardModeGuess(
        guess = "caa",
        lastGuess = GuessResult(
          guess = "cbc",
          listOf(CORRECT, UNUSED, MISPLACED)
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