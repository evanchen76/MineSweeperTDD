package evan.chen.tutorial.tdd.minesweeper

class Cell {
    var nextMines: Int = 0
    var x: Int = 0
    var y: Int = 0
    var isMine: Boolean = false
    var status: Status? = null

    enum class Status {
        CLOSE, OPEN
    }
}