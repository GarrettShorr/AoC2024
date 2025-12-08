import org.apache.commons.lang3.Range
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val divider = input.indexOf("")
        val ranges = input.subList(0, divider).map { it.split("-")[0].toLong().. it.split("-")[1].toLong()}
        val ingredients = input.subList(divider+1, input.size).map { it.toLong() }
        return ingredients.count { ingredient -> ranges.any { ingredient in it } }
    }



    fun part2(input: List<String>): Long {
        val divider = input.indexOf("")
        val ranges = input.subList(0, divider).map { it.split("-")[0].toLong().. it.split("-")[1].toLong()}
            .sortedBy { it.first }
        val ingredients = input.subList(divider+1, input.size).map { it.toLong() }
        val newRanges = mutableListOf<LongRange>()
        var i = 0
        var newRange = ranges[0]
        while(i < ranges.size-1) {
            if(newRange.last + 1 >= ranges[i+1].first) {
               newRange = min(newRange.first,ranges[i+1].first)..max(ranges[i+1].last,newRange.last)
            } else {
                newRanges.add(newRange)
                newRange = ranges[i+1]
            }
            i++
        }
        if(newRanges.last() != newRange) {
            newRanges.add(newRange)
        }
        println(ranges.takeLast(5))
        println(newRanges.takeLast(5))
        return newRanges.sumOf { it.last - it.first + 1L }
    }



    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day05_2025")
    part2(input).println()
//    part2(input).println()
}
