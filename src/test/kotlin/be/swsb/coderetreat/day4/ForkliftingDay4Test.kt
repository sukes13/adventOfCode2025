package be.swsb.coderetreat.day4

import be.swsb.coderetreat.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ForkliftingDay4Test {
    @Test
    fun `parse to rolls`() {
        val input = readFile("day4/exampleInput.txt")
        assertThat(input.toRolls()).contains(Spot(2, 0), Spot(3, 0), Spot(5, 0), Spot(1, 1), Spot(2, 1), Spot(8, 0), Spot(9, 1))
        assertThat(input.toRolls()).doesNotContain(Spot(0, 0), Spot(1, 0), Spot(4, 0), Spot(3, 1), Spot(5, 1), Spot(9, 0), Spot(10, 1))
    }

    @Test
    fun `find neighbours of 0,0`() {
        assertThat(Spot(0, 0).neighbours).containsExactlyInAnyOrder(
            Spot(-1, -1), Spot(0, -1), Spot(1, -1),
            Spot(-1, 0), Spot(1, 0),
            Spot(-1, 1), Spot(0, 1), Spot(1, 1)
        )
    }

    @Test
    fun `find neighbours of 0,1`() {
        assertThat(Spot(1, 0).neighbours).containsExactlyInAnyOrder(
            Spot(0, -1), Spot(1, -1), Spot(2, -1),
            Spot(0, 0), Spot(2, 0),
            Spot(0, 1), Spot(1, 1), Spot(2, 1)
        )
    }

    @Test
    fun `number of roll-neighbours`() {
        val rolls = readFile("day4/exampleInput.txt").toRolls()

        assertThat(Spot(0, 0).numberOfNeighbouringRollsIn(rolls)).isEqualTo(2)
        assertThat(Spot(1, 0).numberOfNeighbouringRollsIn(rolls)).isEqualTo(4)
        assertThat(Spot(2, 0).numberOfNeighbouringRollsIn(rolls)).isEqualTo(3)
        assertThat(Spot(1, 1).numberOfNeighbouringRollsIn(rolls)).isEqualTo(6)
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
