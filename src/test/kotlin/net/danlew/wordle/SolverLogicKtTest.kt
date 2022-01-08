package net.danlew.wordle

import net.danlew.wordle.Hint.*
import org.junit.Assert.*
import org.junit.Test

class SolverLogicKtTest {

  @Test
  fun couldMatch() {
    assertTrue(
      GuessResult("abc", listOf(UNUSED, CORRECT, MISPLACED)).couldMatch("cbz")
    )
  }

  @Test
  fun couldNotMatchBecauseCorrectHintDoesNotMatch() {
    assertFalse(
      GuessResult("abc", listOf(UNUSED, CORRECT, MISPLACED)).couldMatch("cxz")
    )
  }

  @Test
  fun couldNotMatchBecauseMisplacedNotPresent() {
    assertFalse(
      GuessResult("abc", listOf(UNUSED, CORRECT, MISPLACED)).couldMatch("xbz")
    )
  }

  @Test
  fun couldNotMatchBecauseMisplacedInCorrectSpot() {
    assertFalse(
      GuessResult("abc", listOf(UNUSED, CORRECT, MISPLACED)).couldMatch("zbc")
    )
  }

  @Test
  fun couldNotMatchBecauseUnusedLetterPresent() {
    assertFalse(
      GuessResult("abc", listOf(UNUSED, CORRECT, MISPLACED)).couldMatch("cba")
    )
  }

  @Test
  fun couldNotMatchBecauseUnusedHandlesDoubleLetters() {
    assertFalse(
      GuessResult("aab", listOf(MISPLACED, UNUSED, UNUSED)).couldMatch("baa")
    )
  }

}