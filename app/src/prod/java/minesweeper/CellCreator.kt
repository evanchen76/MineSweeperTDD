package evan.chen.tutorial.tdd.minesweeper

import java.util.*

class CellCreator : ICellCreator {

    var level: Int = 0

    override fun createCell(): MutableList<Cell> {
        val returnCells = mutableListOf<Cell>()
        val mineIndexes = createRandomIndexes(level)
        (0 until level).forEach { x ->
            (0 until level).forEach { y ->
                val number = x * level + y
                val cell = Cell()
                cell.x = x
                cell.y = y
                cell.status = Cell.Status.CLOSE
                cell.isMine = false
                if (mineIndexes.filter { it == number }.count() != 0) {
                    cell.isMine = true
                }
                returnCells.add(cell)
            }
        }
        return returnCells
    }

    private fun createRandomIndexes(cellSize: Int): MutableList<Int> {
        val mineIndexes: MutableList<Int> = mutableListOf()

        val random = Random()
        //15 % 機率為地雷
        while (mineIndexes.count() < cellSize * cellSize * 0.15) {
            val nextInt = random.nextInt(cellSize * cellSize - 1)
            if (mineIndexes.none { it == nextInt }) {
                mineIndexes.add(nextInt)
            }
        }
        return mineIndexes
    }
}

