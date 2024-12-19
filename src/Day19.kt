import java.util.*
import kotlin.math.abs

fun main() {
  fun towelTokenizer(design: String, availablePatterns: List<String>): Map<String, Int> {
    var start = 0
    var end = design.length
    var patternsUsed = mutableMapOf<String, Int>().withDefault { 0 }
    var patterns = availablePatterns.sortedByDescending { it.length }.toMutableList()
    while (patterns.size > 0) {
      while (start < design.length) {
        val subPattern = design.substring(start, end)
        if (subPattern in patterns) {
          if (patternsUsed[subPattern] != null) {
            patternsUsed[subPattern] = patternsUsed[subPattern]!! + 1
          } else {
            patternsUsed[subPattern] = 1
          }
          start += subPattern.length
          end = design.length
        } else {
          end--
        }
        if (end < 0 || end < start) {
          patternsUsed = mutableMapOf<String, Int>()
          patterns.shuffle()
          start = 0
          end = design.length
          break
        }
        if (start == end) {
          return patternsUsed
        }
//            println("start: $start end: $end")
      }
    }
    return patternsUsed

  }




  fun canCreateDesign(design: String, patterns: List<String>): Boolean {
    if (design.length == 0) {
      return true
    }

    val possibleUsefulPatterns = patterns.filter { design.contains(it) }
    possibleUsefulPatterns.forEach {
      val patterns = possibleUsefulPatterns.shuffled()
      for (pattern in patterns) {
        if (design.indexOf(pattern) == 0) {
          if (design.length == pattern.length) {
            return true
          }
          return canCreateDesign(
            design.substring(pattern.length + 1),
            patterns.filter { it.length <= design.length - pattern.length })
        }
      }
    }
    return false


//        println(possibleUsefulPatterns.size)
    return towelTokenizer(design, possibleUsefulPatterns).keys.size > 0
  }


  fun dfs(design: String, patterns: List<String>): Boolean {
    println(design)
    if (design.isEmpty() || design in patterns) {
      return true
    }

    for (pattern in patterns) {
      if (design.startsWith(pattern)) {
        var thisPatternWorks = dfs(design.substring(pattern.length), patterns.filter { design.contains(it) })
        if(thisPatternWorks) {
          return true
        }
      }
    }
    return false
  }

  fun dfs2(design: String, patterns: List<String>, known: MutableMap<Pair<String, List<String>>, Long>): Long {
    if (design.isEmpty()) {
      return 1
    }
    if(known[Pair(design, patterns)] != null) {
      return known[Pair(design, patterns)]!!
    }

    var sum = 0L
    for (pattern in patterns) {
      if (design.startsWith(pattern)) {
        sum += dfs2(design.substring(pattern.length), patterns.filter { design.contains(it) }, known)
      }
    }
    known[Pair(design, patterns)] = sum
    return sum
  }



  fun part1(input: List<String>): Int {
    val patterns = input[0].split(", ")
//        println(patterns.size)
//        println(towelTokenizer("bwurrg", patterns))


    val designs = input.subList(2, input.size)
    designs.forEach { design->
      println(design)
      println(dfs(design, patterns.filter { design.contains(it) }))
      println()
    }

    return designs.count { design ->
      dfs(design, patterns.filter { design.contains(it) })
    }

//        println(permutations(designs))
//        val counts = mutableListOf<Int>()
//        repeat(0) {
//            counts.add( designs.count { canCreateDesign(it, patterns) })
//        }
//        return counts.max()
//        val designMap = mutableMapOf<String, Map<String, Int>>()
//        for(design in designs) {
//            designMap[design] = towelTokenizer(design, patterns)
//        }
//        println(designMap)
//        return designs.count { towelTokenizer(it, patterns).keys.size > 0 }

//        return 0
    }


    fun part2(input: List<String>): Long {
      val patterns = input[0].split(", ")
//        println(patterns.size)
//        println(towelTokenizer("bwurrg", patterns))


      val designs = input.subList(2, input.size)
      return designs.sumOf { design ->
        println(design)
        dfs2(design, patterns.filter { design.contains(it) }, mutableMapOf())
      }

    }


    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day19")
//    part1(input).println()
    part2(input).println()
  }


