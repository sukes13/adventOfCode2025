package be.sukes13.aoc2025.day7

import be.sukes13.aoc2025.Direction.DOWN
import be.sukes13.aoc2025.Point
import be.sukes13.aoc2025.stepInDirection
import be.sukes13.aoc2025.toPoints

fun part1(input: String) = input.toManifold().sendBeam().splittersHit

fun part2(input: String) = input.toManifold().sendQuantumBeam()

data class Manifold(
    val splitters: List<Point>,
    val start: Point,
    val lowestBeams: Set<Point> = setOf(start),
    val splittersHit: Int = 0,
) {
    private val bottom = splitters.maxBy { it.y }.y

    fun sendQuantumBeam(): Int {
        val startQuantumRow = (0..splitters.maxBy { it.x }.x).associate { if (Point(it, 0) != start) it to 0 else start.x to 1 }.toMutableMap()
        return (0..bottom).fold(startQuantumRow) { previousRow, y ->
            previousRow.forEach { (x, numberOfBeams) ->
                if (Point(x, y + 1) !in splitters) previousRow[x] = numberOfBeams
                else {
                    previousRow[x - 1] = previousRow.numberOfBeamsLeft(x, y, previousRow[x]!!).also{ println("for ($x-1,$y) set $it")}
                    previousRow[x + 1] = previousRow.numberOfBeamsRight(x, y, previousRow[x]!!).also{ println("for ($x,$y) set $it")}
                    previousRow[x] = 0
                }
            }
            previousRow.also { println(it.map { it.value }) }
        }.values.sumOf { it }
    }

    private fun MutableMap<Int, Int>.numberOfBeamsLeft(x: Int, y: Int, beamsInSpot: Int): Int {
        return beamsInSpot + if (Point(x - 2, y + 1) in splitters) this[x - 2]!! else 0
    }

    private fun MutableMap<Int, Int>.numberOfBeamsRight(x: Int, y: Int, beamsInSpot: Int): Int {
        return beamsInSpot + if (Point(x + 2, y + 1) in splitters) this[x + 2]!! else 0
    }


    fun sendBeam() = (0..bottom).fold(this) { previousManifold, _ ->
        previousManifold.oneClick()
    }

    private fun oneClick() =
        if (lowestBeams.isEmpty()) copy(lowestBeams = setOf(start.stepInDirection(DOWN)))
        else moveBeams().let { result ->
            copy(
                lowestBeams = result.flatMap { it.first }.toSet(),
                splittersHit = splittersHit + result.sumOf { it.second }
            )
        }

    private fun moveBeams() = lowestBeams.map { it.moveBeam() }

    private fun Point.moveBeam(): Pair<List<Point>, Int> = stepInDirection(DOWN).let { oneDown ->
        if (oneDown in splitters) oneDown.splitBeam() to 1
        else listOf(oneDown) to 0
    }

    private fun Point.splitBeam() = listOf(copy(x = x - 1), copy(x = x + 1))
}

private fun String.toManifold() = Manifold(
    splitters = toPoints('^'),
    start = toPoints('S').single()
)



