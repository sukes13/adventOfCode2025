package be.swsb.coderetreat.day1

import be.swsb.coderetreat.mapLines
import kotlin.math.absoluteValue

fun part1(input: String) = input.toTurns()
    .fold(TurningResult(50)) { acc, turning ->
        turning(acc.endPosition).let { turnResult ->
            turnResult.copy(endAtZero = turnResult.endAtZero + acc.endAtZero)
        }
    }.endAtZero

fun part2(input: String) = input.toTurns()
    .fold(TurningResult(50)) { acc, turning ->
        turning(acc.endPosition).let { turnResult ->
            turnResult.copy(passesByNull = turnResult.passesByNull + acc.passesByNull)
        }
    }.passesByNull

sealed class Turning(val numberOfTurns: Int) {
    abstract operator fun invoke(startAt: Int): TurningResult

    class Right(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(startAt: Int): TurningResult {
            val unboundMove = startAt + numberOfTurns
            val endPosition = unboundMove.mod(100)
            return TurningResult(
                endPosition = endPosition,
                endAtZero = endedAtZero(endPosition),
                passesByNull = unboundMove / 100
            )
        }
    }

    class Left(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(startAt: Int): TurningResult {
            val unboundMove = startAt - numberOfTurns
            val endPosition = unboundMove.mod(100)
            return TurningResult(
                endPosition = endPosition,
                endAtZero = endedAtZero(endPosition),
                passesByNull = if (unboundMove <= 0) {
                    val numberOfLoops = unboundMove.absoluteValue / 100
                    if (startAt == 0) numberOfLoops else numberOfLoops + 1
                } else 0
            )
        }
    }

    internal fun endedAtZero(endPosition: Int) = if (endPosition == 0) 1 else 0
}

data class TurningResult(val endPosition: Int, val endAtZero: Int = 0, val passesByNull: Int = 0)

private fun String.toTurns(): List<Turning> = mapLines {
    val numberOfTurns = it.takeLast(it.length - 1).toInt()
    when (it.first()) {
        'R' -> Turning.Right(numberOfTurns)
        'L' -> Turning.Left(numberOfTurns)
        else -> error("illegal direction!")
    }
}
