import javax.swing.text.Position
import kotlin.math.abs
import kotlin.math.sqrt

// -1 will be empty spaces
data class Block(var id: Int) {
    override fun toString(): String {
        if(id >=  0) {
            return "$id"
        } else {
            return "."
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val diskMap = input.map { it.windowed(1).map { it.toInt() } }[0].chunked(2)
        val blockMap = mutableListOf<Block>()
//        println(diskMap)
        diskMap.forEachIndexed { i, pair ->
            for(j in 0..<pair[0]) {
                blockMap.add(Block(i))
            }
            if(pair.size > 1) {
                for (j in 0..<pair[1]) {
                    blockMap.add(Block(-1))
                }
            }
        }
//        println(blockMap)

        var lastNonEmptyIndex = blockMap.size - 1
        var firstEmpty = 0
        while(firstEmpty < lastNonEmptyIndex) {
            while(blockMap[lastNonEmptyIndex].id == -1) {
                lastNonEmptyIndex--
            }
            while(blockMap[firstEmpty].id != -1) {
                firstEmpty++
            }
            blockMap.add(Block(-1))
            val last = blockMap.removeAt(lastNonEmptyIndex)

            blockMap[firstEmpty] = last

            lastNonEmptyIndex = blockMap.size - 1
            firstEmpty = 0
            while(blockMap[lastNonEmptyIndex].id == -1) {
                lastNonEmptyIndex--
            }
            while(blockMap[firstEmpty].id != -1) {
                firstEmpty++
            }
        }
//        println(blockMap)

        // calculate
        var sum = 0L
        for(i in blockMap.indices) {
            if(blockMap[i].id != -1) {
                sum += blockMap[i].id * i
            }
        }
        return sum
    }



    fun part2(input: List<String>): Long {
        val diskMap = input.map { it.windowed(1).map { it.toInt() } }[0].chunked(2)
        val blockMap = mutableListOf<Block>()
//        println(diskMap)
        diskMap.forEachIndexed { i, pair ->
            for(j in 0..<pair[0]) {
                blockMap.add(Block(i))
            }
            if(pair.size > 1) {
                for (j in 0..<pair[1]) {
                    blockMap.add(Block(-1))
                }
            }
        }
//        println(blockMap)

        var lastNonEmptyIndex = blockMap.size - 1
        var firstEmpty = 0
        while(firstEmpty < lastNonEmptyIndex) {
            while(blockMap[lastNonEmptyIndex].id == -1) {
                lastNonEmptyIndex--
            }
            while(blockMap[firstEmpty].id != -1) {
                firstEmpty++
            }
            var lastContiguousEmpty = firstEmpty
            while(blockMap[lastContiguousEmpty].id == -1) {
                lastContiguousEmpty++
            }
            lastContiguousEmpty-- // because we go past it by 1
            var emptyLength = lastContiguousEmpty-firstEmpty+1
            var firstInContiguousBlock = lastNonEmptyIndex
            var currentId = blockMap[lastNonEmptyIndex].id
            while(firstInContiguousBlock >= 0 && blockMap[firstInContiguousBlock].id == currentId && firstInContiguousBlock >= firstEmpty) {
                firstInContiguousBlock--
            }
            firstInContiguousBlock++ // because we go past it by 1
            var blockLength = lastNonEmptyIndex - firstInContiguousBlock + 1
            while(blockLength > emptyLength && firstEmpty < blockMap.size -1) {
                firstEmpty = lastContiguousEmpty+1
                while(blockMap[firstEmpty].id != -1) {
                    firstEmpty++
                }
                lastContiguousEmpty = firstEmpty
                while(blockMap[lastContiguousEmpty].id == -1 && lastContiguousEmpty < blockMap.size - 1) {
                    lastContiguousEmpty++
                }
                lastContiguousEmpty--
                emptyLength = lastContiguousEmpty - firstEmpty + 1
//                println("$firstEmpty $lastContiguousEmpty")
            }
            if(blockLength <= emptyLength && firstEmpty < lastNonEmptyIndex) {
                // move it here
                for(i in 0..<blockLength) {
                    blockMap[firstEmpty] = Block(currentId)
                    blockMap[lastNonEmptyIndex] = Block(-1)
                    lastNonEmptyIndex--
                    firstEmpty++
                }
                while(blockMap[firstEmpty].id != -1) {
                    firstEmpty++
                }
            } else {
                // move on to the next id
                firstEmpty = 0
                while(lastNonEmptyIndex >= 0 && blockMap[lastNonEmptyIndex].id == currentId && blockMap[lastNonEmptyIndex].id != -1) {
                    lastNonEmptyIndex--
                }
            }
            firstEmpty = 0
//            println(blockMap)
        }
//        println(blockMap)

        // calculate
        var sum = 0L
        for(i in blockMap.indices) {
            if(blockMap[i].id != -1) {
                sum += blockMap[i].id * i
            }
        }
        return sum
    }




    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val start = System.currentTimeMillis()

    val input = readInput("Day09")
    part1(input).println()
//    part2(input).println()
    val end = System.currentTimeMillis()
    println("time for part 1: ${end - start} ms")
}

