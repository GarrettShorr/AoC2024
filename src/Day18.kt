import java.awt.Point
import java.util.*
import kotlin.math.abs

fun main() {
  fun getAdjacentPoints(point: Point, width: Int, height: Int, corrupted: List<Point>): List<Point> {
    var adjacents = mutableListOf<Point>()
    for (x in -1..1) {
      for (y in -1..1) {
        var nextLoc = Point(point.x + x, point.y + y)
        if (abs(x) != abs(y)
          && nextLoc.x >= 0 && nextLoc.x <= width
          && nextLoc.y >= 0 && nextLoc.y <= height
          && nextLoc !in corrupted
        ) {
          adjacents.add(nextLoc)
        }
      }
    }
    return adjacents
  }

  fun part1(input: List<String>): Int {
    val HEIGHT = 70
    val WIDTH = 70
    val TOTAL_BYTES = 1024
    var corruptedBytes = input.map { it.split(",").map { it.toInt() } }.map { Point(it[0], it[1]) }
      .take(TOTAL_BYTES)
    var start = Point(0, 0)
    var end = Point(WIDTH, HEIGHT)
    val WEIGHT = 1

    val distances = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
    val priorityQueue = PriorityQueue<Pair<Point, Int>>(compareBy { it.second }).apply { add(start to 0) }
    val visited = mutableSetOf<Pair<Point, Int>>()

    distances[start] = 0
    while (priorityQueue.isNotEmpty()) {
      val (node, currentDist) = priorityQueue.poll()
      if (visited.add(node to currentDist)) {
        val adjacentLocations: List<Point> = getAdjacentPoints(node, WIDTH, HEIGHT, corruptedBytes)
        adjacentLocations.forEach { adjacent ->
          val totalDist = currentDist + WEIGHT
          if (totalDist < distances.getValue(adjacent)) {
            distances[adjacent] = totalDist
            if (priorityQueue.none { it.first == adjacent }) {
              priorityQueue.add(adjacent to totalDist)
//              println(priorityQueue)
            }
          }
        }
      }
    }

    println(distances)

    // backtrack
    var path = mutableListOf<Point>()
    var currPoint = end
    path.add(currPoint)
    do {
      val adjacents = getAdjacentPoints(currPoint, WIDTH, HEIGHT, corruptedBytes)

//      println("adjacents: ${distances.keys.filter {
//        (abs(it.x - currPoint.x) == 1 && it.x == currPoint.x) xor
//          (abs(it.y - currPoint.y) == 1 && it.y == currPoint.y) }.filterNot { it in path }}")
      val nextPoint = distances.keys.filter { it in adjacents }
        .sortedBy { distances[it] }.filterNot { it in path }[0]
      currPoint = nextPoint
      path.add(currPoint)
    } while (nextPoint != start)

    for (y in 0..HEIGHT) {
      for (x in 0..WIDTH) {
        if (Point(x, y) in path) {
          print("O")
        } else if (Point(x, y) in corruptedBytes) {
          print("#")
        } else {
          print(".")
        }
      }
      println()
    }

    return path.toMutableSet().size - 1

  }


  fun part2(input: List<String>): String {
    val HEIGHT = 70
    val WIDTH = 70
    var TOTAL_BYTES = 1024
    val WEIGHT = 1
    var distances = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
    var end = Point(WIDTH, HEIGHT)
    var start = Point(0, 0)
    var corruptedBytes = listOf<Point>()
    do {
      TOTAL_BYTES++
      distances = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
      corruptedBytes = input.map { it.split(",").map { it.toInt() } }.map { Point(it[0], it[1]) }
        .take(TOTAL_BYTES)
      val priorityQueue = PriorityQueue<Pair<Point, Int>>(compareBy { it.second }).apply { add(start to 0) }
      val visited = mutableSetOf<Pair<Point, Int>>()

      distances[start] = 0
      while (priorityQueue.isNotEmpty()) {
        val (node, currentDist) = priorityQueue.poll()
        if (visited.add(node to currentDist)) {
          val adjacentLocations: List<Point> = getAdjacentPoints(node, WIDTH, HEIGHT, corruptedBytes)
          adjacentLocations.forEach { adjacent ->
            val totalDist = currentDist + WEIGHT
            if (totalDist < distances.getValue(adjacent)) {
              distances[adjacent] = totalDist
              if (priorityQueue.none { it.first == adjacent }) {
                priorityQueue.add(adjacent to totalDist)

              }
            }
          }
        }
//        println(priorityQueue)
//        println(visited)
//        println(distances[end])
      }
    }while((distances[end] != Int.MAX_VALUE && distances[end] != null) && TOTAL_BYTES <= input.size)

    println(distances)


    return input[TOTAL_BYTES-1]

  }


  // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


  // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
  val input = readInput("Day18")
//  part1(input).println()
    part2(input).println()
}

