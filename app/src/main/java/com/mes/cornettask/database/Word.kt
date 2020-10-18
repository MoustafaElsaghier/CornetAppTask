package com.mes.cornettask.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// indices => unique = true to stop duplicate in room db
@Entity(tableName = "word_table", indices = [Index(value = ["word"], unique = true)])
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)
