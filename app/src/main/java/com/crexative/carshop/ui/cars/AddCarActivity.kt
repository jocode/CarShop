package com.crexative.carshop.ui.cars

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.crexative.carshop.R
import com.crexative.carshop.data.Car
import com.crexative.carshop.data.Category
import com.crexative.carshop.ui.dialogFraments.DatePickerFragment
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.add_car_activity.*


class AddCarActivity : AppCompatActivity(), DatePickerFragment.OnDateSelectedListener {

    companion object {
        var EXTRA_CAR: String = "car_data"
        var EXTRA_ID_CAR: String = "car_id"
    }

    private var date: String? = ""
    var categoryList = ArrayList<String>()

    lateinit var tvCarReleased: TextView
    lateinit var etCarModel: EditText
    lateinit var etCarPrice: EditText
    lateinit var etCarSeats: EditText
    lateinit var car_battery_capacity: EditText
    lateinit var car_space_capacity: EditText
    lateinit var car_max_payload: EditText
    lateinit var spCarCategory: Spinner
    var editCar: Boolean = false
    lateinit var car: Car
    private var idCar: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_car_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()

        if (intent.action.equals("edit_car")) {
            supportActionBar?.title = "Edit Car"
            car = intent.getSerializableExtra(EXTRA_CAR) as Car
            editCar = true
            idCar = intent.getLongExtra(EXTRA_ID_CAR, 1)
            loadData(car)
            Log.e(EXTRA_CAR, "long: " + idCar)
        } else {
            supportActionBar?.title = "Add Car"
        }


    }

    private fun loadData(car: Car) {
        etCarModel.setText(car.model.toString())
        etCarPrice.setText(car.price.toString())
        etCarSeats.setText(car.seats.toString())
        tvCarReleased.text = car.dateReleased.toString()
        spCarCategory.setSelection(car.category)

        when (car.state.toString()) {
            "New" -> car_rb_new.isChecked = true
            "Used" -> car_rb_used.isChecked = true
        }

        if (!car.battCapacity.isNullOrEmpty())
            car_battery_capacity.setText(car.battCapacity.toString())

        if (!car.spaceCapacity.isNullOrEmpty())
            car_space_capacity.setText(car.spaceCapacity.toString())

        if (!car.maxPayload.isNullOrEmpty())
            car_max_payload.setText(car.maxPayload.toString())

    }

    private fun initViews() {
        etCarModel = findViewById(R.id.car_et_model)
        etCarPrice = findViewById(R.id.car_et_price)
        etCarSeats = findViewById(R.id.car_et_seats)
        tvCarReleased = findViewById(R.id.car_tv_date_released)
        spCarCategory = findViewById(R.id.sp_category)
        car_battery_capacity = findViewById(R.id.car_battery_capacity)
        car_space_capacity = findViewById(R.id.car_space_capacity)
        car_max_payload = findViewById(R.id.car_max_payload)

        val cont_electric: TextInputLayout = findViewById(R.id.cont_electric)
        val cont_comercial: TextInputLayout = findViewById(R.id.cont_comercial)
        val cont_truck: TextInputLayout = findViewById(R.id.cont_truck)


        Category.findAll(Category::class.java).forEach {
            categoryList.add(it.category)
        }
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categoryList
        )
        spCarCategory.adapter = adapter

        tvCarReleased.setOnClickListener {
            val picker: DialogFragment = DatePickerFragment()
            val args = Bundle()
            args.putString(DatePickerFragment.DATE, date)
            picker.arguments = args
            picker.show(supportFragmentManager, "datePicker")
        }

        spCarCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (categoryList[position]) {
                    "Electric" -> {
                        cont_electric.visibility = View.VISIBLE
                        cont_comercial.visibility = View.GONE
                        cont_truck.visibility = View.GONE
                    }
                    "Commercial" -> {
                        cont_electric.visibility = View.GONE
                        cont_comercial.visibility = View.VISIBLE
                        cont_truck.visibility = View.GONE
                    }
                    "Truck" -> {
                        cont_electric.visibility = View.GONE
                        cont_comercial.visibility = View.GONE
                        cont_truck.visibility = View.VISIBLE
                    }
                    else -> {
                        cont_electric.visibility = View.GONE
                        cont_comercial.visibility = View.GONE
                        cont_truck.visibility = View.GONE
                    }
                }
            }

        }

    }

    fun handleClickSave(view: View) {

        if (!emptyFields()) {
            saveCar()
        } else {
            Toast.makeText(baseContext, R.string.empty_save_data, Toast.LENGTH_SHORT).show()
        }

    }

    private fun saveCar() {

        // Obtain Car state from radioButtons
        val state = if (car_rb_new.isChecked) car_rb_new.text else car_rb_used.text

        if (editCar && idCar != null) {

            var carUpdate = Car.findById(Car::class.java, idCar)
            carUpdate.seats = etCarSeats.text.toString().toInt()
            carUpdate.price = etCarPrice.text.toString().toDouble()
            carUpdate.state = state.toString()
            carUpdate.model = etCarModel.text.toString()
            carUpdate.dateReleased = tvCarReleased.text.toString()
            carUpdate.category = sp_category.selectedItemPosition
            carUpdate.battCapacity = car_battery_capacity.text.toString()
            carUpdate.spaceCapacity = car_space_capacity.text.toString()
            carUpdate.maxPayload = car_max_payload.text.toString()

            carUpdate.save() // updates the previous entry with new values

        } else {
            var car = Car(
                etCarSeats.text.toString().toInt(),
                etCarPrice.text.toString().toDouble(),
                state.toString(),
                etCarModel.text.toString(),
                tvCarReleased.text.toString(),
                sp_category.selectedItemPosition,
                car_battery_capacity.text.toString(),
                car_space_capacity.text.toString(),
                car_max_payload.text.toString()
            )
            car.save()
        }

        Toast.makeText(baseContext, R.string.data_saved, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun emptyFields(): Boolean {
        return etCarSeats.text.toString().isEmpty() ||
                etCarPrice.text.toString().isEmpty() ||
                etCarModel.text.toString().isEmpty()
    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {
        val mes =
            if ((month + 1).toString().length == 1) "0" + (month + 1) else "" + (month + 1)
        val dia = if (day.toString().length == 1) "0$day" else "" + day
        date = "$year-$mes-$dia"
        tvCarReleased.text = "$day/$mes/$year"
        Log.e("dateDialog", "" + date)
    }


}