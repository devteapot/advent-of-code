import java.io.File
import kotlin.math.floor
import kotlin.streams.toList

fun part1(inputPath: String): Int {
    fun List<List<Int>>.search(from: Pair<Int, Int>, to: Pair<Int, Int>): Int {
        var nodes = setOf(from)
        val visited = mutableSetOf<Pair<Int, Int>>()
        var count = 0

        while (to !in nodes) {
            visited.addAll(nodes)
            nodes = nodes
                .flatMap { current ->
                    listOf(
                        current.first - 1 to current.second,
                        current.first + 1 to current.second,
                        current.first to current.second - 1,
                        current.first to current.second + 1,
                    )
                        .filter { candidate ->
                            (getOrNull(candidate.second)
                                ?.getOrNull(candidate.first)
                                ?.let { it <= this[current.second][current.first] + 1 }
                                ?: false) && candidate !in visited
                        }
                }
                .toSet()
            count++
        }

        return count
    }

    return File(inputPath)
        .readText()
        .let { input ->
            val lines = input.lines().map { it.chars().toList() }
            val start = floor(input.indexOf("S").toFloat() / lines[0].size).toInt().let { lines[it].indexOf('S'.code) to it }
            val end = floor(input.indexOf("E").toFloat() / lines[0].size).toInt().let { lines[it].indexOf('E'.code) to it }

            input.replace("S", "a").replace("E", "z").lines().map { it.chars().toList() }.search(start, end)
        }
}

fun part2(inputPath: String): Int {
    fun List<List<Int>>.search(from: Set<Pair<Int, Int>>, to: Pair<Int, Int>): Int {
        var nodes = from
        val visited = mutableSetOf<Pair<Int, Int>>()
        var count = 0

        while (to !in nodes) {
            visited.addAll(nodes)
            nodes = nodes
                .flatMap { current ->
                    listOf(
                        current.first - 1 to current.second,
                        current.first + 1 to current.second,
                        current.first to current.second - 1,
                        current.first to current.second + 1,
                    )
                        .filter { candidate ->
                            (getOrNull(candidate.second)
                                ?.getOrNull(candidate.first)
                                ?.let { it <= this[current.second][current.first] + 1 }
                                ?: false) && candidate !in visited
                        }
                }
                .toSet()
            count++
        }

        return count
    }

    return File(inputPath)
        .readText()
        .let { input ->
            val lines = input.replace("S", "a").lines().map { it.chars().toList() }
            val start = lines
                .flatMapIndexed { y, line ->
                    line.mapIndexedNotNull { x, c -> if (c == 'a'.code) x to y else null }
                }
                .toSet()
            val end = floor(input.indexOf("E").toFloat() / lines[0].size).toInt().let { lines[it].indexOf('E'.code) to it }

            input.replace("S", "a").replace("E", "z").lines().map { it.chars().toList() }.search(start, end)
        }
}

fun main() {
    println("part 1: ${part1("test.in")}")
    println("part 2: ${part2("test.in")}")
}
