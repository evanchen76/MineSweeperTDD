package evan.chen.tutorial.tdd.minesweeper

class MineSweeper {
    var cells: List<Cell> = listOf()
    fun startGame(cellCreator: ICellCreator) {
        cells = cellCreator.createCell()
    }

    fun tap(x: Int, y: Int) {
        val cell = cells.find { it.x == x && it.y == y }!!
        cell.status = Cell.Status.OPEN
    }

    fun findCell(x: Int, y: Int) = cells.find { it.x == x && it.y == y }
}
