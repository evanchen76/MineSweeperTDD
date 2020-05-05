package evan.chen.tutorial.tdd.minesweeper

class MineSweeper {
    var cells: List<Cell> = listOf()
    fun startGame(cellCreator: CellCreator) {
        cells = cellCreator.createCell()
    }
}
