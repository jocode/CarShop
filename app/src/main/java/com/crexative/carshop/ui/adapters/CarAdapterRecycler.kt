package com.crexative.carshop.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crexative.carshop.R
import com.crexative.carshop.data.Car
import com.crexative.carshop.data.Category

class CarAdapterRecycler(
    private val carsList: ArrayList<Car>,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<CarAdapterRecycler.ViewHolder>() {

    var categories = ArrayList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_car, parent, false)

        Category.findAll(Category::class.java).forEach {
            categories.add(it)
        }

        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(carsList[position], itemClickListener, categories)
    }

    override fun getItemCount(): Int {
        return carsList.size
    }

    class ViewHolder(itemView : View, val context: Context) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(
            car: Car,
            itemClickListener: ItemClickListener,
            categories: ArrayList<Category>
        ) {

            val carClickable = itemView.findViewById(R.id.car_clickable) as FrameLayout
            val carPicture = itemView.findViewById(R.id.car_picture) as ImageView

            val category = itemView.findViewById(R.id.car_category) as TextView
            val model = itemView.findViewById(R.id.car_model) as TextView
            val status = itemView.findViewById(R.id.car_status) as TextView
            val price = itemView.findViewById(R.id.car_price) as TextView
            val sits = itemView.findViewById(R.id.car_sits) as TextView
            val dateReleased = itemView.findViewById(R.id.car_date_released) as TextView


            category.text = categories[car.category].category
            model.text = "Model: " + car.model
            status.text = car.state
            price.text = "$ " + car.price.toString()
            sits.text = "Date Released: " + car.dateReleased.toString()
            dateReleased.text = "Seats: " + car.seats.toString()

            val drawableResource = when (car.category) {
                0 -> R.drawable.electric_car_vector
                1 -> R.drawable.comercial_car
                2 -> R.drawable.truck_car
                else -> R.drawable.comercial_car
            }

            carPicture.setImageResource(drawableResource)

            carClickable.setOnClickListener {
                itemClickListener.onItemClickListener(car)
            }

        }

    }

    interface ItemClickListener {
        fun onItemClickListener(data: Car)
    }

}