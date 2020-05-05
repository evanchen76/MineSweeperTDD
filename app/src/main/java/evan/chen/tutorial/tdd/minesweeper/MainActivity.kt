package evan.chen.tutorial.tdd.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mineSweeper = MineSweeper()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val level = 9
        mineSweeper.startGame(level)
        mainAdapter = MainAdapter(mineSweeper.cells, this)
        recyclerView.adapter = mainAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(level, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )
    }
}