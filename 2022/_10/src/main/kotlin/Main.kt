import java.io.File

enum class Operation {
    ADDX, NOOP
}

data class Accumulator1(
    val cycle: Int = 1,
    val register: Int = 1,
    val signalStrengths: List<Int> = emptyList()
)

fun part1(inputPath: String) =
    File(inputPath)
        .readLines()
        .map { line ->
            line
                .split(" ")
                .let { Operation.valueOf(it[0].uppercase()) to it.getOrNull(1)?.toInt() }
        }
        .fold(Accumulator1()) { (cycle, register, signalStrengths), (op, value) ->
            when(op) {
                Operation.NOOP -> 1
                Operation.ADDX -> 2
            }
                .let { cycleIncrements ->

                    val newSignalStrength = (0 until cycleIncrements)
                        .map { (cycle + it + 1) }
                        .mapIndexedNotNull { index, cycle ->
                            cycle
                                .takeIf { it == 20 || ((it - 20) % 40) == 0 }
                                ?.let { c ->
                                    c * (value
                                        ?.takeIf {
                                            cycleIncrements == 1 || (cycleIncrements == 2 && index == 1)
                                        }
                                        ?.let { register + it } ?: register)
                                }
                        }

                    Accumulator1(cycle + cycleIncrements, value?.let { register + it } ?: register, signalStrengths + newSignalStrength)
                }
        }
            .signalStrengths
            .sum()

data class Accumulator2(
    var cycle: Int = 1,
    var register: Int = 1,
    var output: String = ""
)

fun part2(inputPath: String) =
    File(inputPath)
        .readLines()
        .map { line ->
            line
                .split(" ")
                .let { Operation.valueOf(it[0].uppercase()) to it.getOrNull(1)?.toInt() }
        }
        .fold(Accumulator2()) { cpu, (op, value) ->
            when(op) {
                Operation.NOOP -> 1
                Operation.ADDX -> 2
            }
                .let { times ->
                    repeat(times) {
                        cpu.output +=
                            if (cpu.register in (cpu.cycle % 40 - 2)..cpu.cycle % 40)
                                "#"
                            else
                                "."

                        if (cpu.cycle % 40 == 0) {
                            cpu.output += '\n'
                        }

                        cpu.cycle += 1
                    }

                    value?.let {
                        cpu.register += it
                    }
                }

            cpu
        }
            .output

fun main() {
    println("part 1: ${part1("test.in")}")
    println("part 2:\n${part2("test.in")}")
}
