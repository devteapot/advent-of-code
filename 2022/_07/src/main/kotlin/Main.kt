import java.io.File
import kotlin.math.absoluteValue

interface Sized {
    fun getSize(): Int
}

data class FileNode(val name: String, val fileSize: Int): Sized {
    override fun getSize() = fileSize
}

data class FolderNode(val files: MutableSet<FileNode> = mutableSetOf(), val folders: MutableMap<String, FolderNode> = mutableMapOf()): Sized {
    override fun getSize() =
        (files + folders.values).map(Sized::getSize).sum()

    fun getCurrentNode(currentPath: List<String>): FolderNode =
        if (currentPath.isEmpty()) this
        else folders[currentPath[0]]!!.getCurrentNode(currentPath.drop(1))

    fun getSizeOfFoldersWithAtMost(size: Int): Int =
        folders
            .filterValues { it.getSize() <= size }
            .values
            .sumOf(FolderNode::getSize) + folders.values.sumOf { it.getSizeOfFoldersWithAtMost(size) }

    fun getFoldersWithAtLeast(size: Int): List<Int> =
        folders
            .filterValues { it.getSize() >= size }
            .values
            .map(FolderNode::getSize) + folders.values.flatMap { it.getFoldersWithAtLeast(size) }
}

fun part1(inputPath: String): Int {
    val root =
        FolderNode()

    var currentPath: List<String> = emptyList()

    for (cmdLines in File(inputPath).readText().split("\n$ ").drop(1).map(String::lines)) {
        val cmd = cmdLines[0].split(' ')

        when (cmd[0]) {
            "ls" -> root.getCurrentNode(currentPath).let { currentNode ->
                cmdLines.drop(1).forEach { outputLine ->
                    if (outputLine[0].isDigit())
                        outputLine.split(' ').let { currentNode.files.add(FileNode(it[1],  it[0].toInt())) }
                    else
                        outputLine.split(' ').let { currentNode.folders.put(it[1], FolderNode()) }
                }
            }
            "cd" -> currentPath = when (cmd[1]) {
                ".." -> currentPath.dropLast(1)
                else -> currentPath + listOf(cmd[1])
            }
            else -> continue
        }
    }

    return root.getSizeOfFoldersWithAtMost(100000)
}

fun part2(inputPath: String): Int {
    val root =
        FolderNode()

    var currentPath: List<String> = emptyList()

    for (cmdLines in File(inputPath).readText().split("\n$ ").drop(1).map(String::lines)) {
        val cmd = cmdLines[0].split(' ')

        when (cmd[0]) {
            "ls" -> root.getCurrentNode(currentPath).let { currentNode ->
                cmdLines.drop(1).forEach { outputLine ->
                    if (outputLine[0].isDigit())
                        outputLine.split(' ').let { currentNode.files.add(FileNode(it[1],  it[0].toInt())) }
                    else
                        outputLine.split(' ').let { currentNode.folders.put(it[1], FolderNode()) }
                }
            }
            "cd" -> currentPath = when (cmd[1]) {
                ".." -> currentPath.dropLast(1)
                else -> currentPath + listOf(cmd[1])
            }
            else -> continue
        }
    }

    return root.getFoldersWithAtLeast((70000000 - root.getSize() - 30000000).absoluteValue).min()
}

fun main() {
    println("part 1: ${part1("test.in")}")
    println("part 2: ${part2("test.in")}")
}
