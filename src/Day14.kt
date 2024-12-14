import java.awt.Point

fun main() {

    val WIDTH = 101
    val HEIGHT = 103

    fun findLocationAfter(gridDimensions: Pair<Int, Int>, loc: Point, velocity: Point, steps: Int) : Point {
        var x = (loc.x + velocity.x * steps) % gridDimensions.first
        if(x < 0) { x += gridDimensions.first }
        var y = (loc.y + velocity.y * steps)  % gridDimensions.second
        if(y < 0) { y += gridDimensions.second }
        return Point(x, y)
    }

    fun part1(input: List<String>): Int {
        var guardInitialLocsAndVelocities = mutableListOf<Pair<Point, Point>>()
        for(line in input) {
            var attrs = line.split(" ")
            var locs = attrs[0].substring(2).split(",")
            var loc = Point(locs[0].toInt(), locs[1].toInt())
            var vels = attrs[1].substring(2).split(",")
            var vel = Point(vels[0].toInt(), vels[1].toInt())
            guardInitialLocsAndVelocities.add(Pair(loc, vel))
        }
        var finalLocations = mutableListOf<Point>()
        guardInitialLocsAndVelocities.forEach {
            finalLocations.add(findLocationAfter(Pair(WIDTH,HEIGHT), it.first, it.second, 100))
        }
        var topLeft = finalLocations.count { it.x < WIDTH/2 && it. y < HEIGHT/2 }
        var topRight = finalLocations.count { it.x > WIDTH/2 && it. y < HEIGHT/2 }
        var botRight = finalLocations.count { it.x > WIDTH/2 && it. y > HEIGHT/2 }
        var botLeft = finalLocations.count { it.x < WIDTH/2 && it. y > HEIGHT/2 }

        return topLeft * topRight * botLeft * botRight

   }





    fun part2(input: List<String>): Int {
        var guardInitialLocsAndVelocities = mutableListOf<Pair<Point, Point>>()
        for(line in input) {
            var attrs = line.split(" ")
            var locs = attrs[0].substring(2).split(",")
            var loc = Point(locs[0].toInt(), locs[1].toInt())
            var vels = attrs[1].substring(2).split(",")
            var vel = Point(vels[0].toInt(), vels[1].toInt())
            guardInitialLocsAndVelocities.add(Pair(loc, vel))
        }
        var finalLocations = mutableListOf<Point>()
        var step = 0
        repeat(1000000) {
            guardInitialLocsAndVelocities.forEach {
                finalLocations.add(findLocationAfter(Pair(WIDTH, HEIGHT), it.first, it.second, 1))
            }
            step++
//            if(finalLocations.size == finalLocations.toMutableSet().size) {
            if(step in 6620..6650) {
                println("$step SECONDS LATER...")

                for (r in 0..HEIGHT) {
                    for (c in 0..WIDTH) {
                        if (Point(c, r) in finalLocations) {
                            print("▮")
                        } else {
                            print(" ")
                        }
                    }
                    println()
                }
            }
            guardInitialLocsAndVelocities = guardInitialLocsAndVelocities.mapIndexed { index, pair -> Pair(finalLocations[index], pair.second) }.toMutableList()
            finalLocations.clear()
        }
        return 0
    }


    val input = readInput("Day14")
//    part1(input).println()
    part2(input).println()
}

