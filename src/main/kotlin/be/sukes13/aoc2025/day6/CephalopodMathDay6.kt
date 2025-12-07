package be.sukes13.aoc2025.day6

fun part1(input: String) = input.toCephalopodOperationsPart1().sumOf { it.calculate() }

fun part2(input: String) = input.toCephalopodOperationsPart2().sumOf { it.calculate() }

private fun String.toCephalopodOperationsPart1() =
    lines().dropLast(1)
        .splitListOnBlanks()
        .flatten()
        .map { it.splitOnAnyNumberOfBlanks() }
        .turnColumnsIntoRow()
        .createCephalopodOperations(this)

private fun String.toCephalopodOperationsPart2() =
    lines().dropLast(1)
        .map { it.split("") }
        .turnColumnsIntoRow()
        .map { it.joinToString("").trim() }
        .splitListOnBlanks()
        .createCephalopodOperations(this)

private fun List<List<String>>.createCephalopodOperations(input: String) =
    input.lines().last().splitOnAnyNumberOfBlanks().let { operations ->
        filterNot { it.isEmpty() }
            .mapIndexed { index, numbers -> CephalopodOperation(numbers, operations[index]) }
    }

private fun List<List<String>>.turnColumnsIntoRow() =
    (0 until first().size).fold(mutableListOf(listOf<String>())) { acc, index ->
        acc.add(map { it[index] })
        acc
    }

fun List<String>.splitListOnBlanks() = fold(mutableListOf(mutableListOf<String>())) { acc, number ->
    if (number.isBlank()) acc.add(mutableListOf()) else acc.last().add(number)
    acc
}

private fun String.splitOnAnyNumberOfBlanks() = trim().split(" +".toRegex())

data class CephalopodOperation(private val numbers: List<String>, private val operation: String) {
    private val operationFunction: (Long, Long) -> Long = when (operation) {
        "+" -> { a, b -> a + b }
        "*" -> { a, b -> a * b }
        else -> error("illegal operation")
    }

    fun calculate() = numbers.foldIndexed(numbers.first().toLong()) { index, acc, number ->
        if (index == 0) acc
        else operationFunction(acc, number.toLong())
    }
}
