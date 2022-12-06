import java.io.File

fun part1(inputPath: String) =
    File(inputPath)
        .readText()
        .windowed(4)
        .foldIndexed(-1) { index, acc, curr ->
            if (acc != -1) return@foldIndexed acc
            if (curr.toSet().size == 4) index + 4 else acc
        }

fun part2(inputPath: String) =
    File(inputPath)
        .readText()
        .windowed(14)
        .foldIndexed(-1) { index, acc, curr ->
            if (acc != -1) return@foldIndexed acc
            if (curr.toSet().size == 14) index + 14 else acc
        }

fun main() {
    println("part 1: ${part1("test.in")}")
    println("part 2: ${part2("test.in")}")
}
