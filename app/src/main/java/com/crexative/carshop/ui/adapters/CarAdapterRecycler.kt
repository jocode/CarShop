package com.crexative.carshop.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.crexative.carshop.R
import com.crexative.carshop.data.Car

class CarAdapterRecycler(val carsList : ArrayList<Car>) :
    RecyclerView.Adapter<CarAdapterRecycler.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_car, parent, false)

        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(carsList[position])
    }

    override fun getItemCount(): Int {
        return carsList.size
    }

    class ViewHolder(itemView : View, val context: Context) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(car : Car) {
            val car_clickable = itemView.findViewById(R.id.car_clickable) as FrameLayout

            val category = itemView.findViewById(R.id.car_category) as TextView
            val model = itemView.findViewById(R.id.car_model) as TextView
            val status = itemView.findViewById(R.id.car_status) as TextView
            val price = itemView.findViewById(R.id.car_price) as TextView
            val sits = itemView.findViewById(R.id.car_sits) as TextView


            category.text = car.category.toString()
            model.text =  "Model: " + car.model
            status.text = car.state
            price.text = "$" + car.price.toString()
            sits.text = "Sits" + car.sits.toString()

            car_clickable.setOnClickListener {
                Toast.makeText(context, "Hola" + car.model, Toast.LENGTH_SHORT).show()
            }

        }

    }

}