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

    fun findColumns(rows: List<String>) : List<Int> {
        val columnStarts = mutableListOf<Int>()
        var height = 0..rows.size - 2
        columnStarts.add(0)
        for(c in 1..<rows[0].length-1) {
            var allSpace = true
            for(r in height) {
                if(c < rows[r].length && rows[r][c].isDigit())   {
                    allSpace = false
                }
            }
            if(allSpace) {
                columnStarts.add(c + 1)
            }
        }
        return columnStarts
    }

//    fun transposeProblems(problems: List<List<String>>) : List<List<String>> {
//
//        for(c in problems[0].indices) {
//            for(r in problems.indices) {
//
//            }
//        }
//    }



    fun part2(input: List<String>): Long {
        var cols = findColumns(input)
        var problems = mutableListOf<MutableList<String>>()
        var maxLastLength = 0
        var operations = input.last().split(" ").filter { it.isNotEmpty() }
        println(operations)
        for(i in 0..input.size-2) {
            problems.add(mutableListOf())
            for (j in 0..cols.size-2) {
                problems[i].add(input[i].substring(cols[j], cols[j+1]-1))
            }
            if(input[i].substring(cols.last()).length > maxLastLength) {
                maxLastLength = input[i].substring(cols.last()).length
            }
            problems[i].add(input[i].substring(cols.last()))
        }
        for(i in problems.indices) {
            while(problems[i][problems[i].size-1].length < maxLastLength) {
                problems[i][problems[i].size-1] += " "
            }
        }
        println(problems)
        var transposedSplitProblems = problems.map { it.map { it.windowed(1)  } }.transpose()
        println(transposedSplitProblems)
        var transposedProblems = mutableListOf<MutableList<Long>>()
        transposedSplitProblems.forEachIndexed { i, row ->
            transposedProblems.add(mutableListOf())
            for(c in row[0].indices) {
                var num = ""
                for(r in row.indices) {
                    num += row[r][c]
                }
                transposedProblems[i].add(num.trim().toLong())
            }
        }
        println(transposedProblems)

        var total = 0L
        for(i in operations.indices) {
            if(operations[i] == "+") {
                total += transposedProblems[i].sum()
            } else {
                var subtotal = 1L
                transposedProblems[i].forEach { subtotal *= it }
                total += subtotal
            }
        }
        println("${operations.size} ${transposedProblems.size}")

        return total
    }



    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day06_2025")
    part2(input).println()
//    part2(input).println()
}
