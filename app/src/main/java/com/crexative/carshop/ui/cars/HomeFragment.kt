package com.crexative.carshop.ui.cars

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crexative.carshop.R
import com.crexative.carshop.data.Car
import com.crexative.carshop.ui.adapters.CarAdapterRecycler
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment(), CarAdapterRecycler.ItemClickListener {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = root.findViewById(R.id.recicler_view)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        // Floating Action Button - Add Car
        root.findViewById<FloatingActionButton>(R.id.fab_add_car).setOnClickListener {
            startActivity(Intent(activity, AddCarActivity::class.java))
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        val cars = ArrayList<Car>()
        Car.findAll(Car::class.java).forEach {
            cars.add(it)
        }
        var adapter = CarAdapterRecycler(cars, this)
        recyclerView.adapter = adapter
    }

    fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    override fun onItemClickListener(data: Car) {
        Log.e(AddCarActivity.EXTRA_CAR, "long: " + data.id)
        val intent = Intent(activity, DetailCarActivity::class.java)
        intent.putExtra(DetailCarActivity.EXTRA_CAR, data)
        intent.putExtra(DetailCarActivity.EXTRA_ID_CAR, data.id)
        startActivity(intent)
    }
}