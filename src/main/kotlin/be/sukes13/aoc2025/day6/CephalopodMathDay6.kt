package be.sukes13.aoc2025.day6

fun part1(input: String) = input.toCephalopodOperations().sumOf { it() }

fun part2(input: String) = input.toCephalopodOperationsPart2().sumOf { it() }

private fun String.toCephalopodOperationsPart2(): List<CephalopodOperation> {
    val operations = lines().last().cleanUp().split(" ")
    val numbersLines = lines().dropLast(1)
    return (0 until numbersLines.first().length).fold(mutableMapOf(0 to listOf<String>())) { acc, index ->
        acc[index] = numbersLines.map { it[index].toString() }
        acc
    }.mapValues { it.value.joinToString("") }.values
        .map { it.trim() }
        .splitOnBlanks()
        .mapIndexed { index, numbers -> CephalopodOperation(numbers, operations[index]) }
}

fun List<String>.splitOnBlanks() = fold(mutableListOf(mutableListOf<String>())) { acc, number ->
    if (number.isBlank()) acc.add(mutableListOf()) else acc.last().add(number)
    acc
}

private fun String.toCephalopodOperations() =
    splitLines().fold(listOf<String>()) { acc, list ->
        if (acc.isEmpty()) list
        else (acc zip list).map { it.first + " " + it.second }
    }.map { list ->
        val line = list.split(" ")
        CephalopodOperation(line.dropLast(1), line.last())
    }

data class CephalopodOperation(private val numbers: List<String>, private val operation: String) {
    private val operationFunction: (Long, Long) -> Long = when (operation) {
        "+" -> { a, b -> a + b }
        "*" -> { a, b -> a * b }
        else -> error("illegal operation")
    }

    operator fun invoke() =
        numbers.foldIndexed(numbers.first().toLong()) { index, acc, number ->
            if (index == 0) acc
            else operationFunction(acc, number.toLong())
        }
}

private fun String.splitLines() = cleanUp()
    .split("\r\n")
    .map { it.trim().split(" ") }

private fun String.cleanUp() = trim()
    .replace("        ", " ")
    .replace("      ", " ")
    .replace("    ", " ")
    .replace("   ", " ")
    .replace("  ", " ")
