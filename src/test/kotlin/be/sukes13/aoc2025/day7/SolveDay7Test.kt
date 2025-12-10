package be.sukes13.aoc2025.day7

import be.sukes13.aoc2025.Point
import be.sukes13.aoc2025.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay7Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day7/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(21)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day7/input.txt")
        assertThat(solve1(input)).isEqualTo(1605)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day7/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(40)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day7/input.txt")
        assertThat(solve2(input)).isEqualTo(29893386035180L) //413655020 too low
    }

    private fun solve1(input: String) = part1(input)
    private fun solve2(input: String) = part2(input)

}