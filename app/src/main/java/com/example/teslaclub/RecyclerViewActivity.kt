package com.example.teslaclub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teslaclub.cars.CarAdapter
import com.example.teslaclub.databinding.RecyclerViewActivityBinding

class RecyclerViewActivity: AppCompatActivity() {
    private lateinit var binding: RecyclerViewActivityBinding
    private lateinit var adapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerViewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val carDao = (application as App).database.carDao()

        val layoutManager = LinearLayoutManager(this)
        adapter = CarAdapter(carDao)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        carDao.getAll().observe(this) { cars ->
            adapter.setCarsList(cars)
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddCarActivity::class.java)
            startActivity(intent)
        }
    }
}