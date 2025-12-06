package be.sukes13.aoc2025.day2

fun part1(input: String) = input.toIdRanges().flatMap { it.invalidIdsPart1() }.sum()

fun part2(input: String) = input.toIdRanges().flatMap { it.invalidIdsPart2() }.sum()


data class IdRange(private val startId: Long, private val endId: Long) {
    private val rangeAsStrings = (startId..endId).map { it.toString() }

    fun invalidIdsPart1() = rangeAsStrings
        .filter { it.length % 2 == 0 }
        .filter { (it.length / 2).let { middle -> it.take(middle) == it.takeLast(middle) } }
        .map { it.toLong() }

    fun invalidIdsPart2() = rangeAsStrings
        .filter { it.isInvalidPart2() }
        .map { it.toLong() }

    private fun String.isInvalidPart2() = run breaker@{
        (1 until length).forEach {
            if (length % it == 0 && containsRepeatOfSize(it)) return@breaker true
        }
        return false
    }

    private fun String.containsRepeatOfSize(size: Int) = run breaker@{
        windowed(size, size).foldIndexed("") { index, previousSegment, segment ->
            when {
                index == 0 -> segment
                previousSegment == segment -> segment
                else -> return@breaker false
            }
        }
        return true
    }

}

fun String.toIdRanges() =
    split(",").map { range ->
        range.split("-").let{
            IdRange(it.first().toLong(), it.last().toLong())
        }
    }

