import java.awt.Point

fun main() {
    val HEIGHT = 50

    fun executeMove(grid: MutableList<MutableList<String>>, robot: Point, direction: Char) : Point {
        var movement = Point(0, 0)
        when(direction) {
            '<' -> {
                movement.x = -1
            }
            'v' -> {
                movement.y = 1
            }
            '>' -> {
                movement.x = 1
            }
            '^' -> {
                movement.y = -1
            }
        }
        println("movement $movement")
        var nextLoc = Point(robot.x + movement.x, robot.y + movement.y)
        println("robot: $robot nextLoc: $nextLoc occupied: ${grid[nextLoc.y][nextLoc.x]}")
        when(grid[nextLoc.y][nextLoc.x]) {
            "." -> {
                grid[robot.y][robot.x] = "."
                robot.x = nextLoc.x
                robot.y = nextLoc.y
                grid[nextLoc.y][nextLoc.x] = "@"            }
            "#" -> {
                // do nothing
            }
            "O" -> {
                var r = nextLoc.y
                var c = nextLoc.x
                var consecutiveOs = 0
                while(grid[r][c] == "O") {
                    consecutiveOs++
                    r += movement.y
                    c += movement.x
                }
                println("consec Os: $consecutiveOs ($c, $r)")
                if(grid[r][c] != "#") {
                    for(i in 0..consecutiveOs) {
                        grid[r][c] = "O"
                        r -= movement.y
                        c -= movement.x
                    }
                    grid[robot.y][robot.x] = "."
                    robot.x = nextLoc.x
                    robot.y = nextLoc.y
                    grid[nextLoc.y][nextLoc.x] = "@"
                }

            }
        }
        return robot
    }

    fun executeWideMove(grid: MutableList<MutableList<String>>, robot: Point, direction: Char) : Point {
        var movement = Point(0, 0)
        when(direction) {
            '<' -> {
                movement.x = -1
            }
            'v' -> {
                movement.y = 1
            }
            '>' -> {
                movement.x = 1
            }
            '^' -> {
                movement.y = -1
            }
        }
        println("movement $movement")
        var nextLoc = Point(robot.x + movement.x, robot.y + movement.y)
        println("robot: $robot nextLoc: $nextLoc occupied: ${grid[nextLoc.y][nextLoc.x]}")
        var canMove = true
        when(grid[nextLoc.y][nextLoc.x]) {
            "." -> {
                grid[robot.y][robot.x] = "."
                robot.x = nextLoc.x
                robot.y = nextLoc.y
                grid[nextLoc.y][nextLoc.x] = "@"            }
            "#" -> {
                // do nothing
            }
            "]", "[" -> {
                if(movement.y == 0) {
                    var r = nextLoc.y
                    var c = nextLoc.x
                    if (grid[r][c] in "[]") {
                        c = nextLoc.x
                    }
                    var consecutiveOs = 0
                    while (grid[r][c] in "[]") {
                        consecutiveOs++
                        r += movement.y
                        c += movement.x
                    }
                    println("consec Os: $consecutiveOs ($c, $r)")
                    if (grid[r][c] != "#") {
                        for (i in 0..consecutiveOs) {
                            grid[r][c] = grid[r][c-movement.x]
                            c -= movement.x
                            for(row in grid) {
                                println(row.joinToString(""))
                            }
                        }
                        grid[robot.y][robot.x] = "."
                        robot.x = nextLoc.x
                        grid[nextLoc.y][nextLoc.x] = "@"
                    }
                } else {
                    var r = nextLoc.y
                    var c = nextLoc.x
                    if (grid[r][c] in "[]") {
                        r = nextLoc.y
                    }
                    var consecutiveOs = 0
                    var rowsOfPointsToMove = mutableListOf<MutableList<Point>>()
                    var pointsToMove = mutableListOf<Point>()
                    pointsToMove.add(nextLoc)
                    outer@while(pointsToMove.any { grid[it.y][it.x] in "[]" }) {
                        consecutiveOs++
                        r += movement.y
                        var newPointsToMove = mutableSetOf<Point>()
                        for(i in pointsToMove.indices.reversed()) {
                            var it = pointsToMove[i]
                            when(grid[it.y][it.x]) {
                                "]" ->pointsToMove.add(Point(it.x-1, it.y))
                                "[" ->pointsToMove.add(Point(it.x+1, it.y))
                            }
                        }
                        println("new points to move: $pointsToMove")

                        rowsOfPointsToMove.add(pointsToMove)
                        for(it in pointsToMove) {
                                when (grid[it.y + movement.y][it.x]) {
                                    "#" -> canMove = false
                                    "]" -> {
                                        newPointsToMove.add(Point(it.x, it.y + movement.y))
                                        newPointsToMove.add(Point( it.x - 1, it.y + movement.y))
                                    }

                                    "[" -> {
                                        newPointsToMove.add(Point(it.x, it.y + movement.y))
                                        newPointsToMove.add(Point(it.x + 1, it.y + movement.y))
                                    }
                                }
                            }

                        pointsToMove = newPointsToMove.toMutableList()
                    }

                    println(rowsOfPointsToMove)
                    if(canMove) {
                        rowsOfPointsToMove.reversed().forEach {
                            it.toMutableSet().forEach {
                                if(it.y+movement.y >= 0 && it.y+movement.y < grid.size) {
                                    grid[it.y + movement.y][it.x] = grid[it.y][it.x]
                                    grid[it.y][it.x] = "."
                                }
                            }
                        }
                        grid[robot.y][robot.x] = "."
                        robot.x = nextLoc.x
                        robot.y = nextLoc.y
                        grid[nextLoc.y][nextLoc.x] = "@"
                    }
                }
            }
        }
        return robot
    }

    fun calculateGps(grid: MutableList<MutableList<String>>): Int {
        var sum = 0
        for(r in grid.indices) {
            for(c in grid[r].indices) {
                if(grid[r][c] == "O" || grid[r][c] == "[") {
                    sum += r * 100 + c-1
                }
            }
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        var grid = input.subList(0,HEIGHT).map { it.windowed(1).toMutableList() }.toMutableList()
        val WIDTH = grid[0].size
        var directions = input.subList(HEIGHT+1, input.size)
        var robot = Point(0,0)
        outer@for(r in grid.indices) {
            for(c in grid[0].indices) {
                if(grid[r][c]=="@") {
                    robot.x = c
                    robot.y = r
                    break@outer
                }
            }
        }
        for(row in directions) {
            for(direction in row) {
                robot = executeMove(grid, robot, direction)
                println("after move robot: $robot")
                for(row in grid) {
                    println(row.joinToString(""))
                }
                println()
            }
        }
        return calculateGps(grid)
   }




    fun part2(input: List<String>): Int {
        var grid = input.subList(0,HEIGHT).map { it.windowed(1).toMutableList() }.toMutableList()
        val WIDTH = grid[0].size * 2
        var directions = input.subList(HEIGHT+1, input.size)
        var robot = Point(0,0)
        for(r in grid.indices) {
            var newRow = ""
            for(c in grid[r].indices) {
                when(grid[r][c]) {
                    "#" -> newRow += "##"
                    "O" -> newRow += "[]"
                    "@" -> newRow += "@."
                    "." -> newRow += ".."
                }
            }
            grid[r] = newRow.split("").toMutableList()
        }
        for(row in grid) {
            println(row.joinToString(""))
        }

        outer@for(r in grid.indices) {
            for(c in grid[r].indices) {
                if(grid[r][c]=="@") {
                    robot.x = c
                    robot.y = r
                    println("Found the robot at ($c, $r) ${grid[r][c]}")
                    break@outer
                }
            }
        }
        for(row in directions) {
            for(direction in row) {
                robot = executeWideMove(grid, robot, direction)
                println("after move robot: $robot")
                for(row in grid) {
                    println(row.joinToString(""))
                }
                println()
            }
        }
        return calculateGps(grid)
    }



    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day15")
//    part1(input).println()
    part2(input).println()
}

