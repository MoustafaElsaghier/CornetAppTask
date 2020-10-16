package com.mes.cornettask.ui.searchScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mes.cornettask.R
import com.mes.cornettask.adapters.DiscoverMoverAdapter
import com.mes.cornettask.adapters.PopularMoviePagedListAdapter
import com.mes.cornettask.data.api.ApiClient
import com.mes.cornettask.data.pojos.MoviesResponse
import com.mes.cornettask.data.repositories.NetworkState
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_discover.progressBar
import kotlinx.android.synthetic.main.fragment_discover.txtError
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var moviesListRepository: MoviesPageListRepository
    private lateinit var moviesAdapter: DiscoverMoverAdapter
    private lateinit var searchKey: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private fun updateMoviesList(it: MoviesResponse?) {
        if (it != null) {
            moviesAdapter = context?.let { it1 -> DiscoverMoverAdapter(it1, it.results) }!!
            discoverMoviesRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            discoverMoviesRv.adapter = moviesAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = ApiClient.getClient()
        moviesListRepository = MoviesPageListRepository(apiService)

        viewModel = getViewModel(searchKey)

        val adapter = context?.let { PopularMoviePagedListAdapter(it) }
        val linearLayoutManager = LinearLayoutManager(context)
        moviesRv.layoutManager = linearLayoutManager
        moviesRv.setHasFixedSize(true)
        moviesRv.adapter = adapter

        viewModel.moviesPagedList.observe(viewLifecycleOwner, {
            adapter?.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            progressBar.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txtError.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                adapter?.setNetworkState(it)
            }
        })

        viewModel.networkState.observe(viewLifecycleOwner, {
            progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txtError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun getViewModel(searchKey: String): SearchFragmentViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchFragmentViewModel(moviesListRepository, searchKey) as T
            }
        }).get(SearchFragmentViewModel::class.java)

    }

}