package com.crexative.carshop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.crexative.carshop.data.Category
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orm.SugarRecord

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_categories
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // CreateDefault Data
        setUpCategoriesDefault()
    }

    private fun setUpCategoriesDefault() {
        val sizeCategories: Long = Category.count<Category>(Category::class.java)
        Log.e(TAG, "Size Categories " + sizeCategories)

        if (sizeCategories < 3) {
            // Created default categories using Bulk Insert
            var categories = ArrayList<Category>()
            categories.add(Category("Electric", true))
            categories.add(Category("Commercial", true))
            categories.add(Category("Truck", true))
            SugarRecord.saveInTx(categories)
        }
    }
}