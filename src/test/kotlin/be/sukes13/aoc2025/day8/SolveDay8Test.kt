package be.sukes13.aoc2025.day8

import be.sukes13.aoc2025.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay8Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day8/exampleInput.txt")
        assertThat(solve1(input, numberOfCircuits = 10)).isEqualTo(40)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day8/input.txt")
        assertThat(solve1(input, numberOfCircuits = 1000)).isEqualTo(122430)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day8/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(25272L)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day8/input.txt")
        assertThat(solve2(input)).isEqualTo(8135565324L)
    }

    private fun solve1(input: String, numberOfCircuits: Int) = part1(input, numberOfCircuits)
    private fun solve2(input: String) = part2(input)

}