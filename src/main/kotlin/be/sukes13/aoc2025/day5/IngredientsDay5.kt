package be.sukes13.aoc2025.day5

import be.sukes13.aoc2025.mapLines
import be.sukes13.aoc2025.splitOnEmptyLine

fun part1(input: String): Int = input.toIngredientModel().freshIngredients().size

fun part2(input: String): Int = 0


data class IngredientModel(private val freshRanges: List<LongRange>, private val ingredients: List<String>) {
    fun freshIngredients() = ingredients.mapNotNull { it.isFresh(freshRanges) }

    private fun String.isFresh(freshRanges: List<LongRange>): String? = run breaker@{
        freshRanges.forEach {
            if (toLong() in it) return@breaker this
        }
        return null
    }
}

private fun String.toIngredientModel() = splitOnEmptyLine().let { splitted ->
    IngredientModel(
        freshRanges = splitted.first().mapLines { rangeString ->
            rangeString.split("-").let {
                (it.first().toLong()..it.last().toLong())
            }
        },
        ingredients = splitted.last().mapLines { it }
    )
}

