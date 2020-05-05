package evan.chen.tutorial.tdd.minesweeper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    var items: List<Cell>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            val cell = items[position]
            viewHolder.textView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    private inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView: TextView = itemView.findViewById(R.id.textView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}