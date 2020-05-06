package evan.chen.tutorial.tdd.minesweeper

interface ICellCreator {
    fun createCell(): MutableList<Cell>
}