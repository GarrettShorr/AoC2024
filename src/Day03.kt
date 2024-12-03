fun main() {

    var first = true

    fun multiply(str: String): Int {
        //println(str.indexOf("mul(32"))
        var sum = 0
        var valid = "mul(0123456789),"
        var i = 0
        outer@while(i < str.length) {
            var start = str.indexOf("mul(", i)
            if(start < 0) {
                break
            }

            //println("index $i start $start")
            var end = str.indexOf(")", start)
            var chunk = str.substring(start + 4, end)
            for(letter in chunk) {
                if(letter !in valid) {
                    i = start + 1
                    continue@outer
                }
            }
         //   println(chunk)
         //   println(chunk.split(",").map { it.toInt() }.reduce { acc, next -> acc * next })
            sum += chunk.split(",").map { it.toInt() }.reduce { acc, next -> acc * next }
            i = end
        }
        return sum
    }

    var doIt = true

    fun multiply2Refactor2(str: String): Int {
        //println(str.indexOf("mul(32"))
        var sum = 0
        var valid = "mul(0123456789),"
        var i = 0
        outer@while(i < str.length) {
            var start = str.indexOf("mul(", i)
            if(start < 0) {
                break
            }

            //println("index $i start $start")
            var end = str.indexOf(")", start)
            var chunk = str.substring(start + 4, end)
            for(letter in chunk) {
                if(letter !in valid) {
                    i = start + 1
                    continue@outer
                }
            }
            //   println(chunk)
            //   println(chunk.split(",").map { it.toInt() }.reduce { acc, next -> acc * next })
            val lastDo = str.substring(0, start).lastIndexOf("do()")
            val lastDont = str.substring(0, start).lastIndexOf("don't()")
            if(lastDo > lastDont) {
                doIt = true
            } else if(lastDont > lastDo) {
                doIt = false
            }
            if(doIt) {
                sum += chunk.split(",").map { it.toInt() }.reduce { acc, next -> acc * next }
            }
            i = end
        }
        return sum
    }

//    fun multiply2Refactor(str: String): Int {
//        //println(str.indexOf("mul(32"))
//        var sum = 0
//        var valid = "mul(0123456789),"
//        var i = 0
//        var doIt = true
//        var lastEnd = 0
//        var filtered = ""
//
//
//        outer@while(i < str.length) {
//
//            var start = str.indexOf("mul(", i)
//            var doBeforeStart = str.lastIndexOf("do()", i)
//            var dontBeforeStart = str.lastIndexOf("don't()", i)
//
//            if(doBeforeStart > dontBeforeStart && doBeforeStart < start) {
//                println("DO BEFORE START")
//
//                doIt = true
//            }
//            if(dontBeforeStart > doBeforeStart && dontBeforeStart < start) {
//                println("DON'T BEFORE START")
//
//                doIt = false
//            }
//
//            if(start < 0) {
//                var beforeMult = str.substring(i)
//                println("START < 0: $beforeMult")
//                var doPhraseLoc = beforeMult.lastIndexOf("do()")
//                var dontPhraseLoc = beforeMult.lastIndexOf("don't()")
//                if(doPhraseLoc > 0) {
//                    doIt = doPhraseLoc > dontPhraseLoc
//                } else {
//                    doIt = dontPhraseLoc > 0
//                }
//                break
//            }
//
//            //println("index $i start $start")
//            var chunk = ""
//            var end = str.indexOf(")", start)
//            var nextMul = str.indexOf("mul(", start+1)
//            println("start: $start end: $end nextMul $nextMul section: ${str.substring(start, end)}")
//            if(end > start && end < nextMul) {
//                chunk = str.substring(start + 4, end)
//
//            } else {
//                println("BROKE HERE")
//                i = start + 1
//                continue@outer
//            }
//
//            for(letter in chunk) {
//                if(letter !in valid) {
//                    i = start + 1
//                    continue@outer
//                }
//            }
//            var begin = 0
//            if(start != 0) {
//                begin = lastEnd + 1
//            }
//            var beforeMult = str.substring(begin, start)
//
//            var doPhraseLoc = beforeMult.lastIndexOf("do()")
//            var dontPhraseLoc = beforeMult.lastIndexOf("don't()")
//            println("beforeMult: $beforeMult do: $doPhraseLoc don't $dontPhraseLoc ${doPhraseLoc > dontPhraseLoc}")
//            if(doPhraseLoc >= 0 && doPhraseLoc > dontPhraseLoc && doIt) {
//                println(" --- RE-ENABLED ---")
//                doIt = true
//                filtered += "do()"
//            } else if(doPhraseLoc >= 0 && doPhraseLoc > dontPhraseLoc) {
//                println(" --- ENABLED ---")
//                doIt = true
//                filtered += "do()"
//            }else if(dontPhraseLoc >= 0 && doPhraseLoc < dontPhraseLoc && !doIt) {
//                println(" --- RE-DISABLED ---")
//                doIt = false
//                filtered += "don't()"
//            } else if(doPhraseLoc >= 0 && doPhraseLoc < dontPhraseLoc) {
//                println(" --- DISABLED (do then don't before mult) ---")
//                doIt = false
//                filtered += "do()don't()"
//            }else if(doPhraseLoc < 0 && dontPhraseLoc < 0 && first) {
//                println(" --- STAYED ENABLED BY DEFAULT --- ")
//                doIt = true
//            }  else if(doPhraseLoc < 0 && dontPhraseLoc < 0 && !doIt) {
//                println(" --- STAYED DISABLED --- ")
//                doIt = false
//            } else if(doPhraseLoc < 0 && dontPhraseLoc < 0) {
//                println(" --- STAYED ENABLED --- ")
//                doIt = true
//            }  else if(doPhraseLoc < dontPhraseLoc && first) {
//                println(" --- DISABLED. FIRST DON'T ---")
//                doIt = false
//                first = false
//                filtered += "don't()"
//            } else if(doPhraseLoc < dontPhraseLoc) {
//                println(" --- DISABLED ---")
//                doIt = false
//                filtered += "don't()"
//            }
//           else if(first)  {
//                println(" --- ENABLED BY DEFAULT --- ")
//                doIt = true
//            }
////            else {
////                println(" --- WEIRD DEFAULT DISABLED ---")
////
////                doIt = false
////            }
//            filtered += "mul(" + chunk + ")"
//            println("chunk $chunk doIt $doIt first $first")
//            println(chunk.split(",").map { it.toInt() }.reduce { acc, next -> acc * next })
//            if(doIt || first) {
//                sum += chunk.split(",").map { it.toInt() }.reduce { acc, next -> acc * next }
//            }
//            i = end + 1
//            println("sum $sum")
//            lastEnd = end
//        }
//        println(filtered)
//        return sum
//    }
//
//    fun multiply2(str: String): Int {
//        //println(str.indexOf("mul(32"))
//        var sum = 0
//        var valid = "mul(0123456789),"
//        var i = 0
//        var doIt = true
//        var lastEnd = 0
//        var filtered = ""
//
//
//        outer@while(i < str.length) {
//
//            var start = str.indexOf("mul(", i)
//            if(start < 0) {
//                var beforeMult = str.substring(i)
//                println("START < 0: $beforeMult")
//                var doPhraseLoc = beforeMult.lastIndexOf("do()")
//                var dontPhraseLoc = beforeMult.lastIndexOf("don't()")
//                if(doPhraseLoc > 0) {
//                    doIt = doPhraseLoc > dontPhraseLoc
//                } else {
//                    doIt = dontPhraseLoc > 0
//                }
//                break
//            }
//
//            //println("index $i start $start")
//            var chunk = ""
//            var end = str.indexOf(")", start)
//            if(end > start) {
//                chunk = str.substring(start + 4, end)
//
//            } else {
//                i = start + 1
//                continue@outer
//            }
//
//            for(letter in chunk) {
//                if(letter !in valid) {
//                    i = start + 1
//                    continue@outer
//                }
//            }
//            var begin = 0
//            if(start != 0) {
//                begin = lastEnd + 1
//            }
//            var beforeMult = str.substring(begin, start)
//
//            var doPhraseLoc = beforeMult.lastIndexOf("do()")
//            var dontPhraseLoc = beforeMult.lastIndexOf("don't()")
//            println("beforeMult: $beforeMult do: $doPhraseLoc don't $dontPhraseLoc ${doPhraseLoc > dontPhraseLoc}")
//            if(doPhraseLoc >= 0 && doPhraseLoc > dontPhraseLoc && doIt) {
//                println(" --- RE-ENABLED ---")
//                doIt = true
//                filtered += "do()"
//            } else if(doPhraseLoc >= 0 && doPhraseLoc > dontPhraseLoc) {
//                println(" --- ENABLED ---")
//                doIt = true
//                filtered += "do()"
//            }else if(dontPhraseLoc >= 0 && doPhraseLoc < dontPhraseLoc && !doIt) {
//                println(" --- RE-DISABLED ---")
//                doIt = false
//                filtered += "don't()"
//            } else if(doPhraseLoc >= 0 && doPhraseLoc < dontPhraseLoc) {
//                println(" --- DISABLED (do then don't before mult) ---")
//                doIt = false
//                filtered += "do()don't()"
//            }else if(doPhraseLoc < 0 && dontPhraseLoc < 0 && first) {
//                println(" --- STAYED ENABLED BY DEFAULT --- ")
//                doIt = true
//            }  else if(doPhraseLoc < 0 && dontPhraseLoc < 0 && !doIt) {
//                println(" --- STAYED DISABLED --- ")
//                doIt = false
//            } else if(doPhraseLoc < 0 && dontPhraseLoc < 0) {
//                println(" --- STAYED ENABLED --- ")
//                doIt = true
//            }  else if(doPhraseLoc < dontPhraseLoc && first) {
//                println(" --- DISABLED. FIRST DON'T ---")
//                doIt = false
//                first = false
//                filtered += "don't()"
//            } else if(doPhraseLoc < dontPhraseLoc) {
//                println(" --- DISABLED ---")
//                doIt = false
//                filtered += "don't()"
//            }
//            else if(first)  {
//                println(" --- ENABLED BY DEFAULT --- ")
//                doIt = true
//            }
////            else {
////                println(" --- WEIRD DEFAULT DISABLED ---")
////
////                doIt = false
////            }
//            filtered += "mul(" + chunk + ")"
//            println("chunk $chunk doIt $doIt first $first")
//            println(chunk.split(",").map { it.toInt() }.reduce { acc, next -> acc * next })
//            if(doIt || first) {
//                sum += chunk.split(",").map { it.toInt() }.reduce { acc, next -> acc * next }
//            }
//            i = end + 1
//            println("sum $sum")
//            lastEnd = end
//        }
//        println(filtered)
//        return sum
//    }
    fun part1(input: List<String>): Int {
        var sum = 0
        for(line in input) {
            sum += multiply(line)
        }
        
        return sum
    }


    
    fun part2(input: List<String>): Int {
        var sum = 0
        for(line in input) {
            sum += multiply2Refactor2(line)
            println("FULL LINE SUM $sum")
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day03")
    part2(input).println()
//    part2(input).println()
}
