package com.example.teslaclub.cars

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "car")
class Car (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var model: String,
    var vin: String,
    var year: Int,
    var price:Int
)