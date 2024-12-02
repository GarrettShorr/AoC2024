import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").map { it.toInt() } }
            .map {
                it.zipWithNext()
            }.count {
                (it.all { it.first - it.second > 0 } ||
                    it.all { it.second - it.first > 0 })
                    && it.all { abs(it.first - it.second) >= 1 && abs(it.first-it.second) <= 3 }
            }


        println()
        return 0
    }

    fun isDecreasing(nums: List<Int>) : Boolean {
        return nums.zipWithNext().all { it.first > it.second } &&
            nums.zipWithNext().all { it.first - it.second >= 1 && it.first-it.second <= 3 }
    }

    fun isIncreasing(nums: List<Int>) : Boolean {
        return nums.zipWithNext().all { it.first < it.second } &&
            nums.zipWithNext().all { it.second - it.first >= 1 && it.second-it.first <= 3 }
    }

    fun part2Rework(input: List<String>): Int {
        val nums = input.map { it.split(" ").map { it.toInt() } }
        var count = 0

        outer@for(row in nums)  {
            if(isIncreasing(row)) {
                count++
                continue
            }
            for(i in 0..row.size - 2) {
                var a = row[i]
                var b = row[i + 1]
                if(b-a !in 1..3) {
                    val rowWithoutA = row.toMutableList()
                    rowWithoutA.removeAt(i)
                    val rowWithoutB = row.toMutableList()
                    rowWithoutB.removeAt(i+1)
                    if(isIncreasing(rowWithoutA) || isIncreasing(rowWithoutB)) {
                        count++
                    }
                    continue@outer
                }
            }
        }

        outer@for(row in nums)  {
            if(isDecreasing(row)) {
                count++
                continue
            }
            for(i in 0..row.size - 2) {
                var a = row[i]
                var b = row[i + 1]
                if(a-b !in 1..3) {
                    val rowWithoutA = row.toMutableList()
                    rowWithoutA.removeAt(i)
                    val rowWithoutB = row.toMutableList()
                    rowWithoutB.removeAt(i+1)
                    if(isDecreasing(rowWithoutA) || isDecreasing(rowWithoutB)) {
                        count++
                    }
                    continue@outer
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val nums = input.map { it.split(" ").map { it.toInt() } }
        var count = 0
        for(row in nums) {
            println("current row: $row")
            var removed = false
            var decreasing = true
            var i = 0
            while(i <  row.size - 1 && decreasing) {
                var a = row[i]
                var b = row[i + 1]
                if(a > b) {
                    if(a-b !in 1..3) {
                        println("bad row decreasing $row")
                        if(i < row.size - 2 && a - row[i+2] in 1..3 && !removed) {
                            println("removed")
                            removed = true
                        } else if(i == row.size - 2 && !removed) {
                            println("removed end")
                            removed = true
                        }
                        else {
                            decreasing = false
                        }
                    }
                } else {
                    if(i < row.size - 2 && a - row[i+2] in 1..3 && !removed) {
                        println("removed in else decreasing: $row")
                        removed = true
                        i++
                    } else if(i == row.size - 2 && !removed) {
                        println("removed end")
                        removed = true
                    } else {
                        println("decreasing set to false at index $i: ${row[i]}")
                        decreasing = false
                        break
                    }
                }
                i++
            }
            if(decreasing) {
                println("decreasing good: $row")
                count++
            } else {
                println("decreasing bad: $row")
            }
            if(decreasing && removed) {
                println("made safe: $row")
            }
        }
        println("START INCREASING")

        for(row in nums) {
            var removed = false
            var increasing = true
            var i = 0
            while(i < row.size - 1 && increasing) {
                var a = row[i]
                var b = row[i + 1]
                if(a < b) {
                    if(b-a !in 1..3) {
                        println("bad row increasing $row")

                        if(i < row.size - 2 && row[i+2] - a in 1..3 && !removed) {
                            print("removed $i: ${row[i]} in $row")
                            removed = true
                            i++
                        } else if(i == row.size - 2 && !removed) {
                            println("removed end")
                            removed = true
                        } else {
                            increasing = false
                        }
                    }
                } else {
                    if(i < row.size - 2 &&  row[i+2] - a in 1..3 && !removed) {
                        println("removed in else $row")
                        removed = true
                        i++
                    } else if(i == row.size - 2 && !removed) {
                        println("removed end")
                        removed = true
                    }else {
                        increasing = false
                        break
                    }
                }
                i++
            }
            if(increasing) {
                println("increasing good: $row")

                count++
            } else{
                println("increasing bad: $row")
            }
            if(increasing && removed) {
                println("made safe: $row")
            }

        }
        return count


    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day02")
//    part1(input).println()
    part2Rework(input).println()
}
