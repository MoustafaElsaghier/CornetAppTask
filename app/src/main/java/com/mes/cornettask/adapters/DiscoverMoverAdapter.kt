package com.mes.cornettask.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mes.cornettask.R
import com.mes.cornettask.data.api.POSTER_BASE_URL
import com.mes.cornettask.data.pojos.MovieModel
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

            Glide
                .with(context)
                .load(POSTER_BASE_URL + item.posterPath)
                .centerCrop()
                .override(600, 600)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.movieImage.setImageDrawable(resource)
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.movieImage.setImageResource(R.drawable.dummy_image)
                        return false
                    }
                })
                .into(itemView.movieImage)
        }
    }

}