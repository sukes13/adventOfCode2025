package be.sukes13.aoc2025.day1

import be.sukes13.aoc2025.mapLines
import kotlin.math.absoluteValue

fun part1(input: String) = input.toTurns().turningState().endedAtZero

fun part2(input: String) = input.toTurns().turningState().passesByZero

private fun List<Turning>.turningState(): TurningState =
    fold(TurningState(position = 50)) { previousState, turning ->
        turning(previousState)
    }

sealed class Turning(val numberOfTurns: Int) {
    abstract operator fun invoke(previousState: TurningState): TurningState

    class Right(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(previousState: TurningState): TurningState {
            val unboundMove = previousState.position + numberOfTurns
            val endedAt = unboundMove.mod(100)
            return TurningState(
                position = endedAt,
                endedAtZero = previousState.endedAtZero + endedAt.countIfAtZero(),
                passesByZero = previousState.passesByZero + unboundMove / 100
            )
        }
    }

    class Left(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(previousState: TurningState): TurningState {
            val unboundMove = previousState.position - numberOfTurns
            val endedAt = unboundMove.mod(100)
            return TurningState(
                position = endedAt,
                endedAtZero = previousState.endedAtZero + endedAt.countIfAtZero(),
                passesByZero = previousState.passesByZero + unboundMove.passesByZeroFrom(previousState)
            )
        }

        private fun Int.passesByZeroFrom(previousState: TurningState) =
            if (this <= 0) {
                val numberOfLoops = absoluteValue / 100
                if (previousState.position == 0) numberOfLoops else numberOfLoops + 1
            } else 0
    }

    internal fun Int.countIfAtZero() = if (this == 0) 1 else 0
}

data class TurningState(val position: Int, val endedAtZero: Int = 0, val passesByZero: Int = 0)

private fun String.toTurns(): List<Turning> = mapLines {
    val numberOfTurns = it.takeLast(it.length - 1).toInt()
    when (it.first()) {
        'R' -> Turning.Right(numberOfTurns)
        'L' -> Turning.Left(numberOfTurns)
        else -> error("illegal direction!")
    }
}
