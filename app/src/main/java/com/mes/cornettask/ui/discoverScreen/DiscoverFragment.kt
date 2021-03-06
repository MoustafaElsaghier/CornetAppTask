package com.mes.cornettask.ui.discoverScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mes.cornettask.R
import com.mes.cornettask.adapters.DiscoverMoverAdapter
import com.mes.cornettask.data.api.ApiClient
import com.mes.cornettask.data.pojos.MoviesResponse
import com.mes.cornettask.data.repositories.NetworkState
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment() {

    private lateinit var viewModel: MoviesListViewModel
    private lateinit var moviesListRepository: MoviesListRepo
    private lateinit var moviesAdapter: DiscoverMoverAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    private fun updateMoviesList(it: MoviesResponse?) {
        if (it != null) {
            moviesAdapter = context?.let { it1 -> DiscoverMoverAdapter(it1, it.results) }!!
            discoverMoviesRv.layoutManager =
                GridLayoutManager(context, 2)
            discoverMoviesRv.adapter = moviesAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScreenMovies()
    }

    private fun initScreenMovies() {
        initViewModel()
        setObserverMoviesList()
        setObserverNetworkState()
    }

    /**
     * register observer for network state
     * */
    private fun setObserverNetworkState() {
        viewModel.networkState.observe(viewLifecycleOwner, {
            progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txtError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    /**
     * register observer for movies list
     * */
    private fun setObserverMoviesList() {
        viewModel.moviesList.observe(viewLifecycleOwner, {
            updateMoviesList(it)
        })
    }

    /**
     * init repo & viewModel
     * */
    private fun initViewModel() {
        val apiService = ApiClient.getClient()
        moviesListRepository = MoviesListRepo(apiService)
        viewModel = getViewModel()
    }

    /**
     * init movies viewModel
     * */
    private fun getViewModel(): MoviesListViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MoviesListViewModel(moviesListRepository) as T
            }
        }).get(MoviesListViewModel::class.java)

    }

}