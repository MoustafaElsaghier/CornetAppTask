package com.mes.cornettask.ui.searchScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mes.cornettask.data.repositories.WordRepository
import com.mes.cornettask.database.Word
import com.mes.cornettask.database.WordRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/**
 * ths was my try to make it using mvvm, pagination, to save your time, ignore this package
 * if not interested to check my try
 * */
class WordViewModel(application: Application) : AndroidViewModel(application) {
    val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()

    private val repository: WordRepository

    private val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}
