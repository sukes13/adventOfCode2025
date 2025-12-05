package be.swsb.coderetreat.day1

import be.swsb.coderetreat.mapLines
import kotlin.math.absoluteValue

fun part1(input: String): Int {
    val turnings = input.toTurns()
    var pointer = Pointer(50)
    val allPointers = turnings.map{ turning ->
        pointer.move(turning).also{ pointer = it }
    }

   return allPointers.count{ it.position == 0 }
}

fun part2(input: String): Int {
    val turnings = input.toTurns()
    var pointer = Pointer(50)
    val allPointers = turnings.map{ turning ->
        pointer.move(turning).also{ pointer = it }
    }
    allPointers.forEach { println("Pos = ${it.position} and Zeros = ${it.numberOfZeros}") }
    return allPointers.sumOf { it.numberOfZeros }
}

sealed class Turning(val numberOfTurns : Int){

    abstract operator fun invoke(startAt : Int) : Pair<Int, Int>

    class Right(numberOfTurns: Int) : Turning(numberOfTurns){
        override fun invoke(startAt : Int): Pair<Int, Int> {
            val i = (startAt + numberOfTurns) /100
            val numberOfZs = if(startAt!=0 && i >= 0) i else 0
            return startAt + numberOfTurns to numberOfZs
        }
    }

    class Left(numberOfTurns: Int) : Turning(numberOfTurns){
        override fun invoke(startAt : Int): Pair<Int, Int> {
            val i = startAt - numberOfTurns
            val numberOfZs = if(startAt!=0 && i <= 0) (i.absoluteValue/100) + 1 else 0
            return startAt - numberOfTurns to numberOfZs
        }
    }
}

class Pointer(val position: Int, val numberOfZeros : Int = 0) {
    fun move(turning: Turning): Pointer {
        val turning1 = turning(position)
        return Pointer((turning1.first).mod(100), numberOfZeros = turning1.second)
    }
}

private fun String.toTurns(): List<Turning> = mapLines {
    val numberOfTurns = it.takeLast(it.length - 1).toInt()
    when (it.first()) {
        'R' -> Turning.Right(numberOfTurns)
        'L' -> Turning.Left(numberOfTurns)
        else -> error("illegal direction!")
    }
}
