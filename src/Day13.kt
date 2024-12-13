import org.jetbrains.kotlinx.multik.api.linalg.solve
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.map
import java.math.BigDecimal
import java.math.RoundingMode

import kotlin.math.round


data class Machine(var a: Pair<Long, Long>, var b: Pair<Long, Long>, var prize: Pair<Long, Long>) {
    var totalA = 0L
    var totalB = 0L

    fun isWinPossible() : Boolean {
        println(prize)
        println(10000000000000)
//        var bPresses = (prize.second - (a.second * prize.first/a.first))/(b.second-(a.second*b.first)/a.first)
//        var aPresses = (prize.first - bPresses*b.first)/a.first
//        println("$aPresses + $bPresses")


        val first = mk.ndarray(mk[mk[a.first, b.first], mk[a.second, b.second]])
        val second  = mk.ndarray(mk[prize.first, prize.second])

        val solution = mk.linalg.solve(first, second).map { round(it).toLong() }
//        println("solution $solution")
        totalA = solution.asD1Array()[0]
        totalB = solution.asD1Array()[1]
//        println(totalA)
//        println(totalB)

        return (totalA * a.first + totalB * b.first) == prize.first
                    && (totalA*a.second + totalB * b.second) == prize.second


    }

    fun costToWin() : Long {
//        println("$this is win possible? ${isWinPossible()}")
        if(isWinPossible()) {
            return totalA * 3L + totalB
        }
        return 0L
    }
}

fun main() {
    fun part1(input: List<String>): BigDecimal {
        var machines = mutableListOf<Machine>()
        for(i in 0..input.size-2 step 4) {
//            println(input[i])
//            println(input[i].indexOf("X+")+2)
//            println(input[i].indexOf(","))
            var ax = input[i].substring(input[i].indexOf("X+")+2, input[i].indexOf(",")).toDouble()
            var ay = input[i].substring(input[i].indexOf("Y+")+2).toDouble()
            var bx = input[i+1].substring(input[i+1].indexOf("X+")+2, input[i+1].indexOf(",")).toDouble()
            var by = input[i+1].substring(input[i+1].indexOf("Y+")+2).toDouble()
            var px = input[i+2].substring(input[i+2].indexOf("X=")+2, input[i+2].indexOf(",")).toDouble()
            var py = input[i+2].substring(input[i+2].indexOf("Y=")+2).toDouble()
//            machines.add(Machine(Pair(BigDecimal.valueOf(ax), BigDecimal.valueOf(ay), Pair(bx, by), Pair(px.toLong(), py.toLong())))
        }

//        return machines.sumOf { if(it.isWinPossible()) it.costToWin() else 0 }

//        var machine = Machine(Pair(94.0,34.0), Pair(22.0,67.0), Pair(8400.0,5400.0))
//        println(machine.isWinPossible())
//        println(machine.costToWin())
        return BigDecimal.valueOf(0)

    }




    fun part2(input: List<String>): Long {
        var machines = mutableListOf<Machine>()
        for(i in 0..input.size-2 step 4) {
//            println(input[i])
//            println(input[i].indexOf("X+")+2)
//            println(input[i].indexOf(","))
            var ax = input[i].substring(input[i].indexOf("X+")+2, input[i].indexOf(",")).toDouble()
            var ay = input[i].substring(input[i].indexOf("Y+")+2).toDouble()
            var bx = input[i+1].substring(input[i+1].indexOf("X+")+2, input[i+1].indexOf(",")).toDouble()
            var by = input[i+1].substring(input[i+1].indexOf("Y+")+2).toDouble()
            var px = input[i+2].substring(input[i+2].indexOf("X=")+2, input[i+2].indexOf(",")).toDouble()
            var py = input[i+2].substring(input[i+2].indexOf("Y=")+2).toDouble()
            machines.add(Machine(Pair(ax.toLong(), ay.toLong()), Pair(bx.toLong(), by.toLong()),
                Pair(px.toLong()+10000000000000L, py.toLong() + 10000000000000L)))
        }

        var sum = 0L
        for(machine in machines) {
            if(machine.isWinPossible()) {
                sum += machine.costToWin()
            }
        }
        return sum

//        var machine = Machine(Pair(94.0,34.0), Pair(22.0,67.0), Pair(8400.0,5400.0))
//        println(machine.isWinPossible())
//        println(machine.costToWin())


    }




    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day13")
//    part1(input).println()
    part2(input).println()
}

