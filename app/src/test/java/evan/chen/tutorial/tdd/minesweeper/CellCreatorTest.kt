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
}