package be.sukes13.aoc2025.day1

import be.sukes13.aoc2025.mapLines
import kotlin.math.absoluteValue

fun part1(input: String) = input.toTurns()
    .fold(TurningResult(50)) { passedTurns, turning ->
        turning(passedTurns.position).let { turnResult ->
            turnResult.copy(endedAtZero = passedTurns.endedAtZero + turnResult.endedAtZero)
        }
    }.endedAtZero

fun part2(input: String) = input.toTurns()
    .fold(TurningResult(50)) { passedTurns, turning ->
        turning(passedTurns.position).let { turnResult ->
            turnResult.copy(passesByNull = passedTurns.passesByNull + turnResult.passesByNull)
        }
    }.passesByNull

sealed class Turning(val numberOfTurns: Int) {
    abstract operator fun invoke(startAt: Int): TurningResult

    class Right(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(startAt: Int): TurningResult {
            val unboundMove = startAt + numberOfTurns
            val endedAt = unboundMove.mod(100)
            return TurningResult(
                position = endedAt,
                endedAtZero = endedAt.countIfAtZero(),
                passesByNull = unboundMove / 100
            )
        }
    }

    class Left(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(startAt: Int): TurningResult {
            val unboundMove = startAt - numberOfTurns
            val endedAt = unboundMove.mod(100)
            return TurningResult(
                position = endedAt,
                endedAtZero = endedAt.countIfAtZero(),
                passesByNull = if (unboundMove <= 0) {
                    val numberOfLoops = unboundMove.absoluteValue / 100
                    if (startAt == 0) numberOfLoops else numberOfLoops + 1
                } else 0
            )
        }
    }

    internal fun Int.countIfAtZero() = if (this == 0) 1 else 0
}

data class TurningResult(val position: Int, val endedAtZero: Int = 0, val passesByNull: Int = 0)

private fun String.toTurns(): List<Turning> = mapLines {
    val numberOfTurns = it.takeLast(it.length - 1).toInt()
    when (it.first()) {
        'R' -> Turning.Right(numberOfTurns)
        'L' -> Turning.Left(numberOfTurns)
        else -> error("illegal direction!")
    }
}
