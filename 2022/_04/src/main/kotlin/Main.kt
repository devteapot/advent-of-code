import java.io.File

fun fullyContains(p1: Pair<Int, Int>, p2: Pair<Int, Int>) =
   p1.first <= p2.first && p1.second >= p2.second

fun overlaps(p1: Pair<Int, Int>, p2: Pair<Int, Int>) =
    (p1.first <= p2.first && p2.first <= p1.second) || (p1.first <= p2.second && p2.second <= p1.second)

fun Pair<Int, Int>.oneOf(other: Pair<Int, Int>, predicate: (p1: Pair<Int, Int>, p2: Pair<Int, Int>) -> Boolean) =
    predicate(this, other) || predicate(other, this)


fun part1(inputPath: String) =
    File(inputPath)
        .readLines()
        .count { line ->
          line
              .split(',')
              .map { range ->
                range
                    .split('-')
                    .map(String::toInt)
                    .let { it[0] to it[1] }
              }
              .let { it[0].oneOf(it[1]) { p1, p2 -> fullyContains(p1, p2) } }
        }

fun part2(inputPath: String) =
    File(inputPath)
        .readLines()
        .count { line ->
          line
              .split(',')
              .map { range ->
                range
                    .split('-')
                    .map(String::toInt)
                    .let { it[0] to it[1] }
              }
              .let { it[0].oneOf(it[1]) { p1, p2 -> overlaps(p1, p2) } }
        }

fun main() {
  println("part 1: ${part1("test.in")}")
  println("part 2: ${part2("test.in")}")
}
