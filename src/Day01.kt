import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val lists = input.map { it.split("   ") }
        val one = lists.map { it[0].toInt() }.sorted()
        val two = lists.map { it[1].toInt() }.sorted()
        var sum = 0
        one.forEachIndexed{ index, value ->
            sum += Math.abs(one[index] - two[index])
        }
        return sum
    }

    fun part1Refactor(input: List<String>): Int {
        return input.map {
            listOf(
                it.split("   ")[0].toInt(),
                it.split("   ")[1].toInt())
            }
            .transpose()
            .map { it.sorted() }
            .transpose()
            .sumOf { abs(it[0] - it[1]) }
    }

    fun part2(input: List<String>): Int {
        val lists = input.map { it.split("   ") }
        val one = lists.map { it[0].toInt() }.sorted()
        val two = lists.map { it[1].toInt() }.sorted()
        var score = 0
        one.forEach { num -> score += num * two.count { it == num } }

        return score
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day01_test")
//    part1(input).println()
    part1Refactor(input).println()
//    part2(input).println()
}
