package com.mes.cornettask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: WordDao?

    fun cleanUp() {
        noteDB = null
    }

    companion object {
        private var noteDB: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase? {
            if (null == noteDB) {
                noteDB = buildDatabaseInstance(context)
            }
            return noteDB
        }

        private fun buildDatabaseInstance(context: Context): NoteDatabase {
            return Room.databaseBuilder(
                context,
                NoteDatabase::class.java,
                "word_database"
            )
                .allowMainThreadQueries().build()
        }
    }
}