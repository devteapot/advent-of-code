import java.io.File

fun Char.getPriority() =
    if (isUpperCase())
      code - 'A'.code + 27
    else
      code - 'a'.code + 1

fun part1(inputPath: String) =
    File(inputPath)
        .readLines()
        .sumOf { line ->
          line
              .foldIndexed(emptySet<Char>() to emptySet<Char>()) { index, (first, second), curr ->
                  if (index < line.length / 2)
                      first + curr to second
                  else
                      first to second + curr
              }
              .let { (first, second) ->
                  first
                      .intersect(second)
                      .first()
                      .getPriority()
              }
        }

fun part2(inputPath: String) =
    File(inputPath)
        .readLines()
        .chunked(3)
        .sumOf { group ->
          group[0]
              .toSet()
              .intersect(
                  group[1].toSet()
              ).intersect(
                  group[2].toSet()
              )
              .first()
              .getPriority()
        }

fun main() {
  println("part 1: ${part1("test.in")}")
  println("part 2: ${part2("test.in")}")
}
