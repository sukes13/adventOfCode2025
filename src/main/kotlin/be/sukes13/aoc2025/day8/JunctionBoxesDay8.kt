package be.sukes13.aoc2025.day8

import be.sukes13.aoc2025.mapLines
import kotlin.collections.getOrNull
import kotlin.collections.mapIndexedNotNull
import kotlin.math.pow

fun part1(input: String, numberOfCircuits: Int) = input.toJunctions()
    .connectCircuits(numberOfConnections = numberOfCircuits)
    .map { it.size }
    .sortedByDescending { it }
    .take(3)
    .reduce { a, b -> a * b }

fun part2(input: String) = input.toJunctions()
    .connectAllCircuits()
    .let{ it.first.x.toLong() * it.second.x.toLong() }

fun Array<Junction>.connectAllCircuits(): Pair<Junction, Junction> {
    val circuitSet = CircuitSet(this)
    val junctionPairs = toSortedNormalizedJunctionPairs()

    var indexOfJunctionPair = 0
    while(circuitSet.circuits().size != 1){
        circuitSet.connectOneCircuit(junctionPairs, indexOfJunctionPair)
        indexOfJunctionPair++
    }

    return junctionPairs[indexOfJunctionPair-1]
}

private fun Array<Junction>.connectCircuits(numberOfConnections: Int): List<List<Junction>> {
    val circuitSet = CircuitSet(this)
    val junctionPairs = toSortedNormalizedJunctionPairs()

    (0 until numberOfConnections).forEach { index ->
        circuitSet.connectOneCircuit(junctionPairs, index)
    }

    return circuitSet.circuits()
}

private fun Array<Junction>.toSortedNormalizedJunctionPairs() =
    flatMap {
        mapIndexedNotNull { index, _ ->
            val secondJunction = getOrNull(index + 1) ?: this[0]
            if (it != secondJunction) it.normalizePair(secondJunction) else null
        }
    }.distinct()
        .sortedBy { (junction1, junction2) -> junction1.distanceTo(junction2) }

data class Junction(val x: Double, val y: Double, val z: Double) {
    fun distanceTo(other: Junction) =
        (x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2)

    fun normalizePair(other: Junction) =
        if (x + y + z < (other.x + other.y + other.z)) this to other
        else other to this
}

class CircuitSet(junctions: Array<Junction>) {
    private val parent = junctions.associateWith { it }.toMutableMap()

    fun connectOneCircuit(junctionPairs: List<Pair<Junction, Junction>>, index: Int) {
        junctionPairs[index].let { (junction1, junction2) ->
            if (!inSameCircuit(junction1, junction2)) {
                union(junction1, junction2)
            }
        }
    }

    fun circuits(): List<List<Junction>> {
        val groups = mutableMapOf<Junction, MutableList<Junction>>()
        parent.keys.forEach { junction ->
            groups.computeIfAbsent(find(junction)) { mutableListOf() }.add(junction)
        }
        return groups.values.map { it.toList() }
    }

    private  fun find(junction: Junction): Junction {
        if (parent[junction] != junction)
            parent[junction] = find(parent[junction]!!)
        return parent[junction]!!
    }

    private fun union(junction1: Junction, junction2: Junction) {
        val parent1 = find(junction1)
        val parent2 = find(junction2)
        if (parent1 != parent2) parent[parent1] = parent2
    }

    private fun inSameCircuit(junction1: Junction, junction2: Junction) =
        find(junction1) == find(junction2)
}


private fun String.toJunctions(): Array<Junction> = mapLines { line ->
    line.split(",")
        .map { it.toDouble() }
        .let { Junction(it[0], it[1], it[2]) }
}.toTypedArray()
