package com.mes.cornettask.ui.normalsearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mes.cornettask.R
import com.mes.cornettask.adapters.PopularMoviePagedListAdapter
import com.mes.cornettask.adapters.WordListAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class NormalSearchFragment : Fragment() {

    private lateinit var wordsAdapter: WordListAdapter
    private var searchKey: String = ""
    private lateinit var moviesAdapter: PopularMoviePagedListAdapter

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

            } else {
                Toast.makeText(context, getString(R.string.movie_empty), Toast.LENGTH_LONG).show()
            }
        }
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
        moviesAdapter = context?.let { PopularMoviePagedListAdapter(it) }!!
        val linearLayoutManager = LinearLayoutManager(context)
        moviesRv.layoutManager = linearLayoutManager
        moviesRv.setHasFixedSize(true)
        moviesRv.adapter = moviesAdapter
    }

    private fun initWordRecycler() {
        wordsAdapter = context?.let { WordListAdapter(it) }!!
        wordsRecycler.adapter = wordsAdapter
        wordsRecycler.layoutManager = LinearLayoutManager(context)
    }
}