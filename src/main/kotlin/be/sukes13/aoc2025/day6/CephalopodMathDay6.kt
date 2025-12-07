package be.sukes13.aoc2025.day6

fun part1(input: String) = input.toCephalopodOperationsPart1().sumOf { it.calculate() }

fun part2(input: String) = input.toCephalopodOperationsPart2().sumOf { it.calculate() }

private fun String.toCephalopodOperationsPart1(): List<CephalopodOperation> {
    val operations = parseOperations()
    val numbersLines = lines().dropLast(1)
        .splitOnBlanks()
        .flatMap { dirtyNumber -> dirtyNumber.map { it.cleanUpBlanks() } }
        .map { it.split(" ") }
    return numbersLines.turnColumnsIntoRow()
        .filterNot { it.isEmpty() }
        .mapIndexed { index, numbers -> CephalopodOperation(numbers, operations[index]) }
}

private fun String.toCephalopodOperationsPart2(): List<CephalopodOperation> {
    val operations = parseOperations()
    val numbersLines = lines().dropLast(1)
        .map { it.split("") }
    return numbersLines.turnColumnsIntoRow().map { it.joinToString("") }
        .map { it.trim() }
        .splitOnBlanks()
        .filterNot { it.isEmpty() }
        .mapIndexed { index, numbers -> CephalopodOperation(numbers, operations[index]) }
}

private fun List<List<String>>.turnColumnsIntoRow() =
    (0 until first().size).fold(mutableListOf(listOf<String>())) { acc, index ->
        acc.add(map { it[index] })
        acc
    }

fun List<String>.splitOnBlanks() = fold(mutableListOf(mutableListOf<String>())) { acc, number ->
    if (number.isBlank()) acc.add(mutableListOf()) else acc.last().add(number)
    acc
}.toList()

private fun String.parseOperations() = lines().last().cleanUpBlanks().split(" ")

data class CephalopodOperation(private val numbers: List<String>, private val operation: String) {
    private val operationFunction: (Long, Long) -> Long = when (operation) {
        "+" -> { a, b -> a + b }
        "*" -> { a, b -> a * b }
        else -> error("illegal operation")
    }

    fun calculate() =
        numbers.foldIndexed(numbers.first().toLong()) { index, acc, number ->
            if (index == 0) acc
            else operationFunction(acc, number.toLong())
        }
}

private fun String.splitLines() = cleanUpBlanks()
    .split("\r\n")
    .map { it.trim().split(" ") }

private fun String.cleanUpBlanks() = trim()
    .replace("        ", " ")
    .replace("      ", " ")
    .replace("    ", " ")
    .replace("   ", " ")
    .replace("  ", " ")
