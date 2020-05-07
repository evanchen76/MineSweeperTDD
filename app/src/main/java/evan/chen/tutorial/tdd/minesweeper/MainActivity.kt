package evan.chen.tutorial.tdd.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ICellTapListener, IMineSweeperListener{

    private val mineSweeper = MineSweeper()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val level = 9
        val cellCreator = CellCreator()
        cellCreator.level = level
        mineSweeper.startGame(cellCreator)
        mineSweeper.setMineSweeperListener(this)

        mainAdapter = MainAdapter(mineSweeper.cells, this)
        mainAdapter.setCellListener(this)

        recyclerView.adapter = mainAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(level, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )
    }

    override fun winGame() {
        gameStatus.text = "你贏了"
    }

    override fun lostGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCellClick(cell: Cell) {
        mineSweeper.tap(cell.x, cell.y)
        mainAdapter.notifyDataSetChanged()
    }

    override fun onCellLongClick(cell: Cell) {
        mineSweeper.tapFlag(cell.x, cell.y)
        mainAdapter.notifyDataSetChanged()
    }
}