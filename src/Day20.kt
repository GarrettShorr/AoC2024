import java.awt.Point
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.abs


fun main() {

  // returns if there is a cheat direction and which direction it is in
  fun getAdjacentPoints(grid: List<List<String>>, point: Point): List<Point> {
    var adjacents = mutableListOf<Point>()
    for (x in -1..1) {
      for (y in -1..1) {

        var nextLoc = Point(point.x + x, point.y + y)

        if (abs(x) != abs(y)
          && nextLoc.x >= 0 && nextLoc.x < grid[0].size
          && nextLoc.y >= 0 && nextLoc.y < grid.size
          && grid[nextLoc.y][nextLoc.x] in ".E"
        ) {
          adjacents.add(nextLoc)
        }
      }
    }
    return adjacents
  }

  // returns if there is a cheat direction and which direction it is in
  fun getAdjacentPointsWithCheat(grid: List<List<String>>, point: Point, wormhole: Pair<Point, Point>): List<Point> {
    var adjacents = mutableListOf<Point>()
    for (x in -1..1) {
      for (y in -1..1) {

        var nextLoc = Point(point.x + x, point.y + y)

        if (abs(x) != abs(y)
          && nextLoc.x >= 0 && nextLoc.x < grid[0].size
          && nextLoc.y >= 0 && nextLoc.y < grid.size
          && grid[nextLoc.y][nextLoc.x] in ".E"
        ) {
          adjacents.add(nextLoc)
        }
      }
    }
    if (point == wormhole.first) {
      adjacents.add(wormhole.second)
    }
    return adjacents.toMutableSet().toMutableList()
  }

  fun findPaths(grid: List<List<String>>): Map<Point, Int> {

    var start = Point(0, 0)
    var end = Point(0, 0)
    for (r in grid.indices) {
      for (c in grid[0].indices) {
        if (grid[r][c] == "S") {
          start = Point(c, r)
          if (grid[r][c] == "E") {
            end = Point(c, r)
          }
        }
      }
    }

    val distances = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
    val queue = ArrayDeque<Pair<Point, Int>>()
    val visited = mutableSetOf<Pair<Point, Int>>()

    val WEIGHT = 1
    distances[start] = 0
    queue.add(Pair(start, 0))
    while (queue.isNotEmpty()) {
      val (node, currentDist) = queue.removeFirst()
      if (visited.add(node to currentDist)) {
        if (node == end) {
          return distances
        }
        val adjacentLocations: List<Point> = getAdjacentPoints(grid, node)
        adjacentLocations.forEach { adjacent ->
          val totalDist = currentDist + WEIGHT
          if (totalDist < distances.getValue(adjacent)) {
            distances[adjacent] = totalDist
            queue.add(adjacent to totalDist)
          }
        }
      }
    }
    return distances
  }

  fun findPathsWithCheat(grid: List<List<String>>, wormhole: Pair<Point, Point>): Map<Point, Int> {

    var start = Point(0, 0)
    var end = Point(0, 0)
    for (r in grid.indices) {
      for (c in grid[0].indices) {
        if (grid[r][c] == "S") {
          start = Point(c, r)
        }
        if (grid[r][c] == "E") {
          end = Point(c, r)
        }
      }
    }


    val distances = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
    val queue = ArrayDeque<Pair<Point, Int>>()
    val visited = mutableSetOf<Pair<Point, Int>>()

    val WEIGHT = 1
    distances[start] = 0
    queue.add(Pair(start, 0))
    while (queue.isNotEmpty()) {
      val (node, currentDist) = queue.removeFirst()
      if (visited.add(node to currentDist)) {
//        if (node == end) {
//          return distances
//        }
        val adjacentLocations: List<Point> = getAdjacentPointsWithCheat(grid, node, wormhole)
        adjacentLocations.forEach { adjacent ->
          var totalDist = currentDist + WEIGHT
          if (node == wormhole.first && adjacent == wormhole.second)  {
            totalDist--
            totalDist += abs(wormhole.first.x-wormhole.second.x)
            totalDist += abs(wormhole.first.y-wormhole.second.y)
          }
          if (totalDist < distances.getValue(adjacent)) {
            distances[adjacent] = totalDist
            queue.add(adjacent to totalDist)
          }
        }
      }
    }
    return distances
  }


  fun getCheatableWalls(grid: List<List<String>>, point: Point): List<Point> {
    val cheatableWalls = mutableListOf<Point>()

    for (x in -1..1) {
      for (y in -1..1) {
        var nextLoc = Point(point.x + x, point.y + y)
        if (abs(x) != abs(y)
          && nextLoc.x >= 0 && nextLoc.x < grid[0].size
          && nextLoc.y >= 0 && nextLoc.y < grid.size
          && grid[nextLoc.y][nextLoc.x] in "#"
        ) {
          if (getAdjacentPoints(grid, nextLoc).size > 0) {
            cheatableWalls.add(nextLoc)
          }
        }
      }
    }
    return cheatableWalls
  }

  fun get20PicoCheatDestinations(grid: List<List<String>>, point: Point): List<Point> {
    val cheatableWalls = mutableListOf<Point>()
    if (grid[point.y][point.x] !in ".S") {
      return cheatableWalls
    }
    for (x in -20..20) {
      for (y in -20..20) {
        var nextLoc = Point(point.x + x, point.y + y)
        if (abs(x) + abs(y) <= 20
          && nextLoc.x >= 0 && nextLoc.x < grid[0].size
          && nextLoc.y >= 0 && nextLoc.y < grid.size
          && grid[nextLoc.y][nextLoc.x] in ".E"
        ) {
          cheatableWalls.add(nextLoc)
        }
      }
    }
    return cheatableWalls.toMutableSet().toMutableList()
  }

  fun part1(input: List<String>): Int {

    var start = Point(0, 0)
    var end = Point(0, 0)
    val grid = input.map { it.windowed(1) }
    for (r in grid.indices) {
      for (c in grid[0].indices) {
        if (grid[r][c] == "S") {
          start = Point(c, r)
        }
        if (grid[r][c] == "E") {
          end = Point(c, r)
        }
      }
    }

    val distances = findPaths(grid)

    val path = distances.keys
    println(path.size)
    val maxTime = distances[end]!!
    val possibleCheats = mutableSetOf<Point>()
    for (point in path) {
      possibleCheats.addAll(getCheatableWalls(grid, point))
    }

    val times = mutableListOf<Int>()
    for (cheat in possibleCheats) {
      times += findPaths(grid.map { it.toMutableList() }.toMutableList().apply { this[cheat.y][cheat.x] = "." })[end]
        ?: -1
    }
    return times.count { maxTime - it >= 100 }

  }


  fun part2(input: List<String>): Int {
    var start = Point(0, 0)
    var end = Point(0, 0)
    val grid = input.map { it.windowed(1) }
    for (r in grid.indices) {
      for (c in grid[0].indices) {
        if (grid[r][c] == "S") {
          start = Point(c, r)
        }
        if (grid[r][c] == "E") {
          end = Point(c, r)
        }
      }
    }
    val distances = findPaths(grid)

    val path = distances.keys
    val maxTime = distances[end]!!
    val possibleCheats = mutableSetOf<Pair<Point, Point>>()
    for (point in path) {
      val skipLandings = get20PicoCheatDestinations(grid, point)
      skipLandings.forEach { possibleCheats.add(Pair(point, it)) }
    }

    val times = mutableListOf<Int>()
    var i = 0
    for (cheat in possibleCheats) {
      i++
      val paths = findPathsWithCheat(grid, cheat)
      if(i % 10000 == 0) {
        println("$i of ${possibleCheats.size} cheats done")
      }
      times += paths[end] ?: -1
    }


//    println(times)
//    for (i in 50..76 step 2) {
//      println(" ${times.count { maxTime - it == i }} cheats at $i picos:")
//    }
//    println(times)
    return times.count { maxTime - it >= 30 }

  }


// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


// Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
  val input = readInput("Day20_extra")
//  part1(input).println()
  part2(input).println()
}


//      for(r in grid.indices) {
//        for(c in grid[0].indices) {
//          if(r == cheat.first.y && c == cheat.first.x) {
//            print("1")
//          } else if(r == cheat.second.y && c == cheat.second.x) {
//            print("2")
//          } else {
//            print(grid[r][c])
//          }
//        }
//        println()
//      }