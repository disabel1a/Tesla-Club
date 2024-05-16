package com.example.teslaclub.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.teslaclub.cars.Car

@Dao
interface CarDao {
    @Insert
    fun insert(car: Car): Long

    @Update
    fun update(car: Car): Int

    @Delete
    fun delete(car: Car): Int

    @Query("SELECT * FROM car")
    fun getAll(): LiveData<List<Car>>
}