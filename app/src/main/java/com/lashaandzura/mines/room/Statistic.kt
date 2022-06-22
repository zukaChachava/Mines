package com.lashaandzura.mines.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "statistic")
data class Statistic (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name="result")
    val result: Boolean,

    @ColumnInfo(name="date")
    val date: String
)
