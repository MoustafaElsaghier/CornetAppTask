package com.mes.cornettask.ui.searchScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mes.cornettask.R
import com.mes.cornettask.adapters.PopularMoviePagedListAdapter
import com.mes.cornettask.data.api.ApiClient
import com.mes.cornettask.data.repositories.NetworkState
import kotlinx.android.synthetic.main.fragment_discover.progressBar
import kotlinx.android.synthetic.main.fragment_discover.txtError
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var moviesListRepository: MoviesPageListRepository
    private var searchKey: String = ""
    private lateinit var adapter: PopularMoviePagedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchScreen()
        initViews()
    }

    private fun initViews() {
        searchBtn.setOnClickListener {
            searchKey = movieNameEt.text.toString()
            if (searchKey.isNotEmpty()) {
                viewModel.searchText = searchKey
                viewModel.moviesRepository.fetchLiveMoviePagedList(
                    viewModel.compositeDisposable,
                    searchKey
                )
            }
        }

    }

    private fun initSearchScreen() {
        initViewModel()
        initRecycler()
    }

    private fun initViewModel() {
        val apiService = ApiClient.getClient()
        moviesListRepository = MoviesPageListRepository(apiService)
        viewModel = getViewModel()
        initObservers()
    }

    private fun initObservers() {
        initMoviesObserve()
        initNetworkObserver()
    }

    private fun initNetworkObserver() {
        viewModel.networkState.observe(viewLifecycleOwner, {
            progressBar.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txtError.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                adapter.setNetworkState(it)
            }
        })
    }

    private fun initMoviesObserve() {
        viewModel.moviesPagedList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun initRecycler() {
        adapter = context?.let { PopularMoviePagedListAdapter(it) }!!
        val linearLayoutManager = LinearLayoutManager(context)
        moviesRv.layoutManager = linearLayoutManager
        moviesRv.setHasFixedSize(true)
        moviesRv.adapter = adapter
    }

    private fun getViewModel(): SearchFragmentViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchFragmentViewModel(moviesListRepository) as T
            }
        }).get(SearchFragmentViewModel::class.java)

    }

}