import java.math.BigInteger
import java.util.*
import kotlin.collections.ArrayDeque

fun main() {

    fun List<Triple<Int, Int, String>>.evaluateOperations() : Long {
        var total = 0L
        this.forEach { total += if(it.third == "+") it.first + it.second else it.first * it.second }
        return total
    }
//    var allPoss = mutableListOf<MutableList<Int>>()
//    var allPoss = mutableListOf<MutableList<Long>>()

//    fun calcAll(inputs: MutableList<Long>) {
//        if(inputs.size == 1) return
//        var added = inputs.map { it } as MutableList
//        var multed = inputs.map { it } as MutableList
//        val first = added.removeFirst()
//        multed.removeFirst()
//        println("inputs $inputs")
//        added[0] += first
//        multed[0] *= first
//        allPoss.remove(inputs)
//        allPoss.add(added)
//        allPoss.add(multed)
//        println("inputs $inputs allPoss: $allPoss")
//    }
//    fun isValidEquation(answer: Int, inputs: MutableList<Int>) : Long {
//        allPoss.add(inputs)
//        while(allPoss.all { it.size > 1 }) {
//            for(i in allPoss.indices) {
//                calcAll(allPoss[i])
//            }
//            for(i in allPoss.indices) {
//                calcAll(allPoss[i])
//            }
//        }
//        if( allPoss.any { it.contains(answer) }) {
//            allPoss.clear()
//            println("answer $answer works!")
//            return answer
//        } else {
//            allPoss.clear()
//            return 0
//        }
//
//    }

    fun isValidEquation(answer: Long, inputs: MutableList<Long>) : Long {
        val queue = ArrayDeque<MutableList<Long>>()
        var added = inputs.map { it } as MutableList
        var multed = inputs.map { it } as MutableList
        var first = added.removeFirst()
        multed.removeFirst()
        added[0] += first
        multed[0] *= first
        queue.add(added)
        queue.add(multed)
        while(queue.isNotEmpty()) {
            val list = queue.removeFirst()
            if(list.size == 1 && list[0] == answer) {
                return answer
            } else if(list.size == 1) {
                continue
            }
            added = list.map { it } as MutableList
            multed = list.map { it } as MutableList
            first = added.removeFirst()
            multed.removeFirst()
            added[0] += first
            multed[0] *= first
            queue.add(added)
            queue.add(multed)
//            println(queue)
        }
        return 0

    }

    fun isValidEquation2(answer: BigInteger, inputs: MutableList<BigInteger>) : BigInteger {
        val queue = ArrayDeque<MutableList<BigInteger>>()
        var added = inputs.map { it } as MutableList
        var multed = inputs.map { it } as MutableList
        var combined = inputs.map { it } as MutableList
        var first = added.removeFirst()
        multed.removeFirst()
        combined.removeFirst()
        added[0] += first
        multed[0] *= first
        combined[0] = (first.toString() + combined[0].toString() ).toBigInteger()
        queue.add(added)
        queue.add(multed)
        queue.add(combined)
        while(queue.isNotEmpty()) {
            val list = queue.removeFirst()
            if(list.size == 1 && list[0] == answer) {
                return answer
            } else if(list.size == 1) {
                continue
            }
            added = list.map { it } as MutableList
            multed = list.map { it } as MutableList
            combined = list.map { it } as MutableList
            first = added.removeFirst()
            multed.removeFirst()
            combined.removeFirst()
            added[0] += first
            multed[0] *= first
            combined[0] = (first.toString() + combined[0].toString() ).toBigInteger()
//            if(added[0] < answer) {
                queue.add(added)
//            }
//            if(multed[0] < answer) {
                queue.add(multed)
//            }
//            if(combined[0] < answer) {
                queue.add(combined)
//            }
//            println(queue)
        }
        return BigInteger.valueOf(0)

    }




//    fun isValidEquation(answer: Long, inputs: MutableList<Int>) : Boolean {
//        val ops = PriorityQueue<Triple<Int, Int, String>>()
//        var i = 1
//        var total = 0
//        ops.add(Triple(inputs[0], inputs[1], "+"))
//        var completedOps = mutableListOf<Triple<Int, Int, String>>()
//        while(ops.isNotEmpty()) {
//            val current = ops.poll()
//            completedOps.add(current)
//            if(completedOps.evaluateOperations() == answer) {
//                return true
//            }
//            if(current.third == "*")
//
//
//        }
//
//        return false
//    }

    fun part1(input: List<String>): Long {
        var lines = input.map { it.split(": ") }
//        println(lines)
        var sum = 0L
        for(line in lines) {
            sum += isValidEquation(line[0].toLong(), line[1].split(" ").map { it.toLong() } as MutableList)
        }

        return sum
    }

    fun part2(input: List<String>): BigInteger {
        var lines = input.map { it.split(": ") }
//        println(lines)
        var sum = BigInteger.valueOf(0)
        for(line in lines) {
//            println("line $line")
            sum += isValidEquation2(line[0].toBigInteger(), line[1].split(" ").map { it.toBigInteger() } as MutableList)
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day07")
//    part1(input).println()
    part2(input).println()
}
