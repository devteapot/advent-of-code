import java.io.File

fun task1(input: List<List<Int>>) {
    val result = input
        .foldIndexed(listOf<Int>()) { i, rowAcc, row ->
            rowAcc + row.foldIndexed(listOf()) { j, cellAcc, cell ->
                if (listOfNotNull(
                        input.getOrNull(i - 1)?.getOrNull(j),
                        input.getOrNull(i + 1)?.getOrNull(j),
                        input.getOrNull(i)?.getOrNull(j - 1),
                        input.getOrNull(i)?.getOrNull(j + 1),
                    )
                        .fold(true) { acc, curr -> acc && (curr > cell) }
                )
                    cellAcc + listOf(cell)
                else
                    cellAcc
            }
        }

    println("task-1: ${result.sumOf { it + 1 }}")
}

fun task2(input: List<List<Int>>) {
    fun lowEndpoint(i: Int, j: Int, input: List<List<Int>>): Pair<Int, Int> {
        val nearCells = listOf(
            input.getOrNull(i - 1)?.getOrNull(j) to (i - 1 to j),
            input.getOrNull(i + 1)?.getOrNull(j) to (i + 1 to j),
            input.getOrNull(i)?.getOrNull(j - 1) to (i to j - 1),
            input.getOrNull(i)?.getOrNull(j + 1) to (i to j + 1),
        )
            .filter { it.first != null }

        if (nearCells.fold(true) { acc, curr -> acc && (curr.first!! > input[i][j]) }) {
            return i to j
        }

        val localMin = nearCells
            .minByOrNull { it.first!! }!!.second

        return lowEndpoint(localMin.first, localMin.second, input)
    }


    val result = input.flatMapIndexed { i, row ->
        (row.indices).map { j -> i to j }
    }
        .filter { (i, j) -> input[i][j] != 9 }
        .map { (i, j) -> lowEndpoint(i, j, input) }
        .groupingBy { it }
        .eachCount()

    println(
        result
            .values
            .sorted()
            .reversed()
            .take(3)
            .fold(1) { acc, curr ->
                acc * curr
            }
    )
}

fun main(args: Array<String>) {
    val input = File("/Users/lino/dev/advent-of-code-21/9/src/main/resources/1.txt")
        .readLines()
        .map { line ->
            line
                .toCharArray()
                .map { it.toString().toInt() }
        }

    task1(input)
    task2(input)
}
