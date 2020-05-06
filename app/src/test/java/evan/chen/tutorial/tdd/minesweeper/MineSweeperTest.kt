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

    @Test
    fun tapNumberShouldDisplay() {
        //點擊的Cell如果不是地雷且旁邊有地雷，顯示地雷數。
        //產生5*5的方格，並在(0,0)、(1,2)、(2,4)放置地雷。
        val init = mutableListOf<String>()
        init.add("*|-|-|-|-")
        init.add("-|-|-|-|-")
        init.add("-|*|-|-|-")
        init.add("-|-|-|-|-")
        init.add("-|-|*|-|-")

        val cells = createCell(init)
        val creator = FakeCellCreator().apply { this.cells = cells }
        mineSweeper.startGame(creator)
        mineSweeper.tap(0, 1)

        //驗證結果，座標0,1應被打開且數字為2。
        val verify = mutableListOf<String>()
        verify.add("*|-|-|-|-")
        verify.add("2|-|-|-|-")
        verify.add("-|*|-|-|-")
        verify.add("-|-|-|-|-")
        verify.add("-|-|*|-|-")
        verifyDisplay(verify)
    }

    @Test
    fun tapCellShouldDisplayNextMines() {
        //點擊的Cell如果旁邊都沒有地雷，顯示旁邊的地雷數。
        val init = mutableListOf<String>()
        init.add("*|-|-|-|-")
        init.add("-|-|-|-|-")
        init.add("-|*|-|-|-")
        init.add("-|-|-|-|-")
        init.add("-|-|*|-|-")

        val cells = createCell(init)
        val creator = FakeCellCreator().apply { this.cells = cells }

        mineSweeper.startGame(creator)
        mineSweeper.tap(0, 4)

        val verify = mutableListOf<String>()
        verify.add("*|-|-|-|-")
        verify.add("-|-|-|-|-")
        verify.add("-|*|-|-|-")
        verify.add("1|2|-|-|-")
        verify.add(" |1|*|-|-")

        verifyDisplay(verify)
    }

    @Test
    fun tapIfNextIs0ThenOpen() {
        //旁邊如果數字是0，就再點擊打開
        val init = mutableListOf<String>()
        init.add("*|-|-|-|-")
        init.add("-|-|-|-|-")
        init.add("-|*|-|-|-")
        init.add("-|-|-|-|-")
        init.add("-|-|*|-|-")

        val cells = createCell(init)
        val creator = FakeCellCreator().apply { this.cells = cells }

        mineSweeper.startGame(creator)
        mineSweeper.tap(3, 2)

        val verify = mutableListOf<String>()
        verify.add("*|1| | | ")
        verify.add("-|2|1| | ")
        verify.add("-|*|1| | ")
        verify.add("-|-|2|1| ")
        verify.add("-|-|*|1| ")

        verifyDisplay(verify)
    }

    private fun verifyDisplay(verify: List<String>) {
        verify.forEachIndexed { yIndex, yList ->
            val lines = yList.split("|")
            lines.forEachIndexed { xIndex, value ->
                val findCell = mineSweeper.findCell(xIndex, yIndex)!!
                when (value) {
                    "*" -> Assert.assertTrue(findCell.isMine)
                    " " -> Assert.assertEquals("$xIndex, $yIndex",
                        Cell.Status.OPEN, findCell.status
                    )
                    "-" -> Assert.assertEquals("$xIndex, $yIndex",
                        Cell.Status.CLOSE, findCell.status
                    )
                    else -> {
                        //顯示Cell的數字且狀態為打開。nextMines表示該Cell附近的地雷數。
                        Assert.assertEquals("$xIndex, $yIndex",
                            Cell.Status.OPEN, findCell.status
                        )
                        Assert.assertEquals("$xIndex, $yIndex",
                            value, mineSweeper.findCell(xIndex, yIndex)?.nextMines.toString()
                        )
                    }
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
                cell.isMine = false
                if (value == "*") {
                    cell.isMine = true
                }
                cells.add(cell)
            }
        }

        return cells
    }
}