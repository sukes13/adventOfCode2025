package be.sukes13.aoc2025.day3

import be.sukes13.aoc2025.mapLines

fun part1(input: String) = input.toRacks().sumOf { it.turnOnBatteries(2) }

fun part2(input: String) = input.toRacks().sumOf { it.turnOnBatteries(12) }

fun List<Int>.turnOnBatteries(numberOfBatteries: Int) =
    (numberOfBatteries - 1 downTo 0).fold(this to "") { (remainingRack, currentResult), remainingNumberOfBatteries ->
        val indexOfHighestUsable = remainingRack.indexOfHighestUsable(remainingNumberOfBatteries)
        val newRack = remainingRack.drop(indexOfHighestUsable + 1)
        val newResult = currentResult + remainingRack[indexOfHighestUsable]
        newRack to newResult
    }.second.toLong()

private fun List<Int>.indexOfHighestUsable(numberOfBatteries: Int) = run breaker@{
    repeat(size - numberOfBatteries) {
        val indexOfHighestUsable = indexOfHighestUsableOrNull(numberOfBatteries)
        if (indexOfHighestUsable != null) return@breaker indexOfHighestUsable
    }
    error("something went wrong")
}

private fun List<Int>.indexOfHighestUsableOrNull(numberOfBatteries: Int) =
    indexOfFirst { it == dropLast(numberOfBatteries).maxOrNull() }.let { indexOfFirst ->
        if (indexOfFirst == -1) null else indexOfFirst
    }

private fun String.toRacks() = mapLines { it.toRack() }

fun String.toRack() = map { it.digitToInt() }

