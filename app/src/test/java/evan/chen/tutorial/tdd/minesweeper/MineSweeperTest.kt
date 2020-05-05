package evan.chen.tutorial.tdd.minesweeper

import org.junit.Assert
import org.junit.Test

class MineSweeperTest {

    @Test
    fun startGame() {
        val level = 9
        val mineSweeper = MineSweeper()
        mineSweeper.startGame(level)
        val cells:List<Cell> = mineSweeper.cells
        Assert.assertEquals(81, cells.count())
    }
}