import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stockexchangeapp.Currency
import com.example.stockexchangeapp.R

class MyAdapter(context: Context, resource: Int, objects: ArrayList<Currency>) :
    ArrayAdapter<Currency>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val currencyName = view.findViewById<TextView>(R.id.currencyName)
        val currencySymbol = view.findViewById<TextView>(R.id.currencySymbol)
        val currencyPrice = view.findViewById<TextView>(R.id.currencyPrice)

        val currency = getItem(position)
        if (currency != null) {
            currencyName.text = currency.name
            currencySymbol.text = currency.symbol
            currencyPrice.text = currency.price.toString() + " PLN"
        }

        return view
    }
}