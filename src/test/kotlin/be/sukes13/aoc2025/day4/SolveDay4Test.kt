package be.sukes13.aoc2025.day4

import be.sukes13.aoc2025.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay4Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day4/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(13)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day4/input.txt")
        assertThat(solve1(input)).isEqualTo(1451)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day4/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(43)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day4/input.txt")
        assertThat(solve2(input)).isEqualTo(8701)
    }

    private fun solve1(input: String): Int = be.sukes13.aoc2025.day4.part1(input)
    private fun solve2(input: String): Int = be.sukes13.aoc2025.day4.part2(input)

}