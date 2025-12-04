package be.swsb.coderetreat.day4

import kotlin.compareTo

fun part1(input: String): Int =
    input.toRolls().let { rolls ->
        rolls.count { it.numberOfNeighbouringRollsIn(rolls) == 4 }
    }

fun part2(input: String): Int = input.toRolls().keepRemovingPossibleRolls().first

private tailrec fun Rolls.keepRemovingPossibleRolls(totalRemoved: Int = 0): Pair<Int, Rolls> {
    val (numberRemoved, newRolls) = removeRolls()

    return if (numberRemoved == 0) totalRemoved to newRolls
    else newRolls.keepRemovingPossibleRolls(totalRemoved + numberRemoved)
}

fun Rolls.removeRolls(): Pair<Int, Rolls> =
    rollsAfterOneRemoval().let { rollsAfterOneRemoval ->
        count() - rollsAfterOneRemoval.count() to rollsAfterOneRemoval
    }

private fun Rolls.rollsAfterOneRemoval() = mapNotNull { if (it.numberOfNeighbouringRollsIn(this) < 4) null else it }

data class Spot(val x: Int, val y: Int) {
    val neighbours: Set<Spot>
        get() = listOf(
            Spot(-1, -1), Spot(0, -1), Spot(1, -1),
            Spot(-1, 0), Spot(1, 0),
            Spot(-1, 1), Spot(0, 1), Spot(1, 1),
        ).map { vector -> this + vector }.toSet()

    fun numberOfNeighbouringRollsIn(rolls: Rolls) = neighbours.sumOf { if (it in rolls) 1 else 0 }

    private operator fun plus(vector: Spot) = Spot(this.x + vector.x, this.y + vector.y)
}

internal fun String.toRolls() =
    lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, spotInRoom ->
            if (spotInRoom == '@') Spot(x, y) else null
        }
    }.filterNotNull()

internal fun Rolls.visualize(): String {
    return (0..9).joinToString("\n") { y ->
        (0..9).joinToString("") { x ->
            when {
                Spot(x, y) in this -> "@"
                else -> "."
            }
        }
    }.trimIndent()
}

typealias Rolls = List<Spot>