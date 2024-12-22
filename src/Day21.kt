import java.util.*

// djikstra graphs for shortest paths
// A IS PUSH
// make a map of pairs of buttons to direction to go from first to second
// regex for digits

fun main() {

  val dangerMoves = mutableMapOf(
    Pair("A", "4") to "^^<<",
    Pair("3", "7") to "<<^^",
    Pair("4", "A") to ">>vv",
    Pair("7", "3") to "vv>>",
  )

  val illegalMoves = mutableMapOf(
    Pair("A", "4") to "<<^^",
    Pair("A", "7") to "<<^^^",
    Pair("7", "A") to "vvv>>",
    Pair("7", "0") to "vvv>",
    Pair("0", "7") to "<^^^",
    Pair("0", "4") to "<^^",
    Pair("4", "0") to "vv>",
    Pair("A", "1") to "<<^",
    Pair("1", "A") to "v>>",
    Pair("4", "A") to "vv>>",
    Pair("<", "^") to "^>",
    Pair("<", "A") to "^>>",
    Pair("A", "<") to "<<v",
    Pair("^", "<") to "<v",
  )

  val numpadDirectionMap = mutableMapOf<Pair<String, String>, String>(
    Pair("7", "8") to ">",
    Pair("7", "4") to "v",
    Pair("8", "7") to "<",
    Pair("8", "5") to "v",
    Pair("8", "9") to ">",
    Pair("9", "8") to "<",
    Pair("9", "6") to "v",
    Pair("4", "5") to ">",
    Pair("4", "7") to "^",
    Pair("4", "1") to "v",
    Pair("5", "4") to "<",
    Pair("5", "8") to "^",
    Pair("5", "6") to ">",
    Pair("5", "2") to "v",
    Pair("6", "5") to "<",
    Pair("6", "9") to "^",
    Pair("6", "3") to "v",
    Pair("1", "4") to "^",
    Pair("1", "2") to ">",
    Pair("2", "1") to "<",
    Pair("2", "5") to "^",
    Pair("2", "3") to ">",
    Pair("2", "0") to "v",
    Pair("3", "2") to "<",
    Pair("3", "A") to "v",
    Pair("3", "6") to "^",
    Pair("0", "A") to ">",
    Pair("0", "2") to "^",
    Pair("A", "0") to "<",
    Pair("A", "3") to "^",
  )

  val numpadGraph = mapOf<String, List<Pair<String, Int>>>(
    "7" to listOf(Pair("8", 1), Pair("4", 1)),
    "8" to listOf(Pair("7", 1), Pair("5", 1), Pair("9", 1)),
    "9" to listOf(Pair("8", 1), Pair("6", 1)),
    "4" to listOf(Pair("7", 1), Pair("5", 1), Pair("1", 1)),
    "5" to listOf(Pair("4", 1), Pair("8", 1), Pair("6", 1), Pair("2", 1)),
    "6" to listOf(Pair("9", 1), Pair("5", 1), Pair("3", 1)),
    "1" to listOf(Pair("4", 1), Pair("2", 1)),
    "2" to listOf(Pair("1", 1), Pair("5", 1), Pair("3", 1), Pair("0", 1)),
    "3" to listOf(Pair("2", 1), Pair("6", 1), Pair("A", 1)),
    "0" to listOf(Pair("2", 1), Pair("A", 1)),
    "A" to listOf(Pair("0", 1), Pair("3", 1))
  )

//  val numpadGraph = mapOf<String, List<Pair<String, Int>>>(
//    "7" to listOf(Pair("8", 1), Pair("4", 2)),
//    "8" to listOf(Pair("7", 3), Pair("5", 2), Pair("9", 1)),
//    "9" to listOf(Pair("8", 2), Pair("6", 1)),
//    "4" to listOf(Pair("7", 3), Pair("5", 1), Pair("1", 2)),
//    "5" to listOf(Pair("4", 4), Pair("8", 3), Pair("6", 2), Pair("2", 1)),
//    "6" to listOf(Pair("9", 2), Pair("5", 3), Pair("3", 1)),
//    "1" to listOf(Pair("4", 2), Pair("2", 1)),
//    "2" to listOf(Pair("1", 4), Pair("5", 3), Pair("3", 2), Pair("0", 1)),
//    "3" to listOf(Pair("2", 3), Pair("6", 1), Pair("A", 2)),
//    "0" to listOf(Pair("2", 2), Pair("A", 1)),
//    "A" to listOf(Pair("0", 2), Pair("3", 1))
//  )

  val arrowKeysGraph = mapOf<String, List<Pair<String, Int>>>(
    "^" to listOf(Pair("A", 1), Pair("v", 1)),
    "A" to listOf(Pair("^", 1), Pair(">", 1)),
    "<" to listOf(Pair("v", 1)),
    "v" to listOf(Pair("<", 1), Pair("^", 1), Pair(">", 1)),
    ">" to listOf(Pair("v", 1), Pair("A", 1)),
  )

//  val arrowKeysGraph = mapOf<String, List<Pair<String, Int>>>(
//    "^" to listOf(Pair("A", 2), Pair("v", 2)),
//    "A" to listOf(Pair("^", 2), Pair(">", 1)),
//    "<" to listOf(Pair("v", 1)),
//    "v" to listOf(Pair("<", 3), Pair("^", 2), Pair(">", 1)),
//    ">" to listOf(Pair("v", 2), Pair("A", 1)),
//  )


//


  val arrowKeysDirectionMap = mutableMapOf<Pair<String, String>, String>(
    Pair("^", "A") to ">",
    Pair("^", "v") to "v",
    Pair("<", "v") to ">",
    Pair("A", "^") to "<",
    Pair("A", ">") to "v",
    Pair("v", "<") to "<",
    Pair("v", "^") to "^",
    Pair("v", ">") to ">",
    Pair(">", "A") to "^",
    Pair(">", "v") to "<",
  )


  fun dijkstra(graph: Map<String, List<Pair<String, Int>>>, start: String): Map<String, Int> {
    val distances = mutableMapOf<String, Int>().withDefault { Int.MAX_VALUE }
    val priorityQueue = PriorityQueue<Pair<String, Int>>(compareBy { it.second }).apply { add(start to 0) }

    distances[start] = 0

    while (priorityQueue.isNotEmpty()) {
      val (node, currentDist) = priorityQueue.poll()
      graph[node]?.forEach { (adjacent, weight) ->
        val totalDist = currentDist + weight
        if (totalDist < distances.getValue(adjacent)) {
          distances[adjacent] = totalDist
          priorityQueue.add(adjacent to totalDist)
        }
      }
    }
    return distances
  }

  fun isAdjacent(key: String, currentLetter: String, numpad: Boolean): Boolean {
    var map = numpadGraph
    if (!numpad) {
      map = arrowKeysGraph
    }
    return map[key]!!.any { it.first == currentLetter }
  }

  fun getTravelPathToTarget(start: String, target: String, distances: Map<String, Int>, numpad: Boolean): String {
    if(start == target) {
      return "A"
    }

    var map = numpadDirectionMap
    if (!numpad) {
      map = arrowKeysDirectionMap
    }
    if (isAdjacent(start, target, numpad)) {
      return map[Pair(start, target)]!!
    }
    if (dangerMoves[Pair(start, target)] != null) {
      return dangerMoves[Pair(start, target)]!!
    }
    var path = ""
    var current = distances[target]!!
    var currentLetter = target
    var previousLetter = ""
    while (currentLetter != start) {
      current--
      while (distances.count { it.value == current } == 0) {
        current--
      }

      val previousLetter = distances.filter { isAdjacent(it.key, currentLetter, numpad) && current >= it.value }
        .keys.sortedBy { distances[it] }.toMutableList()[0]
//      println("prev: $previousLetter curr: $currentLetter")

      path = map[Pair(previousLetter, currentLetter)] + path
//      println(path)
      currentLetter = previousLetter
    }
    return path
  }

  fun permute(str: String): List<String> {
    if (str.length == 1) return listOf(str + "A")
    if (str.length == 2) return listOf("${str[0]}${str[1]}A", "${str[1]}${str[0]}A")
    val newStr = mutableListOf<String>()
    str.forEachIndexed { index, c ->
      permute(str.substring(0, index) + str.substring(index + 1)).forEach {
        newStr.add(c.toString() + it)
      }
    }
    return newStr
  }

  fun getCostAtNDepth(depth: Int, start: String, target: String, distances: Map<String, Int>, numpad: Boolean): Int {

    var directions = ""

    directions += getTravelPathToTarget(start, target, distances, numpad)
//    println("directions: $directions depth $depth start $start target $target")
    if (depth == 0) {
      return directions.length + 1 // +1 for the A
    }

    // permute the directions

//    println("ALL PERMUTES: ${permute(directions).toMutableSet()}  start: $start target: $target depth $depth")

    val allDirs = permute(directions).filterNot { it == illegalMoves[Pair(start, target)] }
    if(allDirs.isEmpty())
    println(allDirs)

    var lengths = mutableListOf<Int>()
    for(dir in allDirs) {
      var length = 0
//      println("THE DDIIR : $dir")
      var allPairs = dir.windowed(1).zipWithNext()
      for(pair in allPairs) {
        length += getCostAtNDepth(depth - 1, pair.first, pair.second, dijkstra(arrowKeysGraph, pair.first), false)
      }
      println("dir: $dir length: $length")
      lengths.add(length)
    }
    println(lengths)
    return lengths.min()

//   allDirs.forEach {
//      println(it.windowed(1).zipWithNext())
//     print("ALL TEH SUMS ")
//     println(
//      it.windowed(1).zipWithNext().sumOf {
//        getCostAtNDepth(depth - 1, it.first, it.second, dijkstra(arrowKeysGraph, it.first), false)
//      }
//     )
//    }
//
//
//    return allDirs.minOf {
////      println(it.windowed(1).zipWithNext())
//      it.windowed(1).zipWithNext().sumOf {
//        getCostAtNDepth(depth - 1, it.first, it.second, dijkstra(arrowKeysGraph, it.first), false)
//      }
//    }
  }


  fun part1(input: List<String>): Int {
    var complexityScore = 0
    for (line in input) {
      var sum = 0
      var code = line.windowed(1)
      var directions = ""
      var start = "A"
      for (letter in code) {
//        println("For start: $start to $letter: **********")

        val distances = dijkstra(numpadGraph, start)
        sum += getCostAtNDepth(4, start, letter, distances, true)
        start = letter
      }
      println("line $line  sum $sum")
      complexityScore += sum * line.replace("A", "").dropWhile { it == '0' }.toInt()
    }
    return complexityScore
  }


////      directions = directions.replace(">^>", ">>^")
////      directions = directions.replace("<^<", "<<^")
////      directions = directions.replace("^<^", "^^<")
////      directions = directions.replace("^>^", ">^^")
////      directions = directions.replace("v<v", "vv<")
////      directions = directions.replace("v>v", ">vv")
//      directions = directions.replace("^^<A", "<^^A")
//      directions = directions.replace(">^A", "^>A")
//      directions = directions.replace("^<A", "<^A")


//      directions = directions.replace("Av^vA", "Av>>A")
//      directions = directions.replace("A>>^A", "A^>>A")
//      directions = directions.replace("A<^<A", "A^<<A")
//      directions = directions.replace("A^<^A", "A^^<A")
//      directions = directions.replace("A^>^A", "A>^^A")
//      directions = directions.replace("Av<vA", "Avv<A")
//      directions = directions.replace("Av>vA", "A>vvA")
////      directions = directions.replace("^^<<", "<<^^")
//      directions = directions.replace("A^<^<A", "A^^<<A")
//      directions = directions.replace("A<^<^A", "A^^<<A")
//      directions = directions.replace("A^>^>A", "A>>^^A")
//      directions = directions.replace("A>^>^A", "A>>^^A")

//      println("first directions $directions")
//
//      code = directions.windowed(1)
//
//      directions = ""
//      start = "A"
//
//      for (letter in code) {
////        println("For start: $start to $letter: **********")
//        val distances = dijkstra(arrowKeysGraph, start)
////        println(distances)
//        directions += getTravelPathToTarget(start, letter, distances, false) + "A"
//        start = letter
//      }
//
////      directions = directions.replace("A>^>A", "A^>>A")
////      directions = directions.replace("A>>^A", "A^>>A")
////      directions = directions.replace("A<^<A", "A^<<A")
////      directions = directions.replace("A^<^A", "A^^<A")
////      directions = directions.replace("A^>^A", "A>^^A")
////      directions = directions.replace("Av<vA", "Avv<A")
////      directions = directions.replace("Av>vA", "A>vvA")
//////      directions = directions.replace("^^<<", "<<^^")
////      directions = directions.replace("A^<^<A", "A^^<<A")
////      directions = directions.replace("A<^<^A", "A^^<<A")
////      directions = directions.replace("A^>^>A", "A>>^^A")
////      directions = directions.replace("A>^>^A", "A>>^^A")
//      directions = directions.replace(">>^A", "^>>A")
//
//
//      println("2nd directions $directions")
//
//      code = directions.windowed(1)
//
//      directions = ""
//      start = "A"
//
//      for (letter in code) {
////            println("For start: $start to $letter: **********")
//        val distances = dijkstra(arrowKeysGraph, start)
////        println(distances)
//        directions += getTravelPathToTarget(start, letter, distances, false) + "A"
//        start = letter
//      }
//
//
////      directions = directions.replace("A>^>A", "A^>>A")
////      directions = directions.replace("A>>^A", "A^>>A")
////      directions = directions.replace("A<^<A", "A^<<A")
////      directions = directions.replace("A^<^A", "A^^<A")
////      directions = directions.replace("A^>^A", "A>^^A")
////      directions = directions.replace("Av<vA", "Avv<A")
////      directions = directions.replace("Av>vA", "A>vvA")
//////      directions = directions.replace("^^<<", "<<^^")
////      directions = directions.replace("A^<^<A", "A^^<<A")
////      directions = directions.replace("A<^<^A", "A^^<<A")
////      directions = directions.replace("A^>^>A", "A>>^^A")
////      directions = directions.replace("A>^>^A", "A>>^^A")
////
//      println("3rd directions $directions")
////
////      code = directions.windowed(1)
////
////      directions = ""
////      start = "A"
////
////      for (letter in code) {
//////            println("For start: $start to $letter: **********")
////        val distances = dijkstra(arrowKeysGraph, start)
////        println(distances)
////        directions += getTravelPathToTarget(start, letter, distances, false) + "A"
////        start = letter
////      }
//
//
////      println(directions)
//
//      var nums =
//      println(nums)
//      println(directions.length)
//      println(nums * directions.length)
//      sum += nums * directions.length
//
//    }
//    return sum

  fun part2(input: List<String>): Int {


    return 0
  }


  // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


  // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
  val input = readInput("Day21_test")
//  println(permute("A"))
  println(getCostAtNDepth(3, "A", "v", dijkstra(arrowKeysGraph, "A"), false))
//  part1(input).println()
//    part2(input).println()
}

