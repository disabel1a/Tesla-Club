package com.example.teslaclub.cars

import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teslaclub.EditCarActivity
import com.example.teslaclub.R
import com.example.teslaclub.dao.CarDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarAdapter(private val carDao: CarDao) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    private var cars: List<Car> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    override fun getItemCount(): Int = cars.size

    fun setCarsList(cars: List<Car>) {
        this.cars = cars
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.imageView)
        private val model: TextView = itemView.findViewById(R.id.model)
        private val vin: TextView = itemView.findViewById(R.id.vin)
        private val year: TextView = itemView.findViewById(R.id.year)
        private val price: TextView = itemView.findViewById(R.id.price)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener {
                showPopupMenu(it, adapterPosition)
                true
            }
        }

        fun bind(car: Car) {
            model.text = car.model
            vin.text = car.vin;
            year.text = car.year.toString()
            price.text = car.price.toString() + "$"

            val carImageResourceId = getCarImageResource(car.model)
            if (carImageResourceId != null) {
                image.setImageResource(carImageResourceId)
            } else {
            }
        }

        override fun onClick(v: View?) {
            // Handle item clicks here
        }

        private fun getCarImageResource(model: String): Int? {
            val carModelImageMap = mapOf(
                "Tesla Model 3" to R.drawable.tesla_model_3,
                "Tesla Model X" to R.drawable.tesla_model_x,
                "Tesla Model Y" to R.drawable.tesla_model_y,
                "Tesla Model S" to R.drawable.tesla_model_s,
                "Tesla Cybertruck" to R.drawable.tesla_cybertruck
            )

            return carModelImageMap[model]
        }
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(view.context, view)

        popupMenu.menu.add(0, 1, Menu.NONE, "Edit")
        popupMenu.menu.add(0, 2, Menu.NONE, "Remove")

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                1 -> {
                    val car = cars[position]
                    val intent = Intent(view.context, EditCarActivity::class.java)
                    intent.putExtra("carId", car.id.toString())
                    intent.putExtra("carModel", car.model)
                    intent.putExtra("carVin", car.vin)
                    intent.putExtra("carYear", car.year.toString())
                    intent.putExtra("carPrice", car.price.toString())

                    view.context.startActivity(intent)
                    true
                }
                2 -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        val car = cars[position]
                        carDao.delete(car)
                    }
                    notifyItemRemoved(position)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}