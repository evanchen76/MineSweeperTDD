package evan.chen.tutorial.tdd.minesweeper

class MineSweeper {
    var cells: List<Cell> = listOf()
    var listener: IMineSweeperListener? = null

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

        //點到地雷，輸了
        if (cell.isMine) {
            listener?.lostGame()
        }

        //判斷是否除了有地雷方格都被打開了。
        if (cells.find { it.status != Cell.Status.OPEN && !it.isMine } == null) {
            listener?.winGame()
        }

        //如該Cell的附近是地雷數是0，則顯示週圍8格的地雷數。
        if (cell.nextMines == 0) {
            //找附近
            for (i in -1..1) {
                for (j in -1..1) {
                    val newX = cell.x + i
                    val newY = cell.y + j
                    val nextCell = findCell(newX, newY)
                    if (nextCell?.isMine == false && nextCell.status == Cell.Status.CLOSE) {
                        if (nextCell.nextMines == 0) {
                            //旁邊=0，則自動點擊
                            tap(newX, newY)
                        } else {
                            //旁邊!=0，顯示
                            nextCell.status = Cell.Status.OPEN
                        }
                    }
                }
            }
        }
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

    fun tapFlag(x: Int, y: Int) {
        val findCell = findCell(x, y)!!
        findCell.isFlag = true
    }

    fun setMineSweeperListener(sweeperListener: IMineSweeperListener?) {
        listener = sweeperListener
    }
}
