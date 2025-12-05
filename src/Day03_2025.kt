fun main() {
    fun part1(input: List<String>): Int {

        var banks =  input.map {
            it.map { num -> num.digitToInt() }
        }

        val highestNums = input.map {
            it.mapIndexed { index, num -> Pair(num.digitToInt(), index) }
                .sortedByDescending { it.first }
//                .sortedBy { it.second }
        }
        println(highestNums)
        var joltages = mutableListOf<Int>()
        banks.forEachIndexed { i, bank ->
            var highest = highestNums[i][0]
            if(highestNums[i][0].second == highestNums[i].size - 1) {
                highest = highestNums[i][1]
            }
            var max = bank[highest.second + 1]
            for(j in highest.second + 1 until bank.size) {
                if(bank[j] > max) {
                    max = bank[j]
                }
            }
            joltages.add("${highest.first}$max".toInt())
        }

        return joltages.sum()
    }


    fun part2(input: List<String>): Int {

        return 0
    }


    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day03_2025")
    part1(input).println()
//    part2(input).println()
}
