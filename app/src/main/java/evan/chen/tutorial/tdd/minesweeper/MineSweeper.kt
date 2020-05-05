package evan.chen.tutorial.tdd.minesweeper

class MineSweeper {
    var cells: List<Cell> = listOf()
    fun startGame(level: Int) {
        val cellCreator = CellCreator()
        cellCreator.level = level
        cells = cellCreator.createCell()
    }
}
