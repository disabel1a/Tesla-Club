package com.example.teslaclub

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.teslaclub.cars.Car
import com.example.teslaclub.databinding.AddCarActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCarActivity: AppCompatActivity() {
    private lateinit var binding: AddCarActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddCarActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val carDao = (application as App).database.carDao()

        val models = listOf("Tesla Model S", "Tesla Model 3", "Tesla Model X", "Tesla Model Y", "Tesla Cybertruck")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, models)
        binding.modelSpinner.adapter = adapter

        binding.submitButton.setOnClickListener {
            val model = binding.modelSpinner.selectedItem.toString()
            val vin = binding.vinEdit.text.toString()
            val year = binding.yearEdit.text.toString().toInt()
            val price = binding.priceEdit.text.toString().toInt()

            val car = Car(model = model, vin = vin, year = year, price = price)
            CoroutineScope(Dispatchers.IO).launch {
                carDao.insert(car)
            }

            val intent = Intent(this, RecyclerViewActivity::class.java)
            startActivity(intent)
        }
    }
}