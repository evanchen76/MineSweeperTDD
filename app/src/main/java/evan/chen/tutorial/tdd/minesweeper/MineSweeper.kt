package evan.chen.tutorial.tdd.minesweeper

class MineSweeper {
    var cells: List<Cell> = listOf()
    fun startGame(cellCreator: ICellCreator) {
        cells = cellCreator.createCell()
        cells.forEach { cell ->
            //設定每個Cell的週圍地圍數
            setCellNextStatus(cell)
        }
    }

    fun tap(x: Int, y: Int) {
        val cell = cells.find { it.x == x && it.y == y }!!
        cell.status = Cell.Status.OPEN
    }

    fun findCell(x: Int, y: Int) = cells.find { it.x == x && it.y == y }

    private fun setCellNextStatus(cell: Cell) {
        var nextMines = 0
        //計算目標週圍8格
        for (i in -1..1) {
            for (j in -1..1) {
                val nextX = cell.x + i
                val nextY = cell.y + j
                if (nextX < 0 || nextY < 0) {
                    continue
                }
                if (cell.x == nextX && cell.y == nextY) {
                    continue
                }
                if (findCell(nextX, nextY)?.isMine == true) {
                    nextMines++
                }
            }
        }

        cell.nextMines = nextMines
    }
}
