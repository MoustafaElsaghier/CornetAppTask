package com.mes.cornettask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mes.cornettask.R
import com.mes.cornettask.data.pojos.MovieModel
import com.mes.cornettask.data.utils.ImageHelper.loadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class DiscoverMoverAdapter(private val context: Context, private val items: List<MovieModel>) :
    RecyclerView.Adapter<DiscoverMoverAdapter.DiscoverViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return DiscoverViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        holder.bindView(items[position], context)
    }

    override fun getItemCount(): Int = items.size


    class DiscoverViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        // here comes all the views / variables from view.findViewById()
        // Example: val textView = view.findViewById(R.id.textView1)
        fun bindView(item: MovieModel, context: Context) {
            itemView.movieName.text = item.title
            itemView.movieReleaseDate.text = item.releaseDate
            itemView.movieOverview.text = item.overview

            loadImage(context, itemView.movieImage, item.posterPath)

        }
    }

}