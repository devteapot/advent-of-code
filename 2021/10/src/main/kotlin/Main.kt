import java.io.File
import java.util.Stack

val openBrackets = setOf(
    '(',
    '[',
    '{',
    '<',
)

val closeBrackets = setOf(
    ')',
    ']',
    '}',
    '>',
)

val bracketsMap = openBrackets
    .zip(closeBrackets)
    .toMap()

fun task1(input: List<List<Char>>) {
    val charScoreMap = closeBrackets
        .zip(listOf(3, 57, 1197, 25137))
        .toMap()

    val result = input
        .mapNotNull {
            val stack = Stack<Char>()
            var error = false
            var wrongBracket: Char? = null

            val inputIterator = it.iterator()
            while (inputIterator.hasNext() && !error) {
                val current = inputIterator.next()

                if (openBrackets.contains(current)) {
                    stack.push(current)
                } else {
                    error = current != bracketsMap[stack.pop()]
                    if (error) {
                        wrongBracket = current
                    }
                }
            }

            wrongBracket
        }
        .sumOf { charScoreMap[it]!! }

    println("task-1: $result")
}

fun task2(input: List<List<Char>>) {
    val charScoreMap = closeBrackets
        .zip(listOf(1, 2, 3, 4))
        .toMap()

    val result = input
        .mapNotNull { it ->
            val stack = Stack<Char>()
            var error = false

            val inputIterator = it.iterator()
            while (inputIterator.hasNext() && !error) {
                val current = inputIterator.next()

                if (openBrackets.contains(current)) {
                    stack.push(current)
                } else {
                    error = current != bracketsMap[stack.pop()]
                }
            }

            if (error)
                null
            else
                stack
                    .toList()
                    .reversed()
                    .map { currentBracket -> charScoreMap[bracketsMap[currentBracket]!!]!! }
                    .fold(0L) { acc, curr ->
                        acc * 5 + curr
                    }
        }
        .sorted()
        .let { it[it.size / 2] }

    println("task-2: $result")
}

fun main(args: Array<String>) {
    val input = File("/Users/lino/dev/advent-of-code-21/10/src/main/resources/1.txt")
        .readLines()
        .map(String::toList)

    task1(input)
    task2(input)
}
