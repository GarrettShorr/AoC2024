import java.lang.Integer.max
import kotlin.math.abs
import kotlin.math.min

data class Antenna(var row: Int, var col: Int, var freq : String, var maxR : Int = 0, var maxC: Int= 0) {
    fun getAntinodes(other: Antenna): List<Antenna> {
        val dr = this.row - other.row
        val dc = this.col - other.col
        var first = Antenna(
            max(this.row, other.row) + abs(dr),
            max(this.col, other.col) + abs(dc),
            this.freq
        )
        var second = Antenna(
            min(this.row, other.row) -  abs(dr),
            min(this.col, other.col) - abs(dc),
            this.freq
        )
        if(dr * dc < 0) {
            first.col = min(this.col, other.col) - abs(dc)
            second.col = max(this.col, other.col) + abs(dc)
        }


        return listOf(first, second)
    }

    fun getAntinodes2(other: Antenna): List<Antenna> {
        val dr = this.row - other.row
        val dc = this.col - other.col
        var antinodes = mutableListOf<Antenna>()
        antinodes.add(this)
        antinodes.add(other)
        for(i in 1..100) {
            var first = Antenna(
                max(this.row, other.row) + abs(dr) * i,
                max(this.col, other.col) + abs(dc) * i,
                this.freq
            )
            var second = Antenna(
                min(this.row, other.row) - abs(dr) * i,
                min(this.col, other.col) - abs(dc) * i,
                this.freq
            )
            if (dr * dc < 0) {
                first.col = min(this.col, other.col) - abs(dc) * i
                second.col = max(this.col, other.col) + abs(dc) * i
            }
            antinodes.addAll(listOf(first, second))
        }


        return antinodes
    }

//    fun getAntinodes2(other: Antenna): List<Antenna> {
//        var current = Antenna(this.row, this.col, this.freq)
//        var pair = Antenna(other.row, other.col, other.freq)
//        val dr = this.row - other.row
//        val dc = this.col - other.col
//        var antinodes = mutableListOf<Antenna>()
//        var nextRow = max(this.row, other.row) + abs(dr)
//        if(dr * dc < 0) {
//            var nextCol = min(this.col, other.col) - abs(dc)
//
//            while(nextRow < maxR && nextCol >= 0) {
//                var first = Antenna(
//                    max(current.row, pair.row) + abs(dr),
//                    min(current.col, pair.col) - abs(dc),
//                    this.freq
//                )
//
//                antinodes.add(first)
//                current = pair
//                pair = first
//                nextCol = min(current.col, pair.col) - abs(dc)
//                nextRow = max(current.row, pair.row) + abs(dr)
//
//            }
//
//            var second = Antenna(
//                min(this.row, other.row) -  abs(dr),
//                max(this.col, other.col) - abs(dc),
//                this.freq
//            )
//        }
//        var first = Antenna(
//            max(this.row, other.row) + abs(dr),
//            max(this.col, other.col) + abs(dc),
//            this.freq
//        )
//        var second = Antenna(
//            min(this.row, other.row) -  abs(dr),
//            min(this.col, other.col) - abs(dc),
//            this.freq
//        )
//        if(dr * dc < 0) {
//            first.col = min(this.col, other.col) - abs(dc)
//            second.col = max(this.col, other.col) + abs(dc)
//        }
//
//
//        return listOf(first, second)
//    }
}


fun main() {
    fun findAllAntennae(grid: List<List<String>>) : List<Antenna> {
        val antennae = mutableListOf<Antenna>()
        grid.forEachIndexed{ r, line ->
            line.forEachIndexed{ c, char -> antennae.add(Antenna(r,c,char)) }
        }
        return antennae.filter { it.freq != "." }
    }

    fun isolateFrequencies(antennae : List<Antenna>) : Map<String, MutableList<Antenna>> {
        var freqMap = mutableMapOf<String, MutableList<Antenna>>()
        antennae.forEach { freqMap.getOrPut(it.freq) { mutableListOf() }.add(it) }
        return freqMap
    }

    fun findAllAntinodes(antennae: MutableList<Antenna>) : MutableSet<Antenna>{
        val antinodes = mutableSetOf<Antenna>()
        for(i in antennae.indices) {
            for(j in i + 1..<antennae.size) {
                antinodes.addAll(antennae[i].getAntinodes(antennae[j]))
            }
        }
        return  antinodes
    }
    fun findAllAntinodes2(antennae: MutableList<Antenna>) : MutableSet<Antenna>{
        val antinodes = mutableSetOf<Antenna>()
        for(i in antennae.indices) {
            for(j in i + 1..<antennae.size) {
                antinodes.addAll(antennae[i].getAntinodes2(antennae[j]))
            }
        }
        return  antinodes
    }



    fun part1(input: List<String>): Int {
        val antennae = findAllAntennae(input.map { it.windowed(1) })
        val freqMap = isolateFrequencies(antennae)
        val freqs = freqMap.keys
        var antinodes = mutableSetOf<Antenna>()
        freqs.forEach { antinodes.addAll(findAllAntinodes(freqMap[it]!!)) }
        antinodes = antinodes.filter { it.row < input.size && it.row >= 0
            && it.col < input[0].length && it.col >= 0 }.toMutableSet()
        println(antinodes)
        var uniqueLocs = mutableSetOf<Pair<Int,Int>>()
        for(antinode in antinodes) {
            uniqueLocs.add(Pair(antinode.row, antinode.col))
        }
        return uniqueLocs.size
    }


    fun findAllAntennae2(grid: List<List<String>>) : List<Antenna> {
        val antennae = mutableListOf<Antenna>()
        grid.forEachIndexed{ r, line ->
            line.forEachIndexed{ c, char -> antennae.add(Antenna(r,c,char, grid.size, grid[0].size)) }
        }
        return antennae.filter { it.freq != "." }
    }

    fun part2(input: List<String>): Int {
        val antennae = findAllAntennae2(input.map { it.windowed(1) })
        val freqMap = isolateFrequencies(antennae)
        val freqs = freqMap.keys
        var antinodes = mutableSetOf<Antenna>()
        freqs.forEach { antinodes.addAll(findAllAntinodes2(freqMap[it]!!)) }
        antinodes = antinodes.filter { it.row < input.size && it.row >= 0
            && it.col < input[0].length && it.col >= 0 }.toMutableSet()
        println(antinodes)
        var uniqueLocs = mutableSetOf<Pair<Int,Int>>()
        for(antinode in antinodes) {
            uniqueLocs.add(Pair(antinode.row, antinode.col))
        }
        return uniqueLocs.size
    }




    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}

