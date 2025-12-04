package be.swsb.coderetreat.day1

import be.swsb.coderetreat.day1.part1
import be.swsb.coderetreat.day1.part2
import be.swsb.coderetreat.readFile
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SolveDay1Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day1/exampleInput.txt")
        Assertions.assertThat(solve1(input)).isEqualTo(3)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day1/input.txt")
        Assertions.assertThat(solve1(input)).isEqualTo(0)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day1/exampleInput.txt")
        Assertions.assertThat(solve2(input)).isEqualTo(0)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day1/input.txt")
        Assertions.assertThat(solve2(input)).isEqualTo(0)
    }

    private fun solve1(input: String): Int = part1(input)
    private fun solve2(input: String): Int = part2(input)

}