package com.example.stockexchangeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class CurrencyActivity : AppCompatActivity() {

    private lateinit var itemList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var newItemEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        itemList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)

        val currencyList: ListView = findViewById(R.id.currencyList)
        currencyList.adapter = adapter

        newItemEditText = findViewById(R.id.newItemEditText)

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            addItem()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_wallet -> {
                // Obsługa kliknięcia "Mój portfel"
                showToast("Mój portfel")
                return true
            }
            R.id.menu_currency -> {
                // Obsługa kliknięcia "Kursy Waluty"
                showToast("Kursy Waluty")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun addItem() {
        val newItem = newItemEditText.text.toString().trim()
        if (newItem.isNotEmpty()) {
            itemList.add(newItem)
            adapter.notifyDataSetChanged()
            newItemEditText.text.clear()
        } else {
            showToast("Wprowadź nazwę przedmiotu")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}