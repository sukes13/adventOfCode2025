package be.swsb.coderetreat

import java.util.concurrent.atomic.DoubleAccumulator

fun readFile(fileName: String): String =
    {}::class.java.classLoader.getResourceAsStream(fileName)?.reader()?.readText() ?: error("Could not load $fileName")

fun <T> String.mapLines(variant: (String) -> T) = lines().map(variant)

fun <T, ACC> String.foldOverLinesFrom(accumulator: ACC, variant: (ACC,T) -> ACC) = lines().fold(accumulator, variant as (acc: ACC, String) -> ACC)

fun <T> String.flatMapLines(variant: (String) -> Iterable<T>) = lines().flatMap(variant)

fun String.filterLines(variant: (String) -> Boolean) = lines().filter(variant)

fun String.splitOnEmptyLine() = split("\r\n\r\n")

fun <T> Iterable<Set<T>>.overlap(): Set<T> =
    fold(first().toSet()) { shared, element ->
        shared intersect element.toSet()
    }