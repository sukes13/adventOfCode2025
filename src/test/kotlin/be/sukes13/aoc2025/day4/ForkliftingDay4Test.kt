package be.sukes13.aoc2025.day4

import be.sukes13.aoc2025.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ForkliftingDay4Test {
    @Test
    fun `parse to rolls`() {
        val input = readFile("day4/exampleInput.txt")
        assertThat(input.toRolls()).contains(
            be.sukes13.aoc2025.day4.Spot(2, 0),
            be.sukes13.aoc2025.day4.Spot(3, 0),
            be.sukes13.aoc2025.day4.Spot(5, 0),
            be.sukes13.aoc2025.day4.Spot(1, 1),
            be.sukes13.aoc2025.day4.Spot(2, 1),
            be.sukes13.aoc2025.day4.Spot(8, 0),
            be.sukes13.aoc2025.day4.Spot(9, 1)
        )
        assertThat(input.toRolls()).doesNotContain(
            be.sukes13.aoc2025.day4.Spot(0, 0),
            be.sukes13.aoc2025.day4.Spot(1, 0),
            be.sukes13.aoc2025.day4.Spot(4, 0),
            be.sukes13.aoc2025.day4.Spot(3, 1),
            be.sukes13.aoc2025.day4.Spot(5, 1),
            be.sukes13.aoc2025.day4.Spot(9, 0),
            be.sukes13.aoc2025.day4.Spot(10, 1)
        )
    }

    @Test
    fun `find neighbours of 0,0`() {
        assertThat(be.sukes13.aoc2025.day4.Spot(0, 0).neighbours).containsExactlyInAnyOrder(
            be.sukes13.aoc2025.day4.Spot(-1, -1),
            be.sukes13.aoc2025.day4.Spot(0, -1),
            be.sukes13.aoc2025.day4.Spot(1, -1),
            be.sukes13.aoc2025.day4.Spot(-1, 0),
            be.sukes13.aoc2025.day4.Spot(1, 0),
            be.sukes13.aoc2025.day4.Spot(-1, 1),
            be.sukes13.aoc2025.day4.Spot(0, 1),
            be.sukes13.aoc2025.day4.Spot(1, 1)
        )
    }

    @Test
    fun `find neighbours of 0,1`() {
        assertThat(be.sukes13.aoc2025.day4.Spot(1, 0).neighbours).containsExactlyInAnyOrder(
            be.sukes13.aoc2025.day4.Spot(0, -1),
            be.sukes13.aoc2025.day4.Spot(1, -1),
            be.sukes13.aoc2025.day4.Spot(2, -1),
            be.sukes13.aoc2025.day4.Spot(0, 0),
            be.sukes13.aoc2025.day4.Spot(2, 0),
            be.sukes13.aoc2025.day4.Spot(0, 1),
            be.sukes13.aoc2025.day4.Spot(1, 1),
            be.sukes13.aoc2025.day4.Spot(2, 1)
        )
    }

    @Test
    fun `number of roll-neighbours`() {
        val rolls = readFile("day4/exampleInput.txt").toRolls()

        assertThat(be.sukes13.aoc2025.day4.Spot(0, 0).numberOfNeighbouringRollsIn(rolls)).isEqualTo(2)
        assertThat(be.sukes13.aoc2025.day4.Spot(1, 0).numberOfNeighbouringRollsIn(rolls)).isEqualTo(4)
        assertThat(be.sukes13.aoc2025.day4.Spot(2, 0).numberOfNeighbouringRollsIn(rolls)).isEqualTo(3)
        assertThat(be.sukes13.aoc2025.day4.Spot(1, 1).numberOfNeighbouringRollsIn(rolls)).isEqualTo(6)
    }

    @Test
    fun `can visualize example input`() {
        val input = readFile("day4/exampleInput.txt").trimIndent()

        assertThat(input.toRolls().visualize()).isEqualTo(input)
    }

    @Test
    fun `check after one turn of removing rolls`() {
        val input = readFile("day4/exampleInput.txt")

        val actual = input.toRolls().removeRolls()

        assertThat(actual.first).isEqualTo(13)
        assertThat(actual.second.visualize()).isEqualTo(
            """.......@..
.@@.@.@.@@
@@@@@...@@
@.@@@@..@.
.@.@@@@.@.
.@@@@@@@.@
.@.@.@.@@@
..@@@.@@@@
.@@@@@@@@.
....@@@...""".trimIndent()
        )
    }

    @Test
    fun `check after turn two of removing rolls`() {
        val input = readFile("day4/exampleInput.txt")

        val afterTurnOne = input.toRolls().removeRolls()
        val afterTurnTwo = afterTurnOne.second.removeRolls()

        assertThat(afterTurnTwo.first).isEqualTo(12)
        assertThat(afterTurnTwo.second.visualize()).isEqualTo(
            """..........
.@@.....@.
.@@@@...@@
..@@@@....
.@.@@@@...
..@@@@@@..
...@.@.@@@
..@@@.@@@@
..@@@@@@@.
....@@@...""".trimIndent()
        )
    }

}
