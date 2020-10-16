package com.mes.cornettask.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Word(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @PrimaryKey @ColumnInfo(name = "word") val word: String
)
