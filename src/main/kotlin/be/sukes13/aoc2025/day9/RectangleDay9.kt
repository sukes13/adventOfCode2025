package be.sukes13.aoc2025.day9

import be.sukes13.aoc2025.Point
import be.sukes13.aoc2025.mapLines
import kotlin.math.absoluteValue

fun part1(input: String) = input.toPoints().toSurfaces().max()

fun part2(input: String) = 0

private fun List<Point>.toSurfaces() =
    toPointPairs().map { (p1, p2) -> calculateSurface(p1, p2) }

private fun calculateSurface(p1: Point, p2: Point) =
    ((p1.x - p2.x).absoluteValue + 1).toLong() * ((p1.y - p2.y).absoluteValue + 1)

private fun List<Point>.toPointPairs() =
    flatMap {
        mapIndexedNotNull { index, _ ->
            val secondPoint = getOrNull(index + 1) ?: this[0]
            if (it != secondPoint)  it to secondPoint
            else null
        }
    }.distinct()

private fun String.toPoints() = mapLines { line ->
    line.split(",").let {
        Point(it.first().toInt(), it.last().toInt())
    }
}

