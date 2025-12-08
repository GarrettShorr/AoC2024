fun main() {
        fun getNeighbors(r: Int, c: Int, grid: List<List<String>>, reach: Int) : Set<Triple<String, Int, Int>> {
            val neighbors = mutableSetOf<Triple<String, Int, Int>>()
            for(dr in -reach..reach) {
                for(dc in -reach..reach) {
                    if(dr + r >= 0 && dr + r < grid.size && dc + c >= 0 && dc + c < grid[0].size && !(dr == 0 && dc == 0)) {
                        neighbors.add(Triple(grid[r + dr][c + dc], r + dr, c + dc))
                    }
                }
            }
            return neighbors
        }

        fun part1(input: List<String>): Int {
            val neighbors = mutableSetOf<Triple<String, Int, Int>>()
            val grid = input.map { it.windowed(1) }
            var count = 0
            for (r in grid.indices) {
                for (c in grid[0].indices) {
                    if(grid[r][c] == "@" && getNeighbors(r, c, grid, 1).count { it.first == "@" } < 4) {
                        count++
                        println("fewer than 4 at $r $c")
                    }
                }
            }
//            println(neighbors.filter { it.first == "@" })
//            return neighbors.toList().count { it.first == "@" }
            return count
        }



        fun part2(input: List<String>): Int {
            val neighbors = mutableSetOf<Triple<String, Int, Int>>()
            val grid = input.map { it.windowed(1).toMutableList() }
            var count = 0
            do {
                println("removing: ${neighbors.size}")
                neighbors.forEach {
                    grid[it.second][it.third] = "."
                }
                grid.forEach { println(it.joinToString("")) }
                println("______________")
                neighbors.clear()
                for (r in grid.indices) {
                    for (c in grid[0].indices) {
                        if (grid[r][c] == "@" && getNeighbors(r, c, grid, 1).count { it.first == "@" } < 4) {
                            count++
                            neighbors.add(Triple("@", r, c))
                            println("fewer than 4 at $r $c $count")
                        }
                    }
                }
            } while(neighbors.isNotEmpty())
//            println(neighbors.filter { it.first == "@" })
//            return neighbors.toList().count { it.first == "@" }
            return count
        }



        // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


        // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
        val input = readInput("Day04_2025")
        part2(input).println()
//    part2(input).println()
}
