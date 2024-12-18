import kotlin.math.min

fun main() {
    fun getComboOperand(operand: Int, a: Long, b: Long, c: Long): Long {
        return when(operand) {
            0, 1, 2, 3 -> operand.toLong()
            4 -> a
            5 -> b
            6 -> c
            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        var a = 0L
        var b = 0L
        var c = 0L
//        var program = mutableListOf(0,3,5,4,3,0)
//        var a = 0
//        var b = 0
//        var c = 0
        var program = mutableListOf(2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0)
        var instructionPointer = 0
        var output = mutableListOf<Long>()
//        var minA = 35581177699116
        var minA = 3L * 8
        minA = 24L * 8
        minA = 192L * 8
        minA = 1538L * 8
        minA = 12304L * 8
        minA = 98446L * 8
        minA = 788830L * 8
        minA = 6310647L * 8
        minA = 50485181L * 8
        minA = 403881454L * 8
        minA = 3231051633L * 8
        minA = 25848413064L * 8
        minA = 206787304516L * 8
        minA = 1654298436134L * 8
        minA = 13234387489075L * 8
        minA = 105875099912602
//        minA = 1992L * 8
//        minA = 15941L * 8
        println(29.toString(2))
//        println(Math.pow(8.0, 16.0).toLong())
//        println(Math.pow(2.0, 48.0).toLong())
//        var minA = 105075099993696L
      //           106077315697050L
//        var minA = 106077214456642L
        // 105875099371150
//        var minA = 110475360953098  10 digits with 1234678
//        var minA = 35184785000000L
//        println("${184.toString(2)}}")
//        outer@while(true) {
        repeat(1) {
            a = minA
            instructionPointer = 0
            while (instructionPointer < program.size - 1) {
                var opcode = program[instructionPointer]
                var operand = program[instructionPointer + 1]
                var willJump = true
                when (opcode) {
                    0 -> {
//                        a = (a / (Math.pow(2.0, getComboOperand(operand, a, b, c).toDouble()))).toLong()
                        a = a shr getComboOperand(operand, a, b, c).toInt()
                    }

                    1 -> {
                        b = b xor operand.toLong()
                    }

                    2 -> {
                        b = getComboOperand(operand, a, b, c) % 8
                    }

                    3 -> {
                        if (a != 0L) {
                            instructionPointer = operand
                            willJump = false
                        }
                    }

                    4 -> {
                        b = b xor c
                    }

                    5 -> {
                        output.add(getComboOperand(operand, a, b, c) % 8L)
                    }

                    6 -> {
//                        b = (a / (Math.pow(2.0, getComboOperand(operand, a, b, c).toDouble()))).toLong()
                        b = a shr getComboOperand(operand, a, b, c).toInt()
                    }

                    7 -> {
//                        c = (a / (Math.pow(2.0, getComboOperand(operand, a, b, c).toDouble()))).toLong()
                        c = a shr getComboOperand(operand, a, b, c).toInt()
                    }
                }
                if (willJump) {
                    instructionPointer += 2
                }


//            println("2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0")
//            if(output.takeLast(12) == listOf(7L, 5L, 0L, 3L, 1L, 6L, 4L, 3L, 5L, 5L, 3L, 0L)) {
//            if(output.takeLast(9) == listOf(3L, 1L, 6L, 4L, 3L, 5L, 5L, 3L, 0L)) {
//                println("****************$minA ${output.joinToString(",")}")
//            }
//            if(output.take(5) == listOf(2L, 4L, 1L, 5L, 7L)) {
//                println("$minA ${output.joinToString(",")}")
//                for(i in 2..7) {
//                    if(minA % i == 0L) {
//                        print("Divisible by: $i ")
//                    }
//                }
//                println()
//            }
//            if (output.size == program.size && output.zip(program).all { it.first == it.second.toLong() }) {
//                println(minA)
//                break@outer
            }
            println("${output.joinToString(",")}  $minA ")

//            if(output[0]==2L) {
//            println("starts with 2: ${minA.toString(2)}")
//            }
//            if(minA > 106077315697050L) {
            if (minA > 18918409735554) {
                println("too high")
//                break@outer
            }
//            if(minA % 1000000L == 0L) {

            minA += 1

            output.clear()
            a = 0L
            b = 0L
            c = 0L
        }
//        }


        println(output.joinToString(","))
        return 0
    }



    fun part2(input: List<String>): Int {
        val output = mutableListOf<Long>()
        var program = mutableListOf(2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0)
        var programCopy = mutableListOf(2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0)

        for(i in 0L..16) {

            var j = i
            println(
                "with $i: output: ${
                    ((((i % 8) xor 5) xor 6) xor (i.toDouble() / (Math.pow(
                        2.0,
                        ((i % 8) xor 3).toDouble()
                    ))).toLong()) % 8
                }"
            )
        }
//        var a = 3L  // 0
//        a = a shl 3
//        a += 0    // 3
//        a = a shl 3
//        a += 4    // 5
//        a = a shl 3
//        a += 4    // 5
//        a = a shl 3
//        a += 0    // 3
//        a = a shl 3
//        a += 14    // 4
//        a = a shl 2
//        a += 184    // 6, 1, 3
//        a = a shl 1
//        a += 3  // 0
//        a = a shl 3
//        a += 4    // 5
//        a = a shl 3
//        a += 60    // 7, 5
//        a = a shl 3
//
//        a += 2 // 1
//        a = a shl 3
//        a += 14    // 4,2
//        a = a shl 2

        var a = 29 // 0, 3
        a = a shl 6
        a += 52 // 5, 5
        a = a shl 7
        a += 118
        a = a shl 8
        a += 184



        println(a)


//        a = 1L   // 2
//        a = a shl 3
//        a += 7    // 4
//        a = a shl 3
//        a += 16 // 1  ???
//        a = a shl 1
//        a += 6    // 5
//        a = a shl 3
//        a += 4    // 7
//        a = a shl 3
//        a += 6    // 5
//        a = a shl 3
//        a += 2   // 0
//        a = a shl 3
//        a += 0    // 3
//        a = a shl 3
//        a += 16 // 1  ???
//        a = a shl 1
//        a += 5    // 6
//        a = a shl 3
//        a += 7    // 4
//        a = a shl 3
//        a += 0    // 3
//        a = a shl 3
//        a += 6    // 5
//        a = a shl 3
//        a += 6    // 5
//        a = a shl 3
//        a += 0    // 3
//        a = a shl 3
//        a += 2
//        println(a)



//            if(((((i%8) xor 5) xor 6) xor (i.toDouble()/(Math.pow(2.0, ((i%8) xor 3).toDouble()))).toLong())%8 == 0L) {
//                output.add(0L)
//                programCopy.removeLast()
//                repeat(15) {
//                    j shl 3
//                    for(k in 0..8) {
//                        j++
//                        if((((((j%8) xor 5) xor 6) xor (i.toDouble()/(Math.pow(2.0, ((j%8) xor 3).toDouble()))).toLong())%8) == programCopy.last().toLong()) {
//                            output.add(0,((((j%8) xor 5) xor 6) xor (i.toDouble()/(Math.pow(2.0, ((j%8) xor 3).toDouble()))).toLong())%8)
//                            programCopy.removeLast()
//                            break
//                        } else {
////                            println("couldn't find")
//                        }
//                    }
//
//                }
//            } else {
//                output.clear()
//                continue
//            }
//            if(output.size >= 12) {
////            if(output.take(2) == listOf(2L, 4L)) {
//                println("output $output with A=$j")
////            }
//            }
//
//            if(output.size == program.size && output.zip(program).all{ it.first == it.second.toLong() }) {
//                    println("output $output with A=$j")
//            }
//            output.clear()
//            programCopy = mutableListOf(2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0)
//        }
        return 0
    }


    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day17")
    part1(input).println()
//    part2(input).println()
}

