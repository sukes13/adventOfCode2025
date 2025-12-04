package be.swsb.coderetreat.day1

import be.swsb.coderetreat.mapLines

fun part1(input: String): Int {
    val turnings = input.toTurns()
    var pointer = Pointer(50)
    val allPointers = turnings.map{ turning ->
        pointer.move(turning).also{ pointer = it }
    }
    allPointers.forEach {println(it.position)}
   return allPointers.count{ it.position == 0}
}

sealed class Turning(val numberOfTurns : Int){
    abstract fun execute(startAt : Int) : Int

    class Right(numberOfTurns: Int) : Turning(numberOfTurns){
        override fun execute(startAt : Int) = startAt + numberOfTurns
    }
    class Left(numberOfTurns: Int) : Turning(numberOfTurns){
        override fun execute(startAt : Int) = startAt - numberOfTurns
    }
}

class Pointer(val position: Int) {

    fun move(turning: Turning): Pointer {
        val unboundPosition = turning.execute(position)

        val realPosition = if(unboundPosition > 99) unboundPosition%99
        else if(unboundPosition < 0) unboundPosition/99 - 100
        else unboundPosition
        return Pointer(unboundPosition % 99)
    }
}

fun part2(input: String): Int = -1

private fun String.toTurns(): List<Turning> = mapLines {
    val numberOfTurns = it.takeLast(it.length - 1).toInt()
    when (it.first()) {
        'R' -> Turning.Right(numberOfTurns)
        'L' -> Turning.Left(numberOfTurns)
        else -> error("illegal direction!")
    }
}
