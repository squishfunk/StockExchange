package com.example.stockexchangeapp

import MyAdapter
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

const val TAG = "MainActivity666666"

class MainActivity : AppCompatActivity(), SensorEventListener {

    //    private lateinit var newItemEditText: EditText

    private lateinit var adapter: ArrayAdapter<Currency>
    private lateinit var itemList: ArrayList<Currency>

    private lateinit var sensorManager: SensorManager;
    private var lastUpdate: Long = 0;
    private val shakeThreshold = 800;
    private lateinit var gestureDetector: GestureDetector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCurrencyApiCall()
        setUiStuff()
        setCurrencyList()
        setSensorManager()
    }

    private fun setUiStuff() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        itemList = ArrayList()
        adapter = MyAdapter(this, android.R.layout.simple_list_item_1, itemList)
    }

    private fun setSensorManager() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun setCurrencyList() {
        val currencyList: ListView = findViewById(R.id.currencyList)
        currencyList.isClickable = true
        currencyList.adapter = adapter
        currencyList.setOnItemClickListener { parent, view, position, id ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://stooq.pl/q/?s="+itemList[position].symbol.toLowerCase()+"pln")))
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
//        val newItem = newItemEditText.text.toString().trim()
//        if (newItem.isNotEmpty()) {
//            itemList.add(newItem)
//            adapter.notifyDataSetChanged()
//            newItemEditText.text.clear()
//        } else {
//            showToast("Wprowadź nazwę przedmiotu")
//        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCurrencyApiCall(){
        val url = "https://api.nbp.pl/api/exchangerates/tables/A?format=json"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body!!.string()
                var arr = JSONArray(responseText);
                val jsonObject = arr.getJSONObject(0)
                val ratesArray = jsonObject.getJSONArray("rates")
                val ratesList = mutableListOf<Currency>()

                for (i in 0 until ratesArray.length()) {
                    val rateObject = ratesArray.getJSONObject(i)
                    val currency = rateObject.getString("currency")
                    val code = rateObject.getString("code")
                    val mid = rateObject.getDouble("mid")

                    itemList.add(Currency(currency, code, mid))
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()

            if ((currentTime - lastUpdate) > 400) {
                val diffTime = currentTime - lastUpdate
                lastUpdate = currentTime

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                var lastX = 0f;
                var lastY =0f;
                var lastZ =0f;

                val speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000

                if (speed > shakeThreshold) {
                    // Shaking detected, update the quote
                    showToast("Odświeżenie listy")
                    getCurrencyApiCall();
                }

                lastX = x
                lastY = y
                lastZ = z
            }
        }
    }
}