package com.example.teslaclub.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teslaclub.cars.Car

@Database(entities = [Car::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun carDao(): CarDao
}