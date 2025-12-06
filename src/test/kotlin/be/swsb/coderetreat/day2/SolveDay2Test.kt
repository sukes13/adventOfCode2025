package be.swsb.coderetreat.day2

import be.swsb.coderetreat.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay2Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day2/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(1227775554L)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day2/input.txt")
        assertThat(solve1(input)).isEqualTo(29818212493L)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day2/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(4174379265)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day2/input.txt")
        assertThat(solve2(input)).isEqualTo(37432260594L)
    }

    private fun solve1(input: String): Long = part1(input)
    private fun solve2(input: String): Long = part2(input)

}