package com.mes.cornettask.ui.discoverScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mes.cornettask.R
import com.mes.cornettask.adapters.DiscoverMoverAdapter
import com.mes.cornettask.data.api.ApiClient
import com.mes.cornettask.data.pojos.DiscoverMoviesResponse
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
        val apiService = ApiClient.getClient()
        moviesListRepository = MoviesListRepo(apiService)

        viewModel = getViewModel()
        viewModel.moviesList.observe(viewLifecycleOwner, {
            updateMoviesList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, {
            progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txtError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    private fun updateMoviesList(it: DiscoverMoviesResponse?) {
        if (it != null) {
            moviesAdapter = context?.let { it1 -> DiscoverMoverAdapter(it1, it.results) }!!
        }
    }

    private fun getViewModel(): MoviesListViewModel {
        return ViewModelProvider(this).get(MoviesListViewModel::class.java)
    }


}