fun main() {
    // search horizontal for xmas and samx
    // transpose
    // do horizontal again
    // do diagonal

    fun horizontalCount(str: String) : Int {
        return str.windowed(4).count { it == "XMAS" || it == "SAMX" }
    }










    fun countMas(diags: List<String>) : Int {
        println(diags)
        if(diags.count { it == "MAS" || it == "SAM" } == 2) {
            return 1
        }
        return 0
    }

    fun getDiags(grid: List<String>) : List<String> {
        var diags = mutableListOf<String>()

        for(i in 0..grid.size) {
            var r = 0
            var c = 0
            var diag = ""
            while (r < grid.size - i && c < grid[0].length) {
                diag += grid[r+i][c]
                r++
                c++
            }
            diags.add(diag)
        }
        for(i in 0..grid.size) {
            var r = 0
            var c = 1
            var diag = ""
            while (r < grid.size && c < grid[0].length-i) {
                diag += grid[r][c+i]
                r++
                c++
            }
            diags.add(diag)
        }
        return diags
    }

    fun getOtherDiags(grid: List<String>) : List<String> {
        var diags = mutableListOf<String>()

        for(i in 0..grid.size) {
            var r = 1
            var c = grid[0].length-1
            var diag = ""
            while (r < grid.size - i && c >= 0) {
                diag += grid[r+i][c]
                r++
                c--
            }
            diags.add(diag)
        }
        for(i in 0..grid.size) {
            var r = 0
            var c = grid[0].length-1
            var diag = ""
            while (r < grid.size - i && c >= 0) {
                diag += grid[r][c-i]
                r++
                c--
            }
            diags.add(diag)
        }
        return diags
    }


    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { sum += horizontalCount(it) }
        input.transpose().forEach { sum += horizontalCount(it) }
        val diags = getDiags(input)
        val otherDiags = getOtherDiags(input)

        diags.forEach { sum += horizontalCount(it) }
        otherDiags.forEach { sum += horizontalCount(it) }

        return sum
    }

    fun hasNeighbors(grid: List<String>, r: Int, c: Int) : Boolean {
        return !(r+1 >= grid.size || r-1 < 0 || c + 1 >= grid[0].length || c - 1 < 0)
    }

    fun getNeighbors(grid: List<String>, r: Int, c: Int) : List<String> {
        var smallGrid = mutableListOf<String>()
        for(i in -1..1) {
            smallGrid.add(grid[r+i].substring(c-1, c+2))
        }
        return smallGrid
    }

    fun part2(input: List<String>): Int {
        var neighbors = mutableListOf<List<String>>()
        for(r in input.indices) {
            for(c in input[0].indices) {
                if(input[r][c] == 'A') {
                    if(hasNeighbors(input, r, c)) {
                        neighbors.add(getNeighbors(input, r, c))
                    }
                }
            }
        }
        println(neighbors)
        var sum = 0

        neighbors.forEach {
            var diags = mutableListOf<String>()
            diags.addAll(getDiags(it))
            diags.addAll(getOtherDiags(it))
            sum+= countMas(diags)
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day04")
//    part1(input).println()
    part2(input).println()
}
