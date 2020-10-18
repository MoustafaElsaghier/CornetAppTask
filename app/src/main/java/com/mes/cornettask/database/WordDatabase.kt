package com.mes.cornettask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract val wordDao: WordDao?

    fun cleanUp() {
        wordDB = null
    }

    companion object {
        private var wordDB: WordDatabase? = null
        fun getInstance(context: Context): WordDatabase? {
            if (null == wordDB) {
                wordDB = buildDatabaseInstance(context)
            }
            return wordDB
        }

        private fun buildDatabaseInstance(context: Context): WordDatabase {
            return Room.databaseBuilder(
                context,
                WordDatabase::class.java,
                "word_database"
            )
                .allowMainThreadQueries().build()
        }
    }
}