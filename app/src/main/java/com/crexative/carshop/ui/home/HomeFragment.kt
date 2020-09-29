package com.crexative.carshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crexative.carshop.R
import com.crexative.carshop.data.Car
import com.crexative.carshop.ui.adapters.CarAdapterRecycler

class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView : RecyclerView = root.findViewById(R.id.recicler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val cars = ArrayList<Car>()
        cars.add(Car(1, 2, 13000.0, "New", "2020", "20/10/2020", 1))
        cars.add(Car(2, 4, 13000.0, "Used", "2020", "20/10/2020", 1))
        cars.add(Car(3, 5, 13000.0, "New", "2020", "20/10/2020", 1))
        cars.add(Car(4, 5, 13000.0, "New", "2020", "20/10/2020", 1))
        cars.add(Car(5, 30, 13000.0, "Used", "2020", "20/10/2020", 1))
        cars.add(Car(6, 12, 13000.0, "New", "2020", "20/10/2020", 1))
        cars.add(Car(7, 20, 13000.0, "New", "2020", "20/10/2020", 1))

        val adapter = CarAdapterRecycler(cars)
        recyclerView.adapter = adapter

        return root
    }
}