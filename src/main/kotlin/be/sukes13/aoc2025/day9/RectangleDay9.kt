package be.sukes13.aoc2025.day9

import be.sukes13.aoc2025.Point
import be.sukes13.aoc2025.mapLines
import kotlin.math.absoluteValue

fun part1(input: String) = input.toPoints().toPointPairs().calculateSurface().max()

fun part2(input: String) = input.toPoints().toValidRectangleRanges().calculateSurface().max()

private fun List<Point>.toValidRectangleRanges(): List<Pair<Point, Point>> {
    val validTiles = (this + first()).windowed(2, 1) { (p1, p2) ->
        p1.connectRedTiles(p2)
    }.flatten().distinct()
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, list) -> list.sorted() }
        .mapValues { (_, list) -> list.first()..list.last() }
    return toSurfacesRanges(validTiles)
}

private fun List<Point>.toSurfacesRanges(validTiles: Map<Int, IntRange>) =
    toPointPairs().filter { (p1, p2) ->
        toRange(p1.y, p2.y)
            .map { p1.inRow(validTiles[it]!!) && p2.inRow(validTiles[it]!!) }
            .all { it }
    }

private fun toRange(p1: Int, p2: Int) =
    if (p1 < p2) (p1..p2) else (p1 downTo p2)

private fun Point.inRow(row: IntRange) = x >= row.first && x <= row.last

private fun Point.connectRedTiles(p2: Point) =
    if (y == p2.y) toRange(x, p2.x).map { y to it }
    else toRange(y, p2.y).map { it to x }

private fun List<Pair<Point, Point>>.calculateSurface() =
    map { (p1, p2) ->
        ((p1.x - p2.x).absoluteValue + 1).toLong() * ((p1.y - p2.y).absoluteValue + 1)
    }

private fun List<Point>.toPointPairs() =
    sortedBy { it.x }.flatMap {
        mapIndexedNotNull { index, _ ->
            val secondPoint = getOrNull(index + 1) ?: this[0]
            if (it != secondPoint) it to secondPoint
            else null
        }
    }.distinct()

private fun String.toPoints() = mapLines { line ->
    line.split(",").let {
        Point(it.first().toInt(), it.last().toInt())
    }
}

