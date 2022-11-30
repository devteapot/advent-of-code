import java.io.File

const val SIM_DURATION = 256

fun task2(input: Collection<Int>) {
    val result = (0 until SIM_DURATION)
        .fold(
            listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
                .associateWith { 0L } +
                    input
                        .groupingBy { it }
                        .eachCount()
                        .mapValues { it.value.toLong() }
        ) { acc, _ ->
            acc.entries.associate { (key, _) ->
                when (key) {
                    8 -> 8 to (acc[0] ?: 0L)
                    6 -> 6 to (acc[0] ?: 0L) + (acc[key + 1] ?: 0L)
                    else -> key to (acc[key + 1] ?: 0L)
                }
            }
        }
        .values
        .sum()

    println(result)
}

fun main(args: Array<String>) {
    val input = File("/Users/lino/dev/advent-of-code-21/06/src/main/resources/1.txt")
        .readLines()[0]
        .split(',')
        .map(String::toInt)

    task2(input)
}
