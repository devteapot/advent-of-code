import java.io.File

enum class Shape(val score: Int) {
    ROCK(1), PAPER(2), SCISSOR(3)
}

enum class Outcome(val score: Int) {
  WIN(6), LOSS(0), DRAW(3)
}

fun Shape.match(other: Shape) =
    when (this) {
        Shape.ROCK -> when (other) {
            Shape.PAPER -> Outcome.LOSS
            Shape.SCISSOR -> Outcome.WIN
            Shape.ROCK -> Outcome.DRAW
        }
        Shape.PAPER -> when (other) {
            Shape.PAPER -> Outcome.DRAW
            Shape.SCISSOR -> Outcome.LOSS
            Shape.ROCK -> Outcome.WIN
        }
        Shape.SCISSOR -> when (other) {
            Shape.PAPER -> Outcome.WIN
            Shape.SCISSOR -> Outcome.DRAW
            Shape.ROCK -> Outcome.LOSS
        }
    }

val opponentEncoding = mapOf(
  "A" to Shape.ROCK,
  "B" to Shape.PAPER,
  "C" to Shape.SCISSOR
)

val myEncoding = mapOf(
  "X" to Shape.ROCK,
  "Y" to Shape.PAPER,
  "Z" to Shape.SCISSOR
)

val outcomeEncoding = mapOf(
  "X" to Outcome.LOSS,
  "Y" to Outcome.DRAW,
  "Z" to Outcome.WIN
)

fun part1(inputPath: String) =
  File(inputPath)
    .readLines()
    .sumOf { line ->
      val shapes = line.split(' ')

      val opponentShape = opponentEncoding[shapes[0]]!!
      val myShape = myEncoding[shapes[1]]!!

      myShape.score + myShape.match(opponentShape).score
    }

fun part2(inputPath: String) =
    File(inputPath)
        .readLines()
        .sumOf { line ->
          val input = line.split(' ')

          val opponentShape = opponentEncoding[input[0]]!!
          val targetResult = outcomeEncoding[input[1]]!!

          targetResult.score + Shape.values().filter { it.match(opponentShape) == targetResult }[0].score
        }

fun main() {
  println("part 1: ${part1("test.in")}")
  println("part 2: ${part2("test.in")}")
}
