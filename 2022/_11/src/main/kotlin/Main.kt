import java.io.File

data class Monkey(
    val items: MutableList<Int>,
    val operation: (old: Int) -> Int,
    val test: (level: Int) -> Int
)

fun part1(inputPath: String) =
    File(inputPath)
        .readText()
        .split("\n\n")
        .map { rawMonkey ->
            rawMonkey
                .lines()
                .drop(1)
                .let { line ->
                    val startingItems = line[0].split(": ")[1].split(", ").map(String::toInt)
                    val operation = { old: Int ->
                        line[1].split("= ")[1].split(" ").let {
                            val leftValue = it[0].toIntOrNull() ?: old
                            val rightValue = it[2].toIntOrNull() ?: old

                            when (it[1]) {
                                "+" -> leftValue + rightValue
                                "*" -> leftValue * rightValue
                                else -> old
                            }
                        }
                    }
                    val test = { level: Int ->
                        val matcher = line[2].split(" ").last().toInt()
                        val trueMonkey = line[3].split(" ").last().toInt()
                        val falseMonkey = line[4].split(" ").last().toInt()

                        if (level % matcher == 0) trueMonkey else falseMonkey
                    }

                    Monkey(startingItems.toMutableList(), operation, test)
                }
        }
        .let { monkeys ->
            val counters = monkeys.map { 0 }.toMutableList()

            repeat(20) {
                for (i in monkeys.indices) {
                    monkeys[i].items.forEach { level ->
                        counters[i]++
                        val newLevel = monkeys[i].operation(level) / 3
                        monkeys[monkeys[i].test(newLevel)].items.add(newLevel)
                    }

                    monkeys[i].items.removeAll { true }
                }
            }

            counters
        }
        .sorted()
        .reversed()
        .take(2)
        .fold(1) { acc, curr -> acc * curr }

data class MonkeyLong(
    val items: MutableList<Long>,
    val operation: (old: Long) -> Long,
    val test: (level: Long) -> Int
)

fun part2(inputPath: String) =
    File(inputPath)
        .readText()
        .split("\n\n")
        .map { rawMonkey ->
            rawMonkey
                .lines()
                .drop(1)
                .let { line ->
                    val startingItems = line[0].split(": ")[1].split(", ").map(String::toLong)
                    val operation = { old: Long ->
                        line[1].split("= ")[1].split(" ").let {
                            val leftValue = it[0].toLongOrNull() ?: old
                            val rightValue = it[2].toLongOrNull() ?: old

                            when (it[1]) {
                                "+" -> leftValue + rightValue
                                "*" -> leftValue * rightValue
                                else -> old
                            }
                        }
                    }
                    val matcher = line[2].split(" ").last().toLong()
                    val test = { level: Long ->
                        val trueMonkey = line[3].split(" ").last().toInt()
                        val falseMonkey = line[4].split(" ").last().toInt()

                        if (level % matcher == 0L) trueMonkey else falseMonkey
                    }

                    MonkeyLong(startingItems.toMutableList(), operation, test) to matcher
                }
        }
        .fold(emptyList<MonkeyLong>() to 1L) { (monkeys, divisor), (monkey, matcher) ->
            monkeys + listOf(monkey) to divisor * matcher
        }
        .let { (monkeys, divisor) ->
            val counters = monkeys.map { 0 }.toMutableList()

            repeat(10000) {
                for (i in monkeys.indices) {
                    monkeys[i].items.forEach { level ->
                        counters[i]++
                        val newLevel = monkeys[i].operation(level).let { it % divisor }
                        monkeys[monkeys[i].test(newLevel)].items.add(newLevel)
                    }

                    monkeys[i].items.removeAll { true }
                }
            }

            counters
        }
        .sorted()
        .reversed()
        .take(2)
        .fold(1L) { acc, curr -> acc * curr }

fun main() {
    println("part 1: ${part1("test.in")}")
    println("part 2: ${part2("test.in")}")
}
