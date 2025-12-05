package be.swsb.coderetreat.day1

import be.swsb.coderetreat.mapLines
import kotlin.math.absoluteValue

fun part1(input: String) = input.toTurns().fold(TurningResult(50)) { acc, turning ->
    turning(acc.position).let { (endAt, _) ->
        TurningResult(
            position = endAt,
            result = acc.result + if (endAt == 0) 1 else 0
        )
    }
}.result

fun part2(input: String) = input.toTurns().fold(TurningResult(50)) { acc, turning ->
    turning(acc.position).let { (endAt, passesByZero) ->
        TurningResult(
            position = endAt,
            result = acc.result + passesByZero
        )
    }
}.result

data class TurningResult(val position: Int, val result: Int = 0)

sealed class Turning(val numberOfTurns: Int) {
    abstract operator fun invoke(startAt: Int): Pair<Int,Int>

    class Right(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(startAt: Int): Pair<Int,Int>  {
            val unboundMove = startAt + numberOfTurns
            val passedByZero = unboundMove / 100
            return unboundMove.endPositionOnDial() to passedByZero
        }
    }

    class Left(numberOfTurns: Int) : Turning(numberOfTurns) {
        override fun invoke(startAt: Int): Pair<Int,Int> {
            val unboundMove = startAt - numberOfTurns
            val passesByZero = if (unboundMove <= 0) {
                val numberOfLoops = unboundMove.absoluteValue / 100
                if (startAt != 0) numberOfLoops + 1 else numberOfLoops
            } else 0
            return unboundMove.endPositionOnDial() to passesByZero
        }
    }

    internal fun Int.endPositionOnDial(): Int = mod(100)
}

private fun String.toTurns(): List<Turning> = mapLines {
    val numberOfTurns = it.takeLast(it.length - 1).toInt()
    when (it.first()) {
        'R' -> Turning.Right(numberOfTurns)
        'L' -> Turning.Left(numberOfTurns)
        else -> error("illegal direction!")
    }
}
