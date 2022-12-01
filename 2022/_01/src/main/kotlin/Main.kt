import java.io.File

fun part1(inputPath: String) =
    File(inputPath)
        .readText()
        .split("\n\n")
        .maxOf {
          it
              .split('\n')
              .map(String::toInt)
              .sum()
        }

fun part2(inputPath: String) =
    File(inputPath)
        .readText()
        .split("\n\n")
        .map {
          it
              .split('\n')
              .map(String::toInt)
              .sum()
        }
        .sortedDescending()
        .take(3)
        .sum()

fun main() {
  println("part 1: ${part1("test.in")}")
  println("part 2: ${part2("test.in")}")
}
