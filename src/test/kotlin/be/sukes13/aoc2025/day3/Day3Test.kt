package be.sukes13.aoc2025.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource

class Day3Test {

    @ParameterizedTest
    @MethodSource("part2")
    fun `check part2`(rack: List<Int>, result: Long) {
        assertThat(rack.turnOnBatteries2(12)).isEqualTo(result)
    }

    companion object {
        @JvmStatic
        fun part2() = listOf(
            of("987654321111111".toRack(), 987654321111L),
            of("811111111111119".toRack(), 811111111119L),
            of("234234234234278".toRack(), 434234234278L),
            of("818181911112111".toRack(), 888911112111L),
        )
    }
}

