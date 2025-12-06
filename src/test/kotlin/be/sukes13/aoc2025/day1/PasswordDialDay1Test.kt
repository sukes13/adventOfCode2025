package be.sukes13.aoc2025.day1

import be.sukes13.aoc2025.day1.Turning.Left
import be.sukes13.aoc2025.day1.Turning.Right
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource

class PasswordDialDay1Test {

    @ParameterizedTest
    @MethodSource("rollOverCases")
    fun `checking roll over zero counting`(turning: Turning, startAt: TurningState, endAt: Int, numberOfZeros: Int) {
        assertThat(turning(startAt).position).isEqualTo(endAt)
        assertThat(turning(startAt).passesByZero).isEqualTo(numberOfZeros)
    }

    companion object {
        @JvmStatic
        fun rollOverCases() = listOf(
            of(Right(numberOfTurns = 200), TurningState(50), 50, 2),
            of(Right(numberOfTurns = 300),TurningState( 0), 0, 3),
            of(Right(numberOfTurns = 50), TurningState(50), 0, 1),
            of(Right(numberOfTurns = 150), TurningState(50), 0, 2),
            of(Right(numberOfTurns = 100), TurningState(0), 0, 1),

            of(Left(numberOfTurns = 200), TurningState(50), 50, 2),
            of(Left(numberOfTurns = 300), TurningState(0), 0, 3),
            of(Left(numberOfTurns = 50), TurningState(50), 0, 1),
            of(Left(numberOfTurns = 150), TurningState(50), 0, 2),
            of(Left(numberOfTurns = 100), TurningState(0), 0, 1),
        )
    }
}