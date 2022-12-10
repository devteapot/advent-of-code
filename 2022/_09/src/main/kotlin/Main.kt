import java.io.File

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun fromString(s: String) =
            when(s) {
                "U" -> UP
                "D" -> DOWN
                "L" -> LEFT
                "R" -> RIGHT
                else -> null
            }!!
    }
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
    first + other.first to second + other.second

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) =
    first - other.first to second - other.second

fun List<Pair<Int, Int>>.plot() {
    val (minX, maxX) = map(Pair<Int, Int>::first).let { it.min() to it.max() }
    val (minY, maxY) = map(Pair<Int, Int>::second).let { it.min() to it.max() }

    for (y in maxY downTo minY) {
        for (x in minX..maxX) {
            print(
                indexOfFirst { it == x to y }
                    .let {
                        when(it) {
                            0 -> "H"
                            -1 -> "."
                            else -> it.toString()
                        }
                    }
            )
        }
        println()
    }
}

fun part1(inputPath: String) =
    File(inputPath)
        .readLines()
        .map { line ->
            line
                .split(" ")
                .let { Direction.fromString(it[0]) to it[1].toInt() }
        }
        .fold(emptySet<Pair<Int, Int>>() to (Pair(0, 0) to Pair(0, 0))) { (visitedPositions, currentPositions), (direction, distance) ->
            val (head, tail) = currentPositions
            var (newHead, newTail) = head to tail

            val newPositions = mutableSetOf<Pair<Int, Int>>()

            repeat(distance) {
                newHead += when(direction) {
                    Direction.UP -> 0 to 1
                    Direction.DOWN -> 0 to -1
                    Direction.LEFT -> -1 to 0
                    Direction.RIGHT -> 1 to 0
                }

                newTail += when(newHead - newTail) {
                    0 to 2 -> 0 to 1
                    0 to -2 -> 0 to -1
                    2 to 0 -> 1 to 0
                    -2 to 0 -> -1 to 0

                    -2 to -1 -> -1 to -1
                    -2 to 1 -> -1 to 1
                    2 to -1 -> 1 to -1
                    2 to 1 -> 1 to 1

                    -1 to -2 -> -1 to -1
                    1 to -2 -> 1 to -1
                    -1 to 2 -> -1 to 1
                    1 to 2 -> 1 to 1
                    else -> 0 to 0
                }

                newPositions.add(newTail)
            }

            visitedPositions.union(newPositions) to (newHead to newTail)
        }
            .first
            .size

fun part2(inputPath: String) =
    File(inputPath)
        .readLines()
        .map { line ->
            line
                .split(" ")
                .let { Direction.fromString(it[0]) to it[1].toInt() }
        }
        .fold(emptySet<Pair<Int, Int>>() to MutableList(10) { 0 to 0 }) { (visitedPositions, currentPositions), (direction, distance) ->
            val newPositions = mutableSetOf<Pair<Int, Int>>()

            repeat(distance) {
                currentPositions[0] += when(direction) {
                    Direction.UP -> 0 to 1
                    Direction.DOWN -> 0 to -1
                    Direction.LEFT -> -1 to 0
                    Direction.RIGHT -> 1 to 0
                }

                repeat(currentPositions.size - 1) { index ->
                    currentPositions[index + 1] += when(currentPositions[index] - currentPositions[index + 1]) {
                        0 to 2 -> 0 to 1
                        0 to -2 -> 0 to -1
                        2 to 0 -> 1 to 0
                        -2 to 0 -> -1 to 0

                        -2 to -1 -> -1 to -1
                        -2 to 1 -> -1 to 1
                        2 to -1 -> 1 to -1
                        2 to 1 -> 1 to 1

                        -1 to -2 -> -1 to -1
                        1 to -2 -> 1 to -1
                        -1 to 2 -> -1 to 1
                        1 to 2 -> 1 to 1

                        2 to 2 -> 1 to 1
                        -2 to -2 -> -1 to -1
                        2 to -2 -> 1 to -1
                        -2 to 2 -> -1 to 1
                        else -> 0 to 0
                    }
                }

                newPositions.add(currentPositions.last())
            }

            visitedPositions.union(newPositions) to currentPositions
        }
            .first
            .size

fun main() {
    println("part 1: ${part1("test.in")}")
    println("part 2: ${part2("test.in")}")
}
