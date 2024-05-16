package com.example.teslaclub

import android.app.Application
import androidx.room.Room
import com.example.teslaclub.dao.AppDataBase

class App: Application() {
    lateinit var database: AppDataBase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "car_database").build()
    }
}