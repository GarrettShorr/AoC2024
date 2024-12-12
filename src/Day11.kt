import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.log2

fun main() {

    fun blink(stones: MutableList<Pair<Long, Boolean>>): List<Long> {
        var oldStones = stones.toMutableList()
        var newStones = mutableListOf<Long>()
        repeat(stones.size) {
            newStones.add(-1)
            newStones.add(-1)
        }

        // find the 0s
        for (i in oldStones.indices) {
            if (oldStones[i].first == 0L) {
                newStones[i*2] = 1
                oldStones[i] = Pair(0, false)
            }
        }

        // find evens
        for (i in oldStones.indices) {
            if (oldStones[i].second && oldStones[i].first.toString().length % 2 == 0) {
                var numStringified = oldStones[i].first.toString()
                val left = numStringified.substring(0, numStringified.length/2).toLong()
                var right = numStringified.substring(numStringified.length/2).toLong()
                newStones[i*2] = left
                newStones[i*2+1] = right
                oldStones[i] = Pair(oldStones[i].first, false)
            }
        }
        // rest mult by 2024
        for (i in oldStones.indices) {
            if (oldStones[i].second) {
                newStones[i*2] = oldStones[i].first * 2024
                oldStones[i] = Pair(oldStones[i].first, false)
            }
        }

        return newStones.filterNot { it == -1L }
    }

    fun blinkMap(stones: MutableMap<Long, Long>): MutableMap<Long,Long> {
        var newStones = mutableMapOf<Long, Long>()

        // find the 0s
        newStones[1L] = stones[0L] ?: 0L
        newStones[0] = 0


        // find evens
        var keys = stones.keys.filter { it != 0L }
        for (key in keys) {
            if (key.toString().length % 2 == 0) {
                var numStringified = key.toString()
                val left = numStringified.substring(0, numStringified.length/2).toLong()
                var right = numStringified.substring(numStringified.length/2).toLong()
                newStones[left] = (newStones[left] ?: 0) + stones[key]!!
                newStones[right] = (newStones[right] ?: 0) + stones[key]!!
            } else {
                var increased = key * 2024
                newStones[increased] = (newStones[increased] ?: 0) + stones[key]!!
            }
        }


        return newStones
    }

    fun part1(input: List<String>): Long {
        var stones = input[0].split(" ").map { it.toLong() }.map { Pair(it, true) }.toMutableList()
        var i = 0
        repeat(75) {
            i++
            stones = blink(stones).map { Pair(it, true) }.toMutableList()
//            println(stones.joinToString { it.first.toString() })
            println(i)
        }

        return stones.size.toLong()
    }


    fun part2(input: List<String>): Long {
        var stones = input[0].split(" ").map { it.toLong() }.toMutableList()
        var freqMap = mutableMapOf<String, Int>("zeroes" to 0, "evens" to 0, "others" to 0, "ones" to 0)
        for(stone in stones) {
            if(stone == 0L) {
                freqMap["zeroes"] = freqMap["zeroes"]!! + 1
            } else if(stone.toString().length % 2 == 0) {
                freqMap["evens"] = freqMap["evens"]!! + 1
            } else {
                freqMap["others"] = freqMap["others"]!! + 1
            }
        }

        repeat(6) {
            // apply rules
            stones = stones.filter { it != 0L }.toMutableList()
            freqMap["evens"] = freqMap["evens"]!! + freqMap["ones"]!!
            freqMap["ones"] = freqMap["zeroes"]!!
            // do i have to remember the 1s?
            freqMap["zeroes"] = 0
            var evens = stones.filter { (log10(it.toDouble()).toInt() + 1) % 2 == 0 }
            var newEvens = mutableListOf<Long>()
            evens.forEach {
                var halfLength = pow(10.0, ((log10(it.toDouble()).toInt() + 1) / 2).toDouble()).toInt()
                newEvens.add(it / halfLength)
                newEvens.add(it % halfLength)
            }
            freqMap["evens"] = freqMap["evens"]!! + newEvens.count { (log10(it.toDouble()).toInt() + 1) % 2 == 0 }
            freqMap["ones"] = freqMap["ones"]!! + newEvens.count { it == 1L }
            freqMap["zeroes"] = newEvens.count { it == 0L }
            newEvens = newEvens.filterNot { it == 0L || it == 1L }.toMutableList()
            stones = stones.map { if(it == 1L) 2024 else it }.toMutableList()
            freqMap["evens"] = freqMap["evens"]!! + stones.count { it == 2024L }
            var others = stones.filterNot { (log10(it.toDouble()).toInt() + 1) % 2 == 0 }.map { it * 2024 }

//            freqMap["others"] = freqMap["others"]!! + others.size

            stones.addAll(newEvens)
        }
        return 0
    }

    fun part2Refactor(input: List<String>) : Long {
        var stones = input[0].split(" ").map { it.toLong() }.groupingBy { it }
            .eachCount().toMutableMap().map{ it -> it.key.toLong() to it.value.toLong() }.toMap().toMutableMap()
        println(stones)
        repeat(75) {
            stones = blinkMap(stones)
            println(stones)
        }
        return stones.values.sum()
    }


    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day11")
//    part1(input).println()
    part2Refactor(input).println()
}