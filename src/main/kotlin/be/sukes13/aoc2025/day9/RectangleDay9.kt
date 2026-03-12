package be.sukes13.aoc2025.day9

import be.sukes13.aoc2025.Point
import be.sukes13.aoc2025.mapLines
import kotlin.math.absoluteValue

fun part1(input: String) = input.toPoints().toPointPairs().calculateSurface().max()

fun part2(input: String) = input.toPoints().toValidRectangleRanges().calculateSurface().max()

private fun List<Point>.toValidRectangleRanges(): List<Pair<Point, Point>> {
    val validRows = validRows()
    return toPointPairs().filter { (p1, p2) ->
        toRange(p1.y, p2.y)
            .map { p1.inRow(validRows[it]!!) && p2.inRow(validRows[it]!!) }
            .all { it }
    }
}

private fun List<Point>.validRows() =
    (this + first()).windowed(2, 1) { (p1, p2) ->
        connectRedTiles(p1, p2)
    }.flatten().distinct()
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, list) -> list.sorted() }
        .mapValues { (_, list) -> list.first()..list.last() }

private fun connectRedTiles(p1: Point, p2: Point) =
    if (p1.y == p2.y) toRange(p1.x, p2.x).map { p1.y to it }
    else toRange(p1.y, p2.y).map { it to p1.x }

private fun toRange(p1: Int, p2: Int) =
    if (p1 < p2) (p1..p2) else (p1 downTo p2)

private fun Point.inRow(row: IntRange) = x >= row.first && x <= row.last

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

