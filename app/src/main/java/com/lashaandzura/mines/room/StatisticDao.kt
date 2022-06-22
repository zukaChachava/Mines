package com.lashaandzura.mines.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface StatisticDao {
    @Insert
    fun insert(vararg statistic: Statistic)

    @Query("SELECT * FROM statistic ORDER BY date DESC LIMIT 20")
    fun getAllStatistic(): List<Statistic>

    @Query("SELECT count(result) FROM statistic WHERE result = 1")
    fun getSumWin(): Int

    @Query("SELECT count(result) FROM statistic WHERE result = 0")
    fun getSumLose(): Int

}