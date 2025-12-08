package be.sukes13.aoc2025.day7

import be.sukes13.aoc2025.Direction
import be.sukes13.aoc2025.Direction.DOWN
import be.sukes13.aoc2025.Point
import be.sukes13.aoc2025.stepInDirection
import be.sukes13.aoc2025.toPoints

fun part1(input: String) = input.toManifold().sendBeam().splittersHit

fun part2(input: String) = 40

data class Manifold(
    val splitters: List<Point>,
    val start: Point,
    val lowestBeams: Set<Point> = emptySet(),
    val splittersHit: Int = 0
) {
    fun sendBeam() = (0..splitters.maxOf { it.y }).fold(this) { previousManifold, _ ->
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

    private fun moveBeams() = lowestBeams.map {
        it.stepInDirection(DOWN).let { oneDown ->
            if (oneDown in splitters) oneDown.splitBeam() to 1
            else listOf(oneDown) to 0
        }
    }

    private fun Point.splitBeam() = listOf(copy(x = x - 1), copy(x = x + 1))
}

private fun String.toManifold() = Manifold(
    splitters = toPoints('^'),
    start = toPoints('S').single()
)



