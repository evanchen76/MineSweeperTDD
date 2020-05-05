package evan.chen.tutorial.tdd.minesweeper

class CellCreator {

    var level: Int = 0

    fun createCell(): MutableList<Cell> {
        val returnCells = mutableListOf<Cell>()
        (0 until level).forEach { x ->
            (0 until level).forEach { y ->
                val number = x * level + y
                val cell = Cell()
                cell.status = Cell.Status.CLOSE
                returnCells.add(cell)
            }
        }
        return returnCells
    }
}

class Cell {
    var status:Status? = null
    enum class Status {
        CLOSE
    }
}
