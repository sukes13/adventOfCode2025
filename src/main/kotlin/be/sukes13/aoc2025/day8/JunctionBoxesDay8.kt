package be.sukes13.aoc2025.day8

import be.sukes13.aoc2025.mapLines
import kotlin.math.pow

fun part1(input: String, numberOfCircuits: Int) = input.toCircuitSet()
    .circuitsAfterConnecting(numberOfConnections = numberOfCircuits)
    .map { it.size }
    .sortedByDescending { it }
    .take(3)
    .reduce { a, b -> a * b }

fun part2(input: String) = input.toCircuitSet()
    .lastConnectionToConnectAllJunctions()
    .let { it.first.x.toLong() * it.second.x.toLong() }

class CircuitSet(junctions: List<Junction>) {
    private val parent = junctions.associateWith { it }.toMutableMap()
    private val junctionPairs = toSortedNormalizedJunctionPairs(junctions)

    fun lastConnectionToConnectAllJunctions(): Pair<Junction, Junction> {
        var index = 0
        while (circuits().size != 1) {
            junctionPairs[index].connect()
            index++
        }
        return junctionPairs[index - 1]
    }

    fun circuitsAfterConnecting(numberOfConnections: Int): List<List<Junction>> {
        (0 until numberOfConnections).forEach { index ->
            junctionPairs[index].connect()
        }
        return circuits()
    }

    private fun Pair<Junction, Junction>.connect() {
        let { (junction1, junction2) ->
            if (!inSameCircuit(junction1, junction2))
                union(junction1, junction2)
        }
    }

    private fun circuits(): List<List<Junction>> {
        val groups = mutableMapOf<Junction, MutableList<Junction>>()
        parent.keys.forEach { junction ->
            groups.computeIfAbsent(find(junction)) { mutableListOf() }.add(junction)
        }
        return groups.values.map { it.toList() }
    }

    private fun find(junction: Junction): Junction {
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

    private fun toSortedNormalizedJunctionPairs(junctions: List<Junction>) =
        junctions.flatMap {
            junctions.mapIndexedNotNull { index, _ ->
                val secondJunction = junctions.getOrNull(index + 1) ?: junctions[0]
                if (it != secondJunction) it.normalizedPairWith(secondJunction) else null
            }
        }.distinct().sortedBy { (junction1, junction2) -> junction1.distanceTo(junction2) }

}

data class Junction(val x: Double, val y: Double, val z: Double) {
    fun distanceTo(other: Junction) =
        (x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2)

    fun normalizedPairWith(other: Junction) =
        if (x + y + z < (other.x + other.y + other.z)) this to other
        else other to this
}


private fun String.toCircuitSet() = CircuitSet(
    junctions = mapLines { line ->
        line.split(",")
            .map { it.toDouble() }
            .let { Junction(it[0], it[1], it[2]) }
    }
)
