package com.mes.cornettask.ui.normalsearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mes.cornettask.R
import com.mes.cornettask.adapters.SearchMoverAdapter
import com.mes.cornettask.adapters.WordListAdapter
import com.mes.cornettask.data.api.ApiClient
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.MovieModel
import com.mes.cornettask.data.utils.EndlessRecyclerViewScrollListener
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*

class NormalSearchFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val moviesList: ArrayList<MovieModel> = ArrayList()
    private lateinit var apiService: MovieInterface
    private lateinit var wordsAdapter: WordListAdapter
    private var searchKey: String = ""
    private lateinit var moviesAdapter: SearchMoverAdapter
    private val compositeDisposable = CompositeDisposable()
    lateinit var scroller: EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        apiService = ApiClient.getClient()
        initRecycler()
        initSearchEditText()
        searchBtn.setOnClickListener {
            searchKey = movieNameEt.text.toString()
            if (searchKey.isNotEmpty()) {
                // page =  1 here to reset it
                searchMovies(searchKey, 1)
            } else {
                Toast.makeText(context, getString(R.string.movie_empty), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun searchMovies(searchKey: String, page: Int) {
        compositeDisposable.add(
            apiService.getSearchMovieAsync(searchKey, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.results.isNotEmpty()) {
                            moviesList.addAll(it.results)
                            moviesAdapter.notifyItemRangeInserted(
                                moviesAdapter.itemCount,
                                it.results.size
                            )
                        }
                    },
                    {
                        it.message?.let { it1 -> Log.e("MovieDetailsDataSource", it1) }
                    }
                )
        )
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
    }

    private fun initRecycler() {
        moviesAdapter = context?.let { SearchMoverAdapter(it, moviesList) }!!
        linearLayoutManager = LinearLayoutManager(context)
        moviesRv.layoutManager = linearLayoutManager
        moviesRv.setHasFixedSize(true)
        moviesRv.adapter = moviesAdapter

        scroller = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (page > 1) searchMovies(searchKey, page)
            }
        }
    }

    private fun initWordRecycler() {
        wordsAdapter = context?.let { WordListAdapter(it) }!!
        wordsRecycler.adapter = wordsAdapter
        wordsRecycler.layoutManager = LinearLayoutManager(context)
    }
}