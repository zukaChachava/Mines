package com.lashaandzura.mines.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Statistic::class], version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getStatisticDao(): StatisticDao
}