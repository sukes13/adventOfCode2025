package be.sukes13.aoc2025.day3

import be.sukes13.aoc2025.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay3Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day3/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(357)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day3/input.txt")
        assertThat(solve1(input)).isEqualTo(17113)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day3/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(3121910778619L)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day3/input.txt")
        assertThat(solve2(input)).isEqualTo(169709990062889L)
    }

    private fun solve1(input: String) = part1(input)
    private fun solve2(input: String) = part2(input)

}