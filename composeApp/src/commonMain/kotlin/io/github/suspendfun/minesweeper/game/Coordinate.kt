package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable
import kotlin.random.Random

@Immutable
data class Coordinate(val col: Int, val row: Int)

/**
 * Fisher-Yates shuffle.
 */
fun List<Coordinate>.shuffle(random: Random = Random): List<Coordinate> {
    val result = toMutableList()
    for (i in result.lastIndex downTo 1) {
        val j = random.nextInt(i + 1)
        result[j] = result[i].also { result[i] = result[j] }
    }
    return result
}
