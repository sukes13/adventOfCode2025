package be.sukes13.aoc2025.day3

import be.sukes13.aoc2025.mapLines

fun part1(input: String) = input.toRacks().turnOnBatteries().sum()

fun part2(input: String) = input.toRacks().sumOf { rack ->
    rack.turnOnBatteries2(12)
}

fun List<Int>.turnOnBatteries2(numberOfBatteries: Int): Long {
    return (numberOfBatteries downTo 1).fold(this to "") { (remainingRack,currentResult), remainingNumberOfBatteries ->
        val (nextIndex, nextRack) = findNext(remainingRack, remainingNumberOfBatteries)
        nextRack to currentResult + remainingRack[nextIndex]
    }.second.toLong()
}

private fun findNext(remainingRack: List<Int>, remainingNumberOfBatteries: Int): Pair<Int, List<Int>> {
    val firstIndex = remainingRack.findHighestUsable2(remainingNumberOfBatteries)
    val rackAfterFirst = remainingRack.drop(firstIndex + 1)
    return firstIndex to rackAfterFirst
}

private fun List<Int>.findHighestUsable2(numberOfBatteries: Int) = run breaker@{
    (0 until size - numberOfBatteries).fold(0) { acc, _ ->
        val indexOfHighestUsable = findHighestUsable(numberOfBatteries - 1)
        if (indexOfHighestUsable == -1) acc else return@breaker indexOfHighestUsable
    }
}

private fun List<Int>.findHighestUsable(numberOfBatteries: Int): Int {
    val indexOfHighest = indexOfFirst { it == maxOrNull() }
    return if (indexOfHighest + numberOfBatteries >= size) {
        indexOfFirst { it == dropLast(numberOfBatteries).maxOrNull() }
    } else indexOfHighest
}

private fun List<List<Int>>.turnOnBatteries(): List<Int> {
    return map { rack ->
        val firstBatteryIndex = rack.findHighestUsable(1)
        val secondBattery = rack.drop(firstBatteryIndex + 1).maxOrNull()
        "${rack[firstBatteryIndex]}$secondBattery".toInt()
    }
}

private fun String.toRacks() = mapLines { it.toRack() }

fun String.toRack() = map { it.digitToInt() }

