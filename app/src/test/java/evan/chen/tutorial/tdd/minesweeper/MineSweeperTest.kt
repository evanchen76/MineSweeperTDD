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

    @Test
    fun tapCellShouldOpen() {
        //點擊會被打開
        //產生1*1個Cell
        val cells = mutableListOf<Cell>(
            Cell().apply { x=0; y=0 }
        )

        val creator = FakeCellCreator()
        creator.cells = cells
        mineSweeper.startGame(creator)

        val x = 0
        val y = 0
        mineSweeper.tap(x, y)
        val cell = mineSweeper.cells.find { it.x == x && it.y == y }
        Assert.assertEquals(Cell.Status.OPEN, cell?.status)
    }
}