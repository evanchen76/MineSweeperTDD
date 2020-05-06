package evan.chen.tutorial.tdd.minesweeper

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MineSweeperTest {

    lateinit var mineSweeper: MineSweeper

    @Before
    fun setup() {
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
        val init = mutableListOf<String>()
        init.add("-")
        val cells = createCell(init)

        val creator = FakeCellCreator()
        creator.cells = cells
        mineSweeper.startGame(creator)

        val x = 0
        val y = 0
        mineSweeper.tap(x, y)

        val verify = mutableListOf<String>()
        verify.add(" ") //這裡的空格代表已被打開
        verifyDisplay(verify)
    }

    private fun verifyDisplay(verify: List<String>) {
        verify.forEachIndexed { yIndex, yList ->
            val lines = yList.split("|")
            lines.forEachIndexed { xIndex, value ->
                val findCell = mineSweeper.findCell(xIndex, yIndex)!!
                when (value) {
                    " " -> Assert.assertEquals("$xIndex, $yIndex",
                        Cell.Status.OPEN, findCell.status
                    )
                    "-" -> Assert.assertEquals("$xIndex, $yIndex",
                        Cell.Status.CLOSE, findCell.status
                    )
                }
            }
        }
    }

    private fun createCell(initSweeper: MutableList<String>): MutableList<Cell> {
        //將圖示字串轉為MutableList<Cell>
        val cells: MutableList<Cell> = mutableListOf()
        initSweeper.forEachIndexed { yIndex, yList ->
            val lines = yList.split("|")
            lines.forEachIndexed { xIndex, value ->
                val cell = Cell()
                cell.x = xIndex
                cell.y = yIndex
                cell.status = Cell.Status.CLOSE
                if (value == " ") {
                    cell.status = Cell.Status.OPEN
                }
                cells.add(cell)
            }
        }

        return cells
    }
}