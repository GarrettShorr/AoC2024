import org.apache.commons.lang3.Range
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        val problems = input.map { it.split(" ").filter { it.isNotEmpty() } }.transpose()
        var total = 0L
        problems.forEach {
            var subtotal = 0L
            for (i in 0..it.size - 2) {
                if(it.last()=="+") {
                    subtotal += it[i].toLong()
                } else if(it.last()=="*") {
                    if(subtotal == 0L && i == 0) {
                        subtotal = 1L
                    }
                    subtotal *= it[i].toLong()
                }
            }
            println("problem: $it  total: $subtotal")
            total += subtotal
        }
        return total
    }



    fun part2(input: List<String>): Long {
        val problems = input.map { it.split(" ").filter { it.isNotEmpty() } }.transpose()
        var total = 0L
        problems.forEach {
            var subtotal = 0L
            var operation = it.last()
            var numbers = it.dropLast(1).toMutableList()
            var maxLength = numbers.maxOf { it.length }
            for(i in numbers.indices) {
                while(numbers[i].length < maxLength) {
                    numbers[i] += "_"
                }
            }
            numbers = numbers.transposeStringList().map { it.replace("_", "") } as MutableList<String>
            println(numbers)
            for (i in numbers.indices) {
                if(operation=="+") {
                    subtotal += numbers[i].toLong()
                } else if(operation=="*") {
                    if(subtotal == 0L && i == 0) {
                        subtotal = 1L
                    }
                    subtotal *= it[i].toLong()
                }
            }
            println("problem: $it  total: $subtotal")
            total += subtotal
        }
        return total
    }



    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day06_2025_test")
    part2(input).println()
//    part2(input).println()
}
