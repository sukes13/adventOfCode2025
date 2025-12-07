package be.sukes13.aoc2025.day6

import be.sukes13.aoc2025.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay6Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day6/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(4277556L)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day6/input.txt")
        assertThat(solve1(input)).isEqualTo(4449991244405L)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day6/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(3263827L)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day6/input.txt")
        assertThat(solve2(input)).isEqualTo(9348430857627L) //too high
    }

    private fun solve1(input: String) = part1(input)
    private fun solve2(input: String) = part2(input)

}