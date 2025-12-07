package be.sukes13.aoc2025.day5

import be.sukes13.aoc2025.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay5Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day5/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(3)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day5/input.txt")
        assertThat(solve1(input)).isEqualTo(739)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day5/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(0)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day5/input.txt")
        assertThat(solve2(input)).isEqualTo(0)
    }

    private fun solve1(input: String) = part1(input)
    private fun solve2(input: String): Int = part2(input)

}