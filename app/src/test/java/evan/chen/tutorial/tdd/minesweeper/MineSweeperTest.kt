package evan.chen.tutorial.tdd.minesweeper

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MineSweeperTest {

    lateinit var mineSweeper : MineSweeper

    @Before
    fun setup(){
        mineSweeper = MineSweeper()
    }

    @Test
    fun startGame() {
        val level = 9
        val cellCreator = CellCreator()
        cellCreator.level = level
        mineSweeper.startGame(cellCreator)
        val cells: List<Cell> = mineSweeper.cells
        Assert.assertEquals(81, cells.count())
    }
}