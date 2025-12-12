package be.sukes13.aoc2025.day8

import be.sukes13.aoc2025.mapLines
import kotlin.math.pow

fun part1(input: String) = input.toJunctions().connectCircuits()

fun part2(input: String) = 0

class UnionFind(junctions: Array<Junction>) {
    private val parent = junctions.associateWith { it }.toMutableMap()

    fun find(junction: Junction): Junction {
        if (parent[junction] != junction)
            parent[junction] = find(parent[junction]!!)
        return parent[junction]!!
    }

    fun union(junction1: Junction, junction2: Junction) {
        val parent1 = find(junction1)
        val parent2 = find(junction2)
        if (parent1 != parent2) parent[parent1] = parent2
    }

    fun inSameCircuit(junction1: Junction, junction2: Junction) =
        find(junction1) == find(junction2)
}


private fun String.toJunctions(): Array<Junction> = mapLines { line ->
    line.split(",")
        .map { it.toDouble() }
        .let { Junction(it[0], it[1], it[2]) }
}.toTypedArray()

data class Circuit(val junctions:  List<Junction>)

data class Junction(val x: Double, val y: Double, val z: Double) {
    fun distanceTo(junction: Junction) =
        (x - junction.x).pow(2) + (y - junction.y).pow(2) + (z - junction.z).pow(2)
}