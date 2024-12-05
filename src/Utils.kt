import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> List<List<T>>.transpose(): List<List<T>> {
    if (isEmpty() || this[0].isEmpty()) return this

    val width = this[0].size

    return (0 until width).map { i ->
        this.map { it[i] }
    }
}

fun List<String>.transposeStringList() : List<String> {
    if (isEmpty() || this[0].isEmpty()) return this
    val transposed = mutableListOf<String>()
    for(c in 0 until this[0].length) {
        var col = ""
        for(r in 0 until this.size) {
            col += this[r][c]
        }
        transposed.add(col)
    }
    return transposed
}
