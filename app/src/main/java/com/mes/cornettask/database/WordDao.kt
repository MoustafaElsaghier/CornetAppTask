package com.mes.cornettask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * from word_table")
    fun getViewModelSearchWords(): LiveData<List<Word>>

    @Query("SELECT * from word_table")
    fun getSearchWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: Word)

}