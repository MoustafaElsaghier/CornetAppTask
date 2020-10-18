package com.mes.cornettask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mes.cornettask.R
import com.mes.cornettask.data.utils.Utils.hideSoftKeyBoard
import com.mes.cornettask.database.Word
import com.mes.cornettask.ui.normalsearch.NormalSearchFragment
import kotlinx.android.synthetic.main.word_item.view.*

class WordListAdapter internal constructor(
    private val context: Context,
    private val fragment: NormalSearchFragment
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.word_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.itemView.textView.text = current.word
        holder.itemView.setOnClickListener {
            fragment.searchMovies(current.word, 1)
            fragment.searchKey = current.word
            hideSoftKeyBoard(context, it)
        }
    }

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}