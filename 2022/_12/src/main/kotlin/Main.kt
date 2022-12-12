import java.io.File
import kotlin.math.floor
import kotlin.streams.toList

fun part1(inputPath: String): Int {
    fun List<List<Int>>.search(from: Pair<Int, Int>, to: Pair<Int, Int>, visited: Set<Pair<Int, Int>> = emptySet(), step: Int = 0): Int {
        if (from == to) return step

        return listOf(
            (from.first - 1 to from.second) to getOrNull(from.second)?.getOrNull(from.first - 1),
            (from.first + 1 to from.second) to getOrNull(from.second)?.getOrNull(from.first + 1),
            (from.first to from.second - 1) to getOrNull(from.second - 1)?.getOrNull(from.first),
            (from.first to from.second + 1) to getOrNull(from.second + 1)?.getOrNull(from.first),
        )
            .asSequence()
            .filter { it.second != null }
            .filter {
                getOrNull(from.second)?.getOrNull(from.first)?.let { current ->
                    it.second!! <= current + 1
                } ?: false
            }
            .map { it.first }
            .filter { it !in visited }
            .map { candidate ->
                search(candidate, to, visited + from, step + 1)
            }
            .filter { it > 0 }
            .minOrNull() ?: -1
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

fun part2(inputPath: String) =
    File(inputPath)
        .let { "TODO" }

fun main() {
    println("part 1: ${part1("test.in")}")
    println("part 2: ${part2("example.in")}")
}
