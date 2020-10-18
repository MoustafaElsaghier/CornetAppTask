package com.mes.cornettask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mes.cornettask.R
import com.mes.cornettask.data.pojos.MovieModel
import com.mes.cornettask.data.repositories.NetworkState
import com.mes.cornettask.data.utils.ImageHelper
import kotlinx.android.synthetic.main.newtork_loading_item.view.*
import kotlinx.android.synthetic.main.search_item.view.*

class PopularMoviePagedListAdapter(private val context: Context) :
    PagedListAdapter<MovieModel, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    //    companion object {
//        const val MOVIE_VIEW_TYPE = 1
//        const val NETWORK_VIEW_TYPE = 2
//    }
//
//    private var networkState: NetworkState? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val view: View
//
//        return if (viewType == MOVIE_VIEW_TYPE) {
//            view = layoutInflater.inflate(R.layout.search_item, parent, false)
//            MovieViewHolder(view)
//        } else {
//            view = layoutInflater.inflate(R.layout.newtork_loading_item, parent, false)
//            NetworkStateItemViewHolder(view)
//        }
//    }
//
//    // check if networkState is loading to show itemView for progressBar
//    private fun hasExtraRow(): Boolean {
//        return networkState != null && networkState != NetworkState.LOADED
//    }
//
//    /**
//     * add 1 or 0 depend on state of data loading so that,
//     * if the network state is loading then RecyclerView inflate one more row for loading
//     *
//     **/
//    override fun getItemCount(): Int {
//        return super.getItemCount() + if (hasExtraRow()) 1 else 0
//    }
//
//    /**
//     * set item view type depend on status of progressBar & count
//     * */
//    override fun getItemViewType(position: Int): Int {
//        return if (hasExtraRow() && position == itemCount - 1) {
//            NETWORK_VIEW_TYPE
//        } else {
//            MOVIE_VIEW_TYPE
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
//            getItem(position)?.let { (holder as MovieViewHolder).bindView(it, context) }
//        } else {
//            (holder as NetworkStateItemViewHolder).bind(networkState)
//        }
//    }
//
//    class MoviesDiffCallBack : DiffUtil.ItemCallback<MovieModel>() {
//        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bindView(item: MovieModel, context: Context) {
//            itemView.movieName.text = item.title
//            itemView.movieReleaseDate.text = item.releaseDate
//            itemView.movieOverview.text = item.overview
//
//            item.posterPath.let { ImageHelper.loadImage(context, itemView.movieImage, it) }
//
//        }
//    }
//
//    class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        fun bind(networkState: NetworkState?) {
//            if (networkState != null && networkState == NetworkState.LOADING) {
//                itemView.progress_bar_item.visibility = View.VISIBLE
//            } else {
//                itemView.progress_bar_item.visibility = View.GONE
//            }
//
//            if (networkState != null && networkState == NetworkState.ERROR) {
//                itemView.errorMessage.visibility = View.VISIBLE
//                itemView.errorMessage.text = networkState.msg
//            } else if (networkState != null && networkState == NetworkState.END_OF_LIST) {
//                itemView.errorMessage.visibility = View.VISIBLE
//                itemView.errorMessage.text = networkState.msg
//            } else {
//                itemView.errorMessage.visibility = View.GONE
//            }
//        }
//    }
//
//    fun setNetworkState(newNetworkState: NetworkState) {
//        val previousState = this.networkState
//        val hadExtraRow = hasExtraRow()
//        this.networkState = newNetworkState
//        val hasExtraRow = hasExtraRow()
//
//        if (hadExtraRow != hasExtraRow) {
//            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
//                //remove the progressbar at the end
//                notifyItemRemoved(super.getItemCount())
//            } else {                                       //hasExtraRow is true and hadExtraRow false
//                //add the progressbar at the end
//                notifyItemInserted(super.getItemCount())
//            }
//            //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
//        } else if (hasExtraRow && previousState != newNetworkState) {
//            notifyItemChanged(itemCount - 1)       //add the network message at the end
//        }
//
//    }
    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.search_item, parent, false)
            return MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.newtork_loading_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }


    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: MovieModel?, context: Context) {
            itemView.movieName.text = item?.title
            itemView.movieReleaseDate.text = item?.releaseDate
            itemView.movieOverview.text = item?.overview

            item?.posterPath.let {
                it?.let { it1 ->
                    ImageHelper.loadImage(
                        context, itemView.movieImage,
                        it1
                    )
                }
            }
        }

    }

    class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE
            } else {
                itemView.progress_bar_item.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.errorMessage.visibility = View.VISIBLE
                itemView.errorMessage.text = networkState.msg
            } else if (networkState != null && networkState == NetworkState.END_OF_LIST) {
                itemView.errorMessage.visibility = View.VISIBLE
                itemView.errorMessage.text = networkState.msg
            } else {
                itemView.errorMessage.visibility = View.GONE
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }

}