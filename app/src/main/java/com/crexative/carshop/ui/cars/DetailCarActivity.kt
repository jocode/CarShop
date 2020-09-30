package com.crexative.carshop.ui.cars

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crexative.carshop.R
import com.crexative.carshop.data.Car
import com.crexative.carshop.data.Category
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DetailCarActivity : AppCompatActivity() {

    private var idCar: Long? = null
    lateinit var category: String

    // Static Member for class
    companion object {
        val EXTRA_CAR: String = "car_data"
        val EXTRA_ID_CAR: String = "car_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_car)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_activity_detail_car)

        // Get ExtraData as Serializable Object
        val car = intent.getSerializableExtra(EXTRA_CAR) as? Car
        idCar = intent.getLongExtra(AddCarActivity.EXTRA_ID_CAR, 1)

        if (car != null) {
            initViews(car, idCar!!)
        }

    }

    private fun initViews(car: Car, idCar: Long) {
        val detailImage: ImageView = findViewById(R.id.detail_image)
        val detailModel: TextView = findViewById(R.id.detail_model)
        val detailPrice: TextView = findViewById(R.id.detail_price)
        val detailSeats: TextView = findViewById(R.id.detail_seats)
        val detailDateReleased: TextView = findViewById(R.id.detail_date_released)
        val detailCategory: TextView = findViewById(R.id.detail_category)
        val detailState: TextView = findViewById(R.id.detail_state)
        val btnEdit: FloatingActionButton = findViewById(R.id.btn_edit)

        detailImage.setImageResource(
            getImageResourcecategory(car.category)
        )

        category = getCategoryName(car.category)
        detailModel.text = "Model: " + car.model
        detailPrice.text = "Price: $" + car.price
        detailSeats.text = "Seats: " + car.seats
        detailDateReleased.text = "Date Released: " + car.dateReleased
        detailCategory.text = "Category: " + category
        detailState.text = "State: " + car.state

        if (category == "Electric") {
            btnEdit.visibility = View.GONE
        }


        btnEdit.setOnClickListener {
            val intent = Intent(baseContext, AddCarActivity::class.java)
            intent.putExtra(AddCarActivity.EXTRA_CAR, car)
            intent.putExtra(AddCarActivity.EXTRA_ID_CAR, idCar)
            intent.action = "edit_car"
            startActivity(intent)
        }

    }

    private fun getCategoryName(category: Int): String {
        var categories = ArrayList<Category>()
        Category.findAll(Category::class.java).forEach {
            categories.add(it)
        }

        return categories[category].category
    }

    private fun getImageResourcecategory(category: Int): Int {
        return when (category) {
            0 -> R.drawable.electric_car_vector
            1 -> R.drawable.comercial_car
            2 -> R.drawable.truck_car
            else -> R.drawable.comercial_car
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Hide action delete Car
        if (category == "Electric")
            return false

        menuInflater.inflate(R.menu.menu_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu_delete -> {
                // Delete Car Item
                var carDelete = Car.findById(Car::class.java, idCar)
                carDelete.delete()

                Toast.makeText(baseContext, "Car deleted", Toast.LENGTH_SHORT).show()
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

}