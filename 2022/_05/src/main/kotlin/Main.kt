import java.io.File

data class Move(val amount: Int, val from: Int, val to: Int)

fun part1(inputPath: String) =
    File(inputPath)
        .readText()
        .split("\n\n")
        .let { it[0] to it[1] }
        .let { (staksText, movesText) ->
            val stacks = staksText
                .lines()
                .reversed()
                .let { lines ->
                    val n = lines.first().last().toString().toInt()
                    lines
                        .drop(1)
                        .fold(List(n) { emptyList<Char>() }) { acc, line ->
                            acc.mapIndexed { index, stack ->
                                line
                                    .getOrNull(1 + 4 * index)
                                    .takeIf { it != ' ' }
                                    ?.let { stack + it }
                                    ?: stack
                            }
                        }
                }

            val moves = movesText
                .lines()
                .map { line ->
                    line
                        .split(' ')
                        .filter { chars -> chars.all(Char::isDigit) }
                        .map(String::toInt)
                        .let { Move(it[0], it[1] - 1, it[2] - 1) }
                }

            moves
                .fold(stacks) { acc, curr ->
                    acc.mapIndexed { index, stack ->
                        when (index) {
                            curr.from -> stack.dropLast(curr.amount)
                            curr.to -> stack + acc[curr.from].takeLast(curr.amount).reversed()
                            else -> stack
                        }
                    }
                }
                    .map(List<Char>::last)
                    .joinToString("")
        }

fun part2(inputPath: String) =
    File(inputPath)
        .readText()
        .split("\n\n")
        .let { it[0] to it[1] }
        .let { (staksText, movesText) ->
            val stacks = staksText
                .lines()
                .reversed()
                .let { lines ->
                    val n = lines.first().last().toString().toInt()
                    lines
                        .drop(1)
                        .fold(List(n) { emptyList<Char>() }) { acc, line ->
                            acc.mapIndexed { index, stack ->
                                line
                                    .getOrNull(1 + 4 * index)
                                    .takeIf { it != ' ' }
                                    ?.let { stack + it }
                                        ?: stack
                            }
                        }
                }

            val moves = movesText
                .lines()
                .map { line ->
                    line
                        .split(' ')
                        .filter { chars -> chars.all(Char::isDigit) }
                        .map(String::toInt)
                        .let { Move(it[0], it[1] - 1, it[2] - 1) }
                }

            moves
                .fold(stacks) { acc, curr ->
                    acc.mapIndexed { index, stack ->
                        when (index) {
                            curr.from -> stack.dropLast(curr.amount)
                            curr.to -> stack + acc[curr.from].takeLast(curr.amount)
                            else -> stack
                        }
                    }
                }
                    .map(List<Char>::last)
                    .joinToString("")
        }

fun main() {
  println("part 1: ${part1("test.in")}")
  println("part 2: ${part2("test.in")}")
}
