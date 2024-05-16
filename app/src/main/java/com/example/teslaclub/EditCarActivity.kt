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

class EditCarActivity: AppCompatActivity() {
    private lateinit var binding: AddCarActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddCarActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val carDao = (application as App).database.carDao()

        val models = listOf("Tesla Model S", "Tesla Model 3", "Tesla Model X", "Tesla Model Y", "Tesla Cybertruck")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, models)
        binding.modelSpinner.adapter = adapter

        val modelPos = models.indexOf(intent.getStringExtra("carModel"))
        binding.modelSpinner.setSelection(modelPos)

        binding.vinEdit.setText(intent.getStringExtra("carVin"))
        binding.yearEdit.setText(intent.getStringExtra("carYear"))
        binding.priceEdit.setText(intent.getStringExtra("carPrice"))

        binding.submitButton.setOnClickListener {
            val id = intent.getStringExtra("carId")!!.toInt()

            val model = binding.modelSpinner.selectedItem.toString()
            val vin = binding.vinEdit.text.toString()
            val year = binding.yearEdit.text.toString().toInt()
            val price = binding.priceEdit.text.toString().toInt()

            val car = Car(id = id, model = model, vin = vin, year = year, price = price)
            CoroutineScope(Dispatchers.IO).launch {
                carDao.update(car)
            }

            val intent = Intent(this, RecyclerViewActivity::class.java)
            startActivity(intent)
        }
    }
}