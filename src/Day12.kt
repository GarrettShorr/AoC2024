import kotlin.math.abs

data class Plot(val row: Int, val col: Int, val plant: String) {

}

fun main() {
    fun findSameNeighbors(grid: List<List<String>>, current: Plot): List<Plot> {
        val neighbors = mutableListOf<Plot>()
        val r = current.row
        val c = current.col
        for(i in -1..1) {
            for(j in -1..1) {
                if(abs(i) != abs(j) && r+i >= 0 && r + i < grid.size && c + j >= 0 && c + j < grid[0].size) {
                    if(grid[r+i][c+j] == current.plant) {
                        neighbors.add(Plot(r+i, c+j, current.plant))
                    }
                }
            }
        }
        return  neighbors
    }

    fun getRegion(grid: List<List<String>>, r: Int, c: Int, searched: MutableSet<MutableList<Plot>>): MutableList<Plot> {
        val start = Plot(r, c, grid[r][c])
        searched.forEach { if(start in it) return mutableListOf() }

        var queue = ArrayDeque<Plot>()
        var visited = mutableListOf<Plot>()
        var contiguousPlot = mutableListOf<Plot>()
        queue.add(start)
        while(queue.isNotEmpty()) {
            var current = queue.removeFirst()
            visited.add(current)
            var nextPlots : List<Plot> = findSameNeighbors(grid, current)
            nextPlots.forEach { if(it !in visited && it !in queue) queue.add(it) }
            contiguousPlot.add(current)
            println(queue)
        }
        println("Finished region: ${contiguousPlot[0].plant} at ${contiguousPlot[0].row}, ${contiguousPlot[0].col}")
        return contiguousPlot.toMutableSet().toMutableList()
    }

    fun calculatePerimeter(grid: List<List<String>>, contiguousPlot: MutableList<Plot>): Int {
        println("Plant ${contiguousPlot[0].plant} at r: ${contiguousPlot[0].row} c: ${contiguousPlot[0].col} perimeter ${contiguousPlot.sumOf { 4 - findSameNeighbors(grid, it).size}}")
        return contiguousPlot.sumOf { 4 - findSameNeighbors(grid, it).size }
    }

    fun calculateDiscountPerimeter(grid: List<List<String>>, contiguousPlot: MutableList<Plot>): Int {
        var p = contiguousPlot[0].plant
        contiguousPlot.sortBy { it.col }
        contiguousPlot.sortBy { it.row }
        var perimeter = contiguousPlot.filterNot { findSameNeighbors(grid, it).size == 4 }
        var discount = 0

        var uncheckedHorizontalPlots = contiguousPlot.map { it }.toMutableList()
        var uncheckedVerticalPlots = contiguousPlot.map { it }.toMutableList()

        // above

        for(plot in contiguousPlot) {
            if(plot !in uncheckedHorizontalPlots) {
                continue
            }
            var horizontal = contiguousPlot.filter { it.row == plot.row }.sortedBy { it.col }
            var distinctLines = mutableListOf(mutableListOf<Plot>())
            var horizontalNum = 0
            // above
            var current = plot
            var r = horizontal[0].row
            var c = horizontal[0].col
            distinctLines[horizontalNum].add(current)
            if(r > 0 && grid[r-1][c] == current.plant) {
                horizontalNum++
                distinctLines.add(mutableListOf())
            }
            for(i in 1..< horizontal.size) {
                var oldC = c
                c = horizontal[i].col

                if(r > 0 && grid[r-1][oldC] == current.plant || abs(horizontal[i].col - oldC) > 1) {
                    distinctLines.add(mutableListOf(horizontal[i]))
                    horizontalNum++
                }
                else if(c == horizontal[i].col && (r > 0 && grid[r-1][c] != current.plant || r==0)) {
                    distinctLines[horizontalNum].add(horizontal[i])
                } else {
                    distinctLines.add(mutableListOf(horizontal[i]))
                    horizontalNum++
                }
                uncheckedHorizontalPlots.remove(horizontal[i])
            }
            println("above $distinctLines savings: ${distinctLines.filterNot{ it.isEmpty() }.sumOf { it.toMutableSet().size - 1 }}")
            discount += distinctLines.filterNot{ it.isEmpty() }.sumOf { it.toMutableSet().size - 1 }
        }

        // below
        uncheckedHorizontalPlots = contiguousPlot.map { it }.toMutableList()

        for(plot in contiguousPlot) {
            if(plot !in uncheckedHorizontalPlots) {
                continue
            }
            var horizontal = contiguousPlot.filter { it.row == plot.row }.sortedBy { it.col }
            var distinctLines = mutableListOf(mutableListOf<Plot>())
            var horizontalNum = 0

            var current = plot
            var r = horizontal[0].row
            var c = horizontal[0].col
            distinctLines[horizontalNum].add(current)
            if(r < grid.size - 1 && grid[r+1][c] == current.plant) {
                horizontalNum++
                distinctLines.add(mutableListOf())
            }
            for(i in 1..< horizontal.size) {
                var oldC = c
                c = horizontal[i].col
                if(r < grid.size - 1 && grid[r+1][oldC] == current.plant || abs(horizontal[i].col - oldC) > 1) {
                    distinctLines.add(mutableListOf(horizontal[i]))
                    horizontalNum++
                }
                else if(c == horizontal[i].col && (r < grid.size - 1 && grid[r+1][c] != current.plant || r==grid.size-1)) {
                    distinctLines[horizontalNum].add(horizontal[i])
                } else {
                    distinctLines.add(mutableListOf(horizontal[i]))
                    horizontalNum++
                }
                uncheckedHorizontalPlots.remove(horizontal[i])
            }
            println("below $distinctLines savings: ${distinctLines.filterNot{ it.isEmpty() }.sumOf { it.toMutableSet().size - 1 }}")
            discount += distinctLines.filterNot{ it.isEmpty() }.sumOf { it.toMutableSet().size - 1 }
        }

        // left
        for(plot in contiguousPlot) {
            if(plot !in uncheckedVerticalPlots) {
                continue
            }
            var vertical = contiguousPlot.filter { it.col == plot.col }.sortedBy { it.row }
            var distinctLines = mutableListOf(mutableListOf<Plot>())
            var verticalNum = 0

            var current = plot
            var r = vertical[0].row
            var c = vertical[0].col
            distinctLines[verticalNum].add(current)
            if(c > 0 && grid[r][c-1] == current.plant) {
                verticalNum++
                distinctLines.add(mutableListOf())
            }
            for(i in 1..< vertical.size) {
                var oldR = r
                r = vertical[i].row
                if(c > 0 && grid[oldR][c-1] == current.plant || abs(vertical[i].row - oldR) > 1) {
                    distinctLines.add(mutableListOf(vertical[i]))
                    verticalNum++
                }
                else if(r == vertical[i].row && (c > 0 && grid[r][c-1] != current.plant || c==0)) {
                    distinctLines[verticalNum].add(vertical[i])
                } else {
                    distinctLines.add(mutableListOf(vertical[i]))
                    verticalNum++
                }
                uncheckedVerticalPlots.remove(vertical[i])
            }
            println("left $distinctLines savings: ${distinctLines.filterNot{ it.isEmpty() }.sumOf { it.toMutableSet().size - 1 }}")
            discount += distinctLines.filterNot{ it.isEmpty() }.sumOf { it.toMutableSet().size - 1 }
        }

        uncheckedVerticalPlots = contiguousPlot.map { it }.toMutableList()

        // right
        for(plot in contiguousPlot) {
            if(plot !in uncheckedVerticalPlots) {
                continue
            }
            var vertical = contiguousPlot.filter { it.col == plot.col }.sortedBy { it.row }
            var distinctLines = mutableListOf(mutableListOf<Plot>())
            var verticalNum = 0

            var current = plot
            var r = vertical[0].row
            var c = vertical[0].col
            distinctLines[verticalNum].add(current)
            if(c < grid[0].size -1 && grid[r][c+1] == current.plant) {
                verticalNum++
                distinctLines.add(mutableListOf())
            }
            for(i in 1..< vertical.size) {
                var oldR = r
                r = vertical[i].row
                if(c < grid[0].size -1 && grid[oldR][c+1] == current.plant || abs(vertical[i].row - oldR) > 1) {
                    distinctLines.add(mutableListOf(vertical[i]))
                    verticalNum++
                }
                else if(r == vertical[i].row && (c < grid[0].size -1 && grid[r][c+1] != current.plant || c == grid[0].size -1)) {
                    distinctLines[verticalNum].add(vertical[i])
                } else {
                    distinctLines.add(mutableListOf(vertical[i]))
                    verticalNum++
                }
                uncheckedVerticalPlots.remove(vertical[i])
            }
            println("right $distinctLines savings: ${distinctLines.filterNot{ it.isEmpty() }.sumOf { it.toMutableSet().size - 1 }}")
            discount += distinctLines.filterNot{ it.isEmpty() }.sumOf { it.toMutableSet().size - 1 }
        }

        println("discount for ${p}: ${discount} with perimeter ${contiguousPlot.sumOf { 4 - findSameNeighbors(grid, it).size }}")
        return contiguousPlot.sumOf { 4 - findSameNeighbors(grid, it).size } - discount
    }


    fun part1(input: List<String>): Int {
        var grid = input.map { it.windowed(1) }
        var contiguousPlots = mutableSetOf<MutableList<Plot>>()
        for(r in grid.indices) {
            for(c in grid[0].indices) {
                if(contiguousPlots.any { it.contains(Plot(r, c, grid[r][c])) }) {
                    continue
                }
                contiguousPlots.add(getRegion(grid, r, c, contiguousPlots))
            }
            println("finished row: $r")
        }
        println(contiguousPlots.filter { it.isNotEmpty() })
        var cost = 0
        contiguousPlots = contiguousPlots.filterNot { it.isEmpty() }.toMutableSet()
        contiguousPlots.forEach {
            cost += it.size * calculatePerimeter(grid, it)
            println("Area for ${it[0].plant}: ${it.size}")
        }


        return cost
    }




    fun part2(input: List<String>): Int {
        var grid = input.map { it.windowed(1) }
        var contiguousPlots = mutableSetOf<MutableList<Plot>>()
        for(r in grid.indices) {
            for(c in grid[0].indices) {
                if(contiguousPlots.any { it.contains(Plot(r, c, grid[r][c])) }) {
                    continue
                }
                contiguousPlots.add(getRegion(grid, r, c, contiguousPlots))
            }
            println("finished row: $r")
        }
        println(contiguousPlots.filter { it.isNotEmpty() })
        var cost = 0
        contiguousPlots = contiguousPlots.filterNot { it.isEmpty() }.toMutableSet()
        contiguousPlots.forEach {
            println("Area for ${it[0].plant}: ${it.size}")

            var currCost = calculateDiscountPerimeter(grid, it)
            println("Cost for  ${it[0].plant} $currCost")
            cost += it.size * currCost
        }


        return cost
    }




    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day12")
//    part1(input).println()
    part2(input).println()
}

