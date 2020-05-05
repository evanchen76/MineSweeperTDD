package evan.chen.tutorial.tdd.minesweeper

import org.junit.Assert
import org.junit.Test

class CellCreatorTest {

    @Test
    fun testCreateCell() {
        //傳入難度等級9，產生9*9=81的方格
        val cellCreator = CellCreator()
        cellCreator.level = 9

        val createCell = cellCreator.createCell()
        val cellCount = createCell.count()
        Assert.assertEquals(81, cellCount)
    }

    @Test
    fun testCreateCellShouldBeClose() {
        //所有的狀態應為關閉的
        val createCell = createLevelCell(9)
        val cellCount = createCell.filter { it.status == Cell.Status.CLOSE }.count()
        Assert.assertEquals(81, cellCount)
    }

    @Test
    fun testCreateGameShouldHave15PercentMine() {
        //測試地雷應有15%
        val createCell = createLevelCell(9)
        val mineCount = createCell.filter { it.isMine }.count()
        Assert.assertEquals(13, mineCount)
    }

    private fun createLevelCell(level: Int): MutableList<Cell> {
        val cellCreator = CellCreator()
        cellCreator.level = level
        return cellCreator.createCell()

    }
}