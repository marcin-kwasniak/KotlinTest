package pl.marcinkwasniak.test.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.result_row.view.*
import pl.marcinkwasniak.test.R

/**
 * Created by marcin.kwasniak on 31/03/2019
 */
class SongAdapter(private var data: List<SongViewModel>) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mainView = view
        val title: TextView = view.item_title
        val artist: TextView = view.item_artist
        val year: TextView = view.item_year
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder =
        SongViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.result_row,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currentItem = data[position]
        holder.mainView.tag = currentItem
        holder.title.text = currentItem.title
        holder.artist.text = currentItem.artist
        holder.year.text = currentItem.year
    }

    override fun getItemCount() = data.size

    fun update(data: List<SongViewModel>) {
        this.data = data
        notifyDataSetChanged()
    }
}