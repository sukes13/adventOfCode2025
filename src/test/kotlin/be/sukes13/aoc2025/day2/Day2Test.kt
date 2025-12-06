package be.sukes13.aoc2025.day2

import be.sukes13.aoc2025.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Day2Test {
    @Test
    fun `parse to idRanges`() {
        val input = readFile("day2/exampleInput.txt")
        assertThat(input.toIdRanges()).contains(IdRange(11L, 22L), IdRange(998L,1012L), IdRange(1188511880L,1188511890L), IdRange(824824821L,824824827L),)
    }

    @ParameterizedTest
    @MethodSource("part2")
    fun `check part2`(idRange: IdRange,invalidIds : List<Long>) {
        assertThat(idRange.invalidIdsPart2()).isEqualTo(invalidIds)
    }

    companion object{
        @JvmStatic
        fun part2() = listOf(
            Arguments.of(IdRange(11L, 22L),listOf(11L,22L)),
            Arguments.of( IdRange(95L,115L),listOf(99L,111L)),
            Arguments.of( IdRange(998L,1012L),listOf(999L,1010L)),
            Arguments.of( IdRange(1188511880L,1188511890L),listOf(1188511885L)),
            Arguments.of( IdRange(222220L,222224L),listOf(222222L)),
            Arguments.of( IdRange(1698522L,1698528L), emptyList<Long>()),
            Arguments.of( IdRange(824824821L,824824827L), listOf(824824824L)),
            Arguments.of( IdRange(2121212118L,2121212124L), listOf(2121212121L)),
        )
    }
}

