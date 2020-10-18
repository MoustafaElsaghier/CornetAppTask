package com.mes.cornettask.ui.searchScreen

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mes.cornettask.R
import com.mes.cornettask.adapters.PopularMoviePagedListAdapter
import com.mes.cornettask.data.api.ApiClient
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.repositories.NetworkState
import kotlinx.android.synthetic.main.fragment_discover.progressBar
import kotlinx.android.synthetic.main.fragment_discover.txtError
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchFragmentViewModel
    lateinit var movieRepository: MoviesPageListRepository

//    private lateinit var moviesListRepository: MoviesPageListRepository

    private var searchKey: String = ""
    private lateinit var moviesAdapter: PopularMoviePagedListAdapter
//    private lateinit var wordsAdapter: WordListAdapter
//    private lateinit var wordViewModel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService: MovieInterface = ApiClient.getClient()

        movieRepository = MoviesPageListRepository(apiService)

        viewModel = getViewModel()
        viewModel.searchText.postValue("a")
        val movieAdapter = context?.let { PopularMoviePagedListAdapter(it) }

        val linearLayoutManager = LinearLayoutManager(context)
        moviesRv.layoutManager = linearLayoutManager
        moviesRv.setHasFixedSize(true)
        moviesRv.adapter = movieAdapter

        viewModel.moviesPagedList.observe(viewLifecycleOwner, {
            movieAdapter?.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, {
            progressBar.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txtError.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter?.setNetworkState(it)
            }
        })

        initViews()
//        initSearchScreen()
    }

    private fun initViews() {
//        wordViewModel = initWordsViewModel()

//        wordViewModel.allWords.observe(viewLifecycleOwner, { words ->
//        Update the cached copy of the words in the adapter .
//            words?.let { wordsAdapter.setWords(it) }
//        })
        moviesAdapter = context?.let { PopularMoviePagedListAdapter(it) }!!

        initSearchEditText()
        searchBtn.setOnClickListener {
            searchKey = movieNameEt.text.toString()
            if (searchKey.isNotEmpty()) {
                viewModel.searchText.postValue(searchKey)
            } else {
                Toast.makeText(context, getString(R.string.movie_empty), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initWordsViewModel(): WordViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return WordViewModel(Application()) as T
            }
        }).get(WordViewModel::class.java)
    }

    private fun initSearchEditText() {
        movieNameEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                /**
                 * show list of save words in case of size is> 0
                 * */
//                if (wordsAdapter.itemCount > 0)
//                    wordsRecycler.visibility = View.VISIBLE
//                else
//                    wordsRecycler.visibility = View.GONE
            }
        }

        movieNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // check if the text is empty to show history list
//                if (s?.isEmpty()!!) {
//                    if (wordsAdapter.itemCount > 0)
//                        wordsRecycler.visibility =
//                            View.VISIBLE // show if there is already items in room db
//                    else
//                        wordsRecycler.visibility = View.GONE
//                } else
//                    wordsRecycler.visibility = View.GONE  // hide if the length is > 0
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initSearchScreen() {
        initRecycler()
        initViewModel()
    }

    private fun initViewModel() {
        val apiService = ApiClient.getClient()
        movieRepository = MoviesPageListRepository(apiService)
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
                moviesAdapter.setNetworkState(it)
            }
        })
    }

    private fun initMoviesObserve() {
        viewModel.searchText.postValue("")
        viewModel.moviesPagedList.observe(viewLifecycleOwner, {
            moviesAdapter.submitList(it)
        })
    }

    private fun initRecycler() {
        moviesAdapter = context?.let { PopularMoviePagedListAdapter(it) }!!
        val linearLayoutManager = LinearLayoutManager(context)
        moviesRv.layoutManager = linearLayoutManager
        moviesRv.setHasFixedSize(true)
        moviesRv.adapter = moviesAdapter
    }

    private fun getViewModel(): SearchFragmentViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchFragmentViewModel(movieRepository) as T
            }
        }).get(SearchFragmentViewModel::class.java)
    }

//    private fun initWordRecycler() {
//        wordsAdapter = context?.let { WordListAdapter(it) }!!
//        wordsRecycler.adapter = wordsAdapter
//        wordsRecycler.layoutManager = LinearLayoutManager(context)
//    }
}