package be.sukes13.aoc2025.day3

import be.sukes13.aoc2025.mapLines

fun part1(input: String) = input.toRacks().sumOf { it.turnOnBatteries(2) }

fun part2(input: String) = input.toRacks().sumOf { it.turnOnBatteries(12) }

fun List<Int>.turnOnBatteries(numberOfBatteries: Int) =
    (numberOfBatteries - 1 downTo 0).fold(this to "") { (remainingRack, currentResult), remainingNumberOfBatteries ->
        val indexOfHighestUsable = remainingRack.indexOfHighestUsableDigit(remainingNumberOfBatteries)
        val newRack = remainingRack.drop(indexOfHighestUsable + 1)
        newRack to currentResult + remainingRack[indexOfHighestUsable]
    }.second.toLong()

private fun List<Int>.indexOfHighestUsableDigit(numberOfBatteries: Int) =
    indexOfFirst { it == dropLast(numberOfBatteries).maxOrNull() }

private fun String.toRacks() = mapLines { it.toRack() }
fun String.toRack() = map { it.digitToInt() }

