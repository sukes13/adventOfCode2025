package be.sukes13.aoc2025.day6

import be.sukes13.aoc2025.turnColumnsIntoRows

fun part1(input: String) = input.toCephalopodOperationsPart1().sumOf { it.calculate() }

fun part2(input: String) = input.toCephalopodOperationsPart2().sumOf { it.calculate() }

private fun String.toCephalopodOperationsPart1() =
    lines().dropLast(1)
        .splitListOnBlanks()
        .flatten()
        .map { it.splitStringOnAnyNumberOfBlanks() }
        .turnColumnsIntoRows()
        .createCephalopodOperations(this)

private fun String.toCephalopodOperationsPart2() =
    lines().dropLast(1)
        .map { it.split("") }
        .turnColumnsIntoRows()
        .map { it.joinToString("").trim() }
        .splitListOnBlanks()
        .createCephalopodOperations(this)

fun List<String>.splitListOnBlanks() = fold(mutableListOf(mutableListOf<String>())) { acc, number ->
    if (number.isBlank()) acc.add(mutableListOf()) else acc.last().add(number)
    acc
}

private fun String.splitStringOnAnyNumberOfBlanks() = trim().split(" +".toRegex())

private fun List<List<String>>.createCephalopodOperations(input: String) =
    input.lines().last().splitStringOnAnyNumberOfBlanks().let { operations ->
        filterNot { it.isEmpty() }
            .mapIndexed { index, numbers -> CephalopodOperation(numbers, operations[index]) }
    }

data class CephalopodOperation(private val numbers: List<String>, private val operation: String) {
    private val operationFunction: (Long, Long) -> Long = when (operation) {
        "+" -> { a, b -> a + b }
        "*" -> { a, b -> a * b }
        else -> error("illegal operation")
    }

    fun calculate() = numbers.map { it.toLong() }.reduce(operationFunction)
}
