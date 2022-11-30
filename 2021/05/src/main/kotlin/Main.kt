import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

fun task1(input: Collection<Pair<Pair<Int, Int>, Pair<Int, Int>>>) {
    val result = input
        .filter { it.first.first == it.second.first || it.first.second == it.second.second }
        .flatMap { (start, end) ->
            if (start.first == end.first)
                (min(start.second, end.second)..max(start.second, end.second))
                    .map { start.first to it }
            else
                (min(start.first, end.first)..max(start.first, end.first))
                    .map { it to start.second }
        }
        .groupingBy { it }
        .eachCount()
        .filterValues { it >= 2 }
        .count()

    println("task-1: $result")
}

fun task2(input: Collection<Pair<Pair<Int, Int>, Pair<Int, Int>>>) {
    val result = input
        .flatMap { (start, end) ->
            when ((start.first - end.first).coerceIn(-1, 1) to (start.second - end.second).coerceIn(-1, 1)) {
                1 to 0, -1 to 0 -> (min(start.first, end.first)..max(start.first, end.first))
                    .map { it to start.second }
                0 to 1, 0 to -1 -> (min(start.second, end.second)..max(start.second, end.second))
                    .map { start.first to it }
                1 to -1, 1 to 1 -> (end.first..start.first)
                    .mapIndexed { index, x -> x to end.second + if (start.second < end.second) -index else index }
                -1 to 1, -1 to -1 -> (start.first..end.first)
                    .mapIndexed { index, x -> x to start.second + if (start.second < end.second) index else -index }
                else -> emptyList()
            }
        }
        .groupingBy { it }
        .eachCount()
        .filterValues { it >= 2 }
        .count()

    println("task-2: $result")
}

fun main(args: Array<String>) {
    val lines = File("/Users/lino/dev/advent-of-code-21/05/src/main/resources/1.txt").readLines()

    val input = lines
        .map { line ->
            line
                .split(" -> ")
                .map { pair ->
                    pair
                        .split(',')
                        .let { it[0].toInt() to it[1].toInt() }
                }
                .let { it[0] to it[1] }
        }

    task1(input)
    task2(input)
}
