package evan.chen.tutorial.tdd.minesweeper

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface ICellTapListener {
    fun onCellClick(cell: Cell)
    fun onCellLongClick(cell: Cell)
}

class MainAdapter(
    var items: List<Cell>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: ICellTapListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {
            val cell = items[position]
            viewHolder.textView.visibility = View.GONE
            viewHolder.imageView.visibility = View.GONE

            if (cell.status == Cell.Status.CLOSE) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY)

                if (cell.isFlag) {
                    viewHolder.textView.visibility = View.GONE
                    viewHolder.imageView.visibility = View.VISIBLE
                    viewHolder.imageView.setImageResource(R.mipmap.flag)
                }
            }

            if (cell.status == Cell.Status.OPEN) {
                viewHolder.itemView.setBackgroundColor(Color.WHITE)
                if (cell.isMine) {
                    viewHolder.textView.visibility = View.GONE
                    viewHolder.imageView.visibility = View.VISIBLE
                    viewHolder.imageView.setImageResource(R.mipmap.mine)
                } else if (cell.nextMines != 0) {
                    viewHolder.textView.visibility = View.VISIBLE
                    viewHolder.imageView.visibility = View.GONE
                    viewHolder.textView.text = cell.nextMines.toString()
                }
            }

            viewHolder.itemView.setOnClickListener {
                listener?.onCellClick(cell)
            }

            viewHolder.itemView.setOnLongClickListener {
                listener?.onCellLongClick(cell)
                true
            }
        }
    }

    fun setCellListener(listener: ICellTapListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    private inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView: TextView = itemView.findViewById(R.id.textView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}