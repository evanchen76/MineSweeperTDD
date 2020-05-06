package evan.chen.tutorial.tdd.minesweeper

class CellCreator : ICellCreator {

    var level: Int = 0

    override fun createCell(): MutableList<Cell> {
        val init = mutableListOf<String>()
        init.add("*|-|-|-|-|-|-|-|-")
        init.add("-|-|-|-|-|-|-|-|-")
        init.add("-|-|-|-|-|*|-|-|-")
        init.add("-|-|-|-|-|-|-|-|-")
        init.add("-|-|-|-|-|-|-|-|-")
        init.add("-|-|-|*|-|-|-|-|-")
        init.add("-|-|-|-|-|-|-|-|-")
        init.add("-|-|-|-|-|-|*|-|-")
        init.add("-|-|*|-|-|-|-|-|-")

        return createCellsFromString(init)
    }

    private fun createCellsFromString(initSweeper: List<String>): MutableList<Cell> {
        var cells: MutableList<Cell> = mutableListOf()

        initSweeper.forEachIndexed { yIndex, yList ->
            val lines = yList.split("|")
            lines.forEachIndexed { xIndex, value ->
                val cell = Cell()
                cell.x = xIndex
                cell.y = yIndex

                cell.status =
                    Cell.Status.CLOSE
                if (value == " ") {
                    cell.status =
                        Cell.Status.OPEN

                }
                cell.isMine = false
                if (value == "*") {
                    cell.isMine = true
                }

                cells.add(cell)
            }
        }
        return cells
    }
}

