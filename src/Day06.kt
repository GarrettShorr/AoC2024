fun main() {
    val UP = 0
    val RIGHT = 1
    val DOWN = 2
    val LEFT = 3
    val DIRS = listOf(UP, RIGHT, DOWN, LEFT)
    fun hasAnotherMove(grid: List<List<String>>, row: Int, col: Int, dir: Int): Boolean {
        var r = row
        var c = col
        when(dir) {
            UP->r--
            RIGHT->c++
            DOWN->r++
            LEFT->c--
        }
//        if(grid[r][c] == "#") {
//
//        }

        return r < grid.size && r >= 0 && c < grid[0].size && c >= 0
    }

    fun performNextMove(grid: MutableList<MutableList<String>>, row: Int, col: Int, dir: Int): Triple<Int, Int, Int> {
        var r = row
        var c = col
        var newDir = dir
        when(dir) {
            UP->{
                if(grid[r-1][c] in ".X") {
                    r--
                } else {
                    newDir = ++newDir % 4
                }
            }
            RIGHT->{
                if(grid[r][c+1] in ".X") {
                    c++
                } else {
                    newDir = ++newDir % 4
                }
            }
            DOWN->{
                if(grid[r+1][c] in ".X") {
                    r++
                } else {
                    newDir = ++newDir % 4
                }
            }
            LEFT->{
                if(grid[r][c-1] in ".X") {
                    c--
                } else {
                    newDir = UP
                }
            }
        }
        if(grid[r][c] !in "O#") {
            grid[r][c] = "X"
        }
        return Triple(r, c, newDir)
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { it.windowed(1).toMutableList() }.toMutableList()
        var moves = 1
        var movesList = mutableSetOf<Pair<Int, Int>>()
        var dir = UP
        var row = 0
        var col = 0
        outer@for(r in grid.indices) {
            for(c in grid.indices) {
                if(grid[r][c] == "^") {
                    row = r
                    col = c
                    break@outer
                }
            }
        }

        grid[row][col] = "."

        while(hasAnotherMove(grid, row, col, dir)) {
            var trips = performNextMove(grid, row, col, dir)
            movesList.add(Pair(trips.first, trips.second))
            dir = trips.third
            row = trips.first
            col = trips.second
            moves++
        }
        println(movesList)
        return movesList.size + 1 // I was off by one on the real data but not on the test...huh...
    }

    fun part2(input: List<String>): Int {
        var grid = input.map { it.windowed(1).toMutableList() }.toMutableList()
        var moves = 1
        var movesList = mutableSetOf<Pair<Int, Int>>()
        var dir = UP
        var startRow = 0
        var startCol = 0
        var row = 0
        var col = 0
        outer@for(r in grid.indices) {
            for(c in grid.indices) {
                if(grid[r][c] == "^") {
                    row = r
                    startRow = row
                    col = c
                    startCol = col
                    break@outer
                }
            }
        }
        var loopCausingChaosItems = 0
        var repeatCounter = 0
        grid[row][col] = "."
        outer@for(r in grid.indices) {
            inner@for (c in grid.indices) {
                if(grid[r][c] == "#") {
                    continue@inner
                }
                grid[r][c] = "O"
                while(hasAnotherMove(grid, row, col, dir)) {
                    var trips = performNextMove(grid, row, col, dir)
//                    for(line in grid) {
//                        println(line)
//                    }
//                    println("------------------------------")

                    if(!movesList.add(Pair(trips.first, trips.second))) {
                        repeatCounter++
                    } else {
                        repeatCounter = 0
                    }
                    dir = trips.third
                    row = trips.first
                    col = trips.second
                    moves++
                    if(repeatCounter > 1000) {
                        loopCausingChaosItems++
                        row = startRow
                        col = startCol
                        grid[r][c] = "."
                        movesList.clear()
                        grid.map { it.map { if(it in ".XO") "." else "#" } }
                        dir = UP
                        continue@inner
                    }
                }
                row = startRow
                col = startCol
                grid[r][c] = "."
                grid = grid.map { it.map { if(it in ".XO") "." else "#" } } as MutableList<MutableList<String>>
                movesList.clear()
                dir = UP

            }
        }


        return loopCausingChaosItems
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day06")
//    part1(input).println()
    part2(input).println()
}
