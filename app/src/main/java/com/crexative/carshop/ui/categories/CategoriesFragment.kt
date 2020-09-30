package com.crexative.carshop.ui.categories

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.crexative.carshop.R
import com.crexative.carshop.data.Category
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CategoriesFragment : Fragment() {

    private lateinit var listView: ListView

    // use arrayadapter and define an array
    private lateinit var arrayAdapter: ArrayAdapter<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        /**
         * ListView Actions (Update and delete Categories)
         */
        listView = root.findViewById(R.id.lv_categories)
        listView.setOnItemClickListener { _, _, position, _ ->

            if (position < 3) {
                Toast.makeText(activity, R.string.msg_modify_default_category, Toast.LENGTH_SHORT)
                    .show()
            } else {
                showAlertDialogButtonClicked(
                    "Edit",
                    listView.getItemAtPosition(position).toString()
                )
            }
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            if (position < 3) {
                Toast.makeText(activity, R.string.msg_delete_default_category, Toast.LENGTH_SHORT)
                    .show()
            } else {
                dialogDeleteCategory(listView.getItemAtPosition(position).toString())
            }
            true
        }

        /**
         * FAB Button to add new Category, using alert
         */
        root.findViewById<FloatingActionButton>(R.id.btn_add_category).setOnClickListener {
            showAlertDialogButtonClicked("Save", "")
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        var categoryList = ArrayList<String>()
        Category.findAll(Category::class.java).forEach {
            categoryList.add(it.category)
        }

        arrayAdapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_list_item_1, categoryList
        )
        listView.adapter = arrayAdapter


    }

    private fun showAlertDialogButtonClicked(action: String, item: String) {

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Category")
        // Use the custom Layout EditText Category
        val customLayout: View = layoutInflater.inflate(R.layout.custom_layout, null)
        var editText = customLayout.findViewById<EditText>(R.id.et_popup_category)
        Log.e("Prueba", item)
        editText.setText(item)

        builder.setView(customLayout)
        // add a button
        builder.setPositiveButton(
            action,
            DialogInterface.OnClickListener { dialog, which ->
                saveCategory(editText.text.toString(), action, item)
            })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
        // create and show the alert dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun dialogDeleteCategory(listItem: String) {

        val builder = AlertDialog.Builder(context!!)

        builder.setMessage(getString(R.string.dialog_delete_category) + " " + listItem)
            .setPositiveButton(R.string.dialog_accept,
                DialogInterface.OnClickListener { dialog, id ->
                    deleteTask(listItem)
                })
            .setNegativeButton(R.string.dialog_cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

        builder.create().show()
    }

    private fun deleteTask(listItem: String) {
        val categoryItem = Category.find(Category::class.java, "category = ?", listItem)[0]
        categoryItem.delete()
        onResume()
    }

    private fun saveCategory(etCategory: String, action: String, itemList: String) {

        if (action.trim().toLowerCase() == "save") {
            val categoryItem = Category(etCategory, false)
            categoryItem.save()
        } else {
            val categoryItem = Category.find(Category::class.java, "category = ?", itemList)[0]
            categoryItem.category = etCategory
            categoryItem.save()
        }

        // Refresh data
        onResume()
    }
}