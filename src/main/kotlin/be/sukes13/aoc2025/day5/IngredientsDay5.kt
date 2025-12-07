package be.sukes13.aoc2025.day5

import be.sukes13.aoc2025.mapLines
import be.sukes13.aoc2025.splitOnEmptyLine

fun part1(input: String) = input.toIngredientModel().freshIngredients().size

fun part2(input: String) = input.toIngredientModel().allFreshIngredients().sum()

data class IngredientModel(private val freshRanges: List<LongRange>, private val ingredients: List<String>) {
    fun freshIngredients() = ingredients.mapNotNull { it.isFresh(freshRanges) }

    fun allFreshIngredients() =
        freshRanges.sortedBy { it.first }
            .fold(mutableListOf<MutableList<Long>>()) { mergedRanges, range ->
                mergedRanges.mergeIn(range)
            }.map { it.last() - it.first() + 1 }

    private fun String.isFresh(freshRanges: List<LongRange>): String? = run breaker@{
        freshRanges.forEach {
            if (toLong() in it) return@breaker this
        }
        return null
    }

    private fun MutableList<MutableList<Long>>.mergeIn(range: LongRange): MutableList<MutableList<Long>> {
        if (isNotEmpty() && last()[1] >= range.first) {
           last()[1] = maxOf( range.last , last()[1])
        } else {
            add(mutableListOf(range.first, range.last))
        }
        return this
    }

}

private fun String.toIngredientModel() = splitOnEmptyLine().let { splitted ->
    IngredientModel(
        freshRanges = splitted.first().mapLines { rangeString ->
            rangeString.split("-").let { (it.first().toLong()..it.last().toLong()) }
        },
        ingredients = splitted.last().mapLines { it }
    )
}