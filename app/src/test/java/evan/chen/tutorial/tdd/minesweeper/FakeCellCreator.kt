package evan.chen.tutorial.tdd.minesweeper


class FakeCellCreator :
    ICellCreator {
    var cells:MutableList<Cell>? = null

    override fun createCell(): MutableList<Cell> {
        return cells!!
    }
}