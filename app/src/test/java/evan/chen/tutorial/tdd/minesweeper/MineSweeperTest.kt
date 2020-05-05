package evan.chen.tutorial.tdd.minesweeper

import org.junit.Assert
import org.junit.Test

class MineSweeperTest {

    @Test
    fun startGame() {
        val level = 9
        val cellCreator = CellCreator()
        cellCreator.level = level
        val mineSweeper = MineSweeper()
        mineSweeper.startGame(cellCreator)
        val cells:List<Cell> = mineSweeper.cells
        Assert.assertEquals(81, cells.count())
    }
}