package be.sukes13.aoc2025.day1

import be.sukes13.aoc2025.mapLines
import kotlin.math.absoluteValue

fun part1(input: String) = input.toTurns()
    .fold(TurningState(position = 50)) { previousState, turning ->
        turning(previousState)
    }.endedAtZero

fun part2(input: String) = input.toTurns()
    .fold(TurningState(position = 50)) { previousState, turning ->
        turning(previousState)
    }.passesByNull

sealed class Turning(val numberOfTurns: Int) {
    abstract operator fun invoke(pastResult: TurningState): TurningState

    class Right(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(pastResult: TurningState): TurningState {
            val unboundMove = pastResult.position + numberOfTurns
            val endedAt = unboundMove.mod(100)
            return TurningState(
                position = endedAt,
                endedAtZero = pastResult.endedAtZero + endedAt.countIfAtZero(),
                passesByNull = pastResult.passesByNull + unboundMove / 100
            )
        }
    }

    class Left(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(pastResult: TurningState): TurningState {
            val unboundMove = pastResult.position - numberOfTurns
            val endedAt = unboundMove.mod(100)
            return TurningState(
                position = endedAt,
                endedAtZero = pastResult.endedAtZero + endedAt.countIfAtZero(),
                passesByNull = pastResult.passesByNull +
                        if (unboundMove <= 0) {
                            val numberOfLoops = unboundMove.absoluteValue / 100
                            if (pastResult.position == 0) numberOfLoops else numberOfLoops + 1
                        } else 0
            )
        }
    }

    internal fun Int.countIfAtZero() = if (this == 0) 1 else 0
}

data class TurningState(val position: Int, val endedAtZero: Int = 0, val passesByNull: Int = 0)

private fun String.toTurns(): List<Turning> = mapLines {
    val numberOfTurns = it.takeLast(it.length - 1).toInt()
    when (it.first()) {
        'R' -> Turning.Right(numberOfTurns)
        'L' -> Turning.Left(numberOfTurns)
        else -> error("illegal direction!")
    }
}
