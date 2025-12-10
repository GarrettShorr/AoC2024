import kotlin.math.sign
import kotlin.reflect.typeOf

fun main() {
    fun part1(input: List<String>): Int {
        var tachionPositions = mutableListOf<MutableSet<Int>>()
        tachionPositions.add(mutableSetOf(input[0].indexOf("S")))
        var count = 0
        println(tachionPositions)
        for (i in 1..<input.size) {
            tachionPositions.add(mutableSetOf())
            tachionPositions[i - 1].forEach {
                if (input[i].substring(it, it + 1) == "^") {
                    if (it - 1 >= 0) {
                        tachionPositions[i].add(it - 1)
                    }
                    if (it + 1 < input[0].length) {
                        tachionPositions[i].add(it + 1)
                    }
                    count++
                } else {
                    tachionPositions[i].add(it)
                }

            }
            println(tachionPositions)
        }

        return count
    }


    fun part2(input: List<String>): Int {

        var setTest = mutableSetOf<MutableList<Int>>()
        setTest.add(mutableListOf(1,2,3))
        setTest.add(mutableListOf(1,2,3))
        setTest.add(mutableListOf(1,2,3))
        println(setTest)


        var worlds = mutableSetOf<MutableList<Int>>()
        worlds.add(mutableListOf(input[0].indexOf("S")))
        for (i in 1..<input.size) {
            println("LINE: $i")
            var currentWorlds = worlds.toMutableList()
            var worldSplit = false
            for (w in currentWorlds.size-1 downTo 0) {
                var tachionPosition = currentWorlds[w].last()
                println("world $w position $tachionPosition")
                if (input[i].substring(tachionPosition, tachionPosition + 1) == "^") {
                    var splitLeft = false
                    if (tachionPosition - 1 >= 0) {
                        currentWorlds[w].add(tachionPosition - 1)
                        println("splitting left! world $w position $tachionPosition -> ${tachionPosition-1} path to now: ${currentWorlds[w]} line: ${i+1} size ${currentWorlds[w].size}" )
                        splitLeft = true
                    }
                    if (tachionPosition + 1 < input[0].length) {
                        var newPositions = mutableListOf<Int>()
                        if(splitLeft) {
                            newPositions.addAll(currentWorlds[w].dropLast(1))
                        } else {
                            newPositions.addAll(currentWorlds[w])
                        }
                        newPositions.add(tachionPosition + 1)
                        println("splitting right! world $w position $tachionPosition -> ${tachionPosition+1} path to now: ${newPositions} line: ${i + 1} size ${currentWorlds[w].size}" )
                        currentWorlds.add(newPositions)
                        worldSplit = true
                    }
                } else {
                    currentWorlds[w].add(tachionPosition)
                    println("no split! world $w position $tachionPosition -> ${tachionPosition} path to now: ${currentWorlds[w]} line: ${i + 1} size ${currentWorlds[w].size}" )
                }
            }
            if(worldSplit) {
                worlds.addAll(currentWorlds.toSet())
            }
            println(worlds::class.simpleName)
            println(worlds.joinToString(separator = "") { "$it\n" })
            if(worlds.size == 3) {
                println(worlds.toList()[0] == worlds.toList()[1])
            }
        }
        println(worlds.joinToString { "$it\n" })
        println(worlds.size)
        return 0
    }
//        var tachionPositions = mutableListOf<MutableSet<MutableSet<Int>>>()
//        tachionPositions.add(mutableSetOf(mutableSetOf(input[0].indexOf("S"))))
//
//        for (i in 1..<input.size) {
//            tachionPositions.add(mutableSetOf())
//            tachionPositions[i - 1].forEachIndexed { j, tachionSet ->
//                tachionSet.forEach {
//                    println("tachionset: $tachionSet")
//                    if (input[i].substring(it, it + 1) == "^") {
//                        if (it - 1 >= 0) {
//                            var newTachionSet = mutableSetOf<Int>()
//                            newTachionSet.addAll(tachionSet)
//                            newTachionSet.add(it-1)
//                            tachionPositions[i].add(newTachionSet)
//                        }
//                        if (it + 1 < input[0].length) {
//                            var newTachionSet = mutableSetOf<Int>()
//                            newTachionSet.addAll(tachionSet)
//                            newTachionSet.add(it-1)
//                            tachionPositions[i].add(newTachionSet)
//                        }
//
//                    } else {
//                        var newTachionSet = mutableSetOf<Int>()
//                        newTachionSet.addAll(tachionSet)
//                        newTachionSet.add(it)
//                        tachionPositions[i].add(newTachionSet)
//                    }
//
//                }
//            }
//            println(tachionPositions)
//
//        }
//        return tachionPositions.size


    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day07_2025_test")
    part2(input).println()
//    part2(input).println()
}

