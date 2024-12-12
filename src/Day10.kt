import kotlin.math.abs

data class Trailhead(val row: Int, val col: Int) {
  var paths = mutableListOf<MutableList<Triple<Int, Int, Int>>>()

  fun score(): Int {
    var size9 = paths.asSequence().toMutableSet().filter { it.size == 9 }
    println("size 9 paths: $size9")
    var destinations = paths.map { it.last() }.toMutableSet()
    println("DESTINATIONS: $destinations")
    return paths.asSequence().toMutableSet().filter { it.size == 9 }.map { it.last() }.toMutableSet().count()
  }

  fun distinctScore(): Int {
    return paths.asSequence().toMutableSet().filter { it.size == 9 }.count()
  }

  override fun toString(): String {
    return "r: $row c: $col paths: $paths"
  }
}

fun main() {
  fun findNeighbors(grid: List<List<Int>>, row: Int, col: Int, height: Int): MutableList<Triple<Int, Int, Int>> {
    val neighbors = mutableListOf<Triple<Int, Int, Int>>()
    for (i in -1..1) {
      for (j in -1..1) {
        if (row + i >= 0 && row + i < grid.size && col + j >= 0 && col + j < grid[0].size) {
          if (abs(i) != abs(j) && grid[row + i][col + j] == height + 1) {
            neighbors.add(Triple(row + i, col + j, height + 1))
          }
        }
      }
    }
    println("row $row col $col neighbors $neighbors")
    return neighbors
  }

  fun findNeighbors(grid: List<List<Int>>, point: Triple<Int, Int, Int>): MutableList<Triple<Int, Int, Int>> {
    return findNeighbors(grid, point.first, point.second, point.third)
  }

  fun findPaths(grid: List<List<Int>>, trailhead: Trailhead) {
    val trailqueue = ArrayDeque<Triple<Int, Int, Int>>()
    val neighbors = findNeighbors(grid, trailhead.row, trailhead.col, 0)
    if (neighbors.size == 0) {
      return
    }
    trailqueue.addAll(neighbors)
    neighbors.forEach { trailhead.paths.add(mutableListOf(it)) }
    println(trailhead)
    val visited = mutableListOf<Triple<Int, Int, Int>>()
    while (trailqueue.isNotEmpty()) {
      val point = trailqueue.removeFirst()
      if(point in visited) {
        continue
      }
      println("CURRENT POINT: $point")

      val newNeighbors = findNeighbors(grid, point)


       for (path in trailhead.paths) {
          if (!path.contains(point) && path.last().third == point.third - 1 &&
            ((abs(path.last().first - point.first) == 1 && path.last().second == point.second) ||
                (abs(path.last().second - point.second) == 1 && path.last().first == point.first))
          ) {
            path.add(point)
          }
        }
      val newPaths = mutableListOf<MutableList<Triple<Int, Int, Int>>>()
      for(path in trailhead.paths) {
        if(!path.contains(point)) {
          if(path.last().third == point.third && point.third >= 2) {
            var oneLower = path[path.size-2]
            if ((abs(oneLower.first - point.first) == 1 && oneLower.second == point.second) ||
              (abs(oneLower.second - point.second) == 1 && oneLower.first == point.first)) {
              val newPath = path.map { it }.toMutableList()
              newPath[newPath.size-1] = point
                newPaths.add(newPath)

            }
          }
        }
      }

      if(newPaths.size > 0) {
        println("new paths added: $newPaths")
        trailhead.paths.addAll(newPaths)
      }


      trailqueue.addAll(newNeighbors)
      println("before: ${trailhead.paths}")
      if (newNeighbors.size > 1) {
        for (i in trailhead.paths.size - 1 downTo 0) {
          if (trailhead.paths[i].contains(point)) {
            repeat(newNeighbors.size - 1) {
              trailhead.paths.add(trailhead.paths[i].map { it }.toMutableList())
            }
          }
        }
      }
      println("new neighbors: $newNeighbors")
      println("after: ${trailhead.paths}")
      println("trailqueue: $trailqueue")
      trailhead.paths = trailhead.paths.toMutableSet().toMutableList()
      visited.add(point)

    }
  }

  fun findTrailheads(grid: List<List<Int>>): List<Trailhead> {
    var trailheads = mutableListOf<Trailhead>()
    for (r in grid.indices) {
      for (c in grid[0].indices) {
        if (grid[r][c] == 0) {
          trailheads.add(Trailhead(r, c))
        }
      }
    }

    trailheads.forEach { findPaths(grid, it) }
    return trailheads
  }

  fun part1(input: List<String>): Int {
    val grid = input.map { it.windowed(1).map { it.toInt() } }
    println(grid)
    val trailheads: List<Trailhead> = findTrailheads(grid)
    for (trailhead in trailheads) {
      println("trailhead $trailhead \nscore: ${trailhead.score()}")
    }
    return trailheads.sumOf { it.score() }
  }


  fun part2(input: List<String>): Int {
    val grid = input.map { it.windowed(1).map { it.toInt() } }
    println(grid)
    val trailheads: List<Trailhead> = findTrailheads(grid)
    for (trailhead in trailheads) {
      println("trailhead $trailhead \nscore: ${trailhead.score()}")
    }
    return trailheads.sumOf { it.distinctScore() }
  }


// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


// Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
  val input = readInput("Day10")
//  part1(input).println()
    part2(input).println()
}

