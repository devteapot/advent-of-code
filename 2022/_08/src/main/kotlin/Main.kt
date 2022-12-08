import java.io.File

fun Char.intValue() = toString().toInt()

fun List<Int>.viewDistance(from: Int) =
    fold(0 to false) { (distance, wasBlocked), curr ->
        if(wasBlocked) return@fold distance to true
        distance + 1 to (from <= curr)
    }
        .first

fun part1(inputPath: String) =
    File(inputPath)
        .readLines()
        .map { it.toCharArray().toList().map(Char::intValue) }
        .let { rows ->
            rows
                .drop(1)
                .dropLast(1)
                .mapIndexed { r, row ->
                    row
                        .drop(1)
                        .dropLast(1)
                        .mapIndexed { c, treeHeight ->
                            val rowIdx = r + 1
                            val colIdx = c + 1

                            listOf(
                                row.subList(0, colIdx),
                                row.subList(colIdx + 1, row.size),
                                (0 until rowIdx).map { rows[it][colIdx] },
                                (rowIdx + 1 until rows.size).map { rows[it][colIdx] }
                            )
                                .map(List<Int>::max)
                                .any { treeHeight > it }
                        }
                }
                .sumOf {
                    it.count { p -> p }
                } + ((rows.size - 2 + rows[0].size - 2) * 2 + 4)
        }

fun part2(inputPath: String) =
    File(inputPath)
        .readLines()
        .map { it.toCharArray().toList().map(Char::intValue) }
        .let { rows ->
            rows
                .drop(1)
                .dropLast(1)
                .flatMapIndexed { r, row ->
                    row
                        .drop(1)
                        .dropLast(1)
                        .mapIndexed { c, treeHeight ->
                            val rowIdx = r + 1
                            val colIdx = c + 1

                            listOf(
                                row.subList(0, colIdx).reversed(),
                                row.subList(colIdx + 1, row.size),
                                (0 until rowIdx).map { rows[it][colIdx] }.reversed(),
                                (rowIdx + 1 until rows.size).map { rows[it][colIdx] }
                            )
                                .map { it.viewDistance(treeHeight) }
                                .fold(1) { acc, curr -> acc * curr }
                        }
                }
                .max()
        }

fun main() {
    println("part 1: ${part1("test.in")}")
    println("part 2: ${part2("test.in")}")
}
