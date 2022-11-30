import java.io.File

val nearCells = (-1..1)
    .toList()
    .let { asd ->
        asd.flatMap { i ->
            asd.map { j -> i to j }
        }
    }
    .filter { (i, j) -> i != 0 || j != 0 }

fun getNearCells(cell: Pair<Int, Int>, input: List<List<Int>>) = cell.let { (i, j) ->
    nearCells.mapNotNull { (adjI, adjJ) ->
        input.getOrNull(i + adjI)?.getOrNull(j + adjJ)?.let { adjI to adjJ }
    }
}

fun task1(input: List<List<Int>>) {
    var flashes = 0

    fun simulation(input: List<List<Int>>): List<List<Int>> {
        if (input.flatten().none { it > 9 }) {
            return input
        }

        val result = input
            .map { row ->
                row.map { if (it > 9) 0 else it }
            }
            .map(List<Int>::toMutableList)

        input.flatMapIndexed { i, row ->
            row
                .mapIndexed { j, value -> j to value }
                .filter { it.second > 9 }
                .map { (j, _) -> i to j }
        }
            .forEach { (i, j) ->
                flashes += 1

                result[i][j] = 0

                getNearCells(i to j, input)
                    .forEach { (adjI, adjJ) ->
                        if (result[i + adjI][j + adjJ] != 0)
                            result[i + adjI][j + adjJ] += 1
                    }
            }

        return simulation(result)
    }

    var result = input
    repeat(100) {
        result = result.map { row -> row.map { cell -> cell + 1 } }
        result = simulation(result)
    }

    println("task-1: $flashes")
}

fun task2(input: List<List<Int>>) {
    fun simulation(input: List<List<Int>>): List<List<Int>> {
        if (input.flatten().none { it > 9 }) {
            return input
        }

        val result = input
            .map { row ->
                row.map { if (it > 9) 0 else it }
            }
            .map(List<Int>::toMutableList)

        input.flatMapIndexed { i, row ->
            row
                .mapIndexed { j, value -> j to value }
                .filter { it.second > 9 }
                .map { (j, _) -> i to j }
        }
            .forEach { (i, j) ->
                result[i][j] = 0

                getNearCells(i to j, input)
                    .forEach { (adjI, adjJ) ->
                        if (result[i + adjI][j + adjJ] != 0)
                            result[i + adjI][j + adjJ] += 1
                    }
            }

        return simulation(result)
    }

    var iteration = 1
    var result = input
    while (!result.flatten().all { it > 9 }) {
        result = simulation(result)
        iteration += 1
    }

    println("task-2: $iteration")
}

fun main(args: Array<String>) {
    val input = File("/Users/lino/dev/advent-of-code-21/11/src/main/resources/0.txt")
        .readLines()
        .map(String::toList)
        .map { line ->
            line
                .map(Char::toString)
                .map(String::toInt)
        }

    task1(input)
    task2(input)
}
