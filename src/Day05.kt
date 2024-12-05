fun main() {

    // map int to list<int>
    fun isValidPage(ruleMap: HashMap<Int, MutableSet<Int>>, pageList: List<Int>) : Boolean {
        var valid = true
        for(i in pageList.indices) {
            val before = pageList.subList(0, i)
            val after = pageList.subList(i, pageList.size)
            if(ruleMap[pageList[i]] == null) {
                continue
            }
//            println("before $before after $after")
            valid = before.isEmpty() ||  before.none { ruleMap[pageList[i]]!!.contains(it) }
            if(!valid) return false
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val rules = input.subList(0, input.indexOf("")).map { it.split("|").map { it.toInt() } }
        val ruleMap = hashMapOf<Int, MutableSet<Int>>()
        rules.forEach { if(!ruleMap.containsKey(it[0])) {
            ruleMap[it[0]] = mutableSetOf(it[1])
        } else {
            ruleMap[it[0]]?.add(it[1])
        } }
        println(ruleMap)

        val pages = input.subList(input.indexOf("") + 1, input.size).map { it.split(",").map { it.toInt() } }
//        var sum = 0
//        for(page in pages) {
//            if(isValidPage(ruleMap, page)) {
//                sum += page[page.size/2]
//            }
//        }

        return pages.filter { isValidPage(ruleMap, it) }.sumOf { it[it.size/2] }


//        return sum
    }


    fun reorder(ruleMap: HashMap<Int, MutableSet<Int>>, pageList: List<Int>): List<Int> {
        var newPage = mutableListOf<Int>()
        for(i in pageList.indices) {
            var loc = i
            newPage.add(pageList[i])
            while(!isValidPage(ruleMap, newPage)) {
                val temp = newPage[loc-1]
                newPage[loc-1] = newPage[loc]
                newPage[loc] = temp
                loc--
            }
            println("pageList: $pageList\n   newPage $newPage")
        }
        return newPage
    }

    fun part2(input: List<String>): Int {
        val rules = input.subList(0, input.indexOf("")).map { it.split("|").map { it.toInt() } }
        val ruleMap = hashMapOf<Int, MutableSet<Int>>()
        rules.forEach { if(!ruleMap.containsKey(it[0])) {
            ruleMap[it[0]] = mutableSetOf(it[1])
        } else {
            ruleMap[it[0]]?.add(it[1])
        } }
        println(ruleMap)

        val pages = input.subList(input.indexOf("") + 1, input.size).map { it.split(",").map { it.toInt() } }
//        var sum = 0
//        for(page in pages) {
//            if(!isValidPage(ruleMap, page)) {
//                var newPage = reorder(ruleMap, page)
//                sum += newPage[newPage.size/2]
//            }
//        }

        return pages.filter { !isValidPage(ruleMap, it) }.map { reorder(ruleMap, it) }.sumOf { it[it.size/2] }

//        return sum
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day01")
    val input = readInput("Day05")
//    part1(input).println()
    part2(input).println()
}
