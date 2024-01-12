package com.example.stockexchangeapp

import MyAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.GsonConverterFactory
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "http://api.nbp.pl/api/"

    private lateinit var itemList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var newItemEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllCurrencies()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        itemList = ArrayList()
        itemList.add("EUR")
        itemList.add("USD")
        itemList.add("GBP")
        adapter = MyAdapter(this, android.R.layout.simple_list_item_1, itemList)

        val currencyList: ListView = findViewById(R.id.currencyList)
        currencyList.adapter = adapter
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

    private fun getAllCurrencies(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbpApi::class.java)


        val httpData = api.getCurrencies()

        val x = httpData.enqueue(object : Callback<List<Currency>?> {
            override fun onResponse(
                call: Call<List<Currency>?>,
                response: Response<List<Currency>?>
            ) {
                val responseBody = response.body()!!

                for (myData in responseBody){

                }
            }

            override fun onFailure(call: Call<List<Currency>?>, t: Throwable) {
                Log.e(TAG, "onFailure: xDDDD", )
            }
        })

    }
}