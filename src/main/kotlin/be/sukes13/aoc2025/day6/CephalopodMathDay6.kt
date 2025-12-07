package be.sukes13.aoc2025.day6

fun part1(input: String) = input.toCephalopodOperations().sumOf { it() }

fun part2(input: String) = 0

class CephalopodOperation(private val numbers: List<Long>, operation: String) {
    private val operation: (Long, Long) -> Long = when (operation) {
        "+" -> { a, b -> a + b }
        "*" -> { a, b -> a * b }
        else -> error("illegal operation")
    }

    operator fun invoke() =
        numbers.foldIndexed(numbers.first()) { index, acc, number ->
            if (index == 0) acc
            else operation(acc, number)
        }
}

private fun String.toCephalopodOperations() =
    splitLines().fold(listOf<String>()) { acc, list ->
        if (acc.isEmpty()) list
        else (acc zip list).map { it.first + " " + it.second }
    }.map { list ->
        val line = list.split(" ")
        CephalopodOperation(line.dropLast(1).map { it.toLong() }, line.last())
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
