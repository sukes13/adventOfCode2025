package be.sukes13.aoc2025.day7

import be.sukes13.aoc2025.Point
import be.sukes13.aoc2025.toPoints

fun part1(input: String) = input.toManifold().sendQuantumBeam().second

fun part2(input: String) = input.toManifold().sendQuantumBeam().first.values.sum()

data class Manifold(
    val splitters: List<Point>,
    val start: Point,
) {
    private val bottom = splitters.maxBy { it.y }.y
    private val createStartQuantumRow = (0..splitters.maxBy { it.x }.x + 1)
        .associate { if (Point(it, 0) != start) it to 0L else start.x to 1L }
        .toMutableMap()

    fun sendQuantumBeam() = (0..bottom).fold(createStartQuantumRow to 0) { (row, splittersHit), y ->
        var newSplittersHit = splittersHit
        row.forEach { (x, beamsInPoint) ->
            val isAboveSplitter = Point(x, y + 1) in splitters
            if (beamsInPoint > 0 && isAboveSplitter) newSplittersHit++
            if (isAboveSplitter) {
                row[x - 1] = row.numberOfBeamsNextToSplitter(x, y) { a, b -> a - b }
                row[x + 1] = row.numberOfBeamsNextToSplitter(x, y) { a, b -> a + b }
            } else row[x] = beamsInPoint
        }
        row.removeBeamsUnderSplitter(y) to newSplittersHit
    }

    private fun MutableMap<Int, Long>.numberOfBeamsNextToSplitter(x: Int, y: Int, calc: (Int, Int) -> Int) =
        this[x]!! + if (Point(calc(x, 2), y + 2) in splitters) this[calc(x, 2)]!! else this[calc(x, 1)]!!

    private fun MutableMap<Int, Long>.removeBeamsUnderSplitter(y: Int) =
        splitters.filter { it.y == y + 1 }.forEach { this[it.x] = 0L }.let { this }
}

private fun String.toManifold() = Manifold(
    splitters = toPoints('^'),
    start = toPoints('S').single()
)



