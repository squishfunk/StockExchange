import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stockexchangeapp.R

class MyAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val topText = view.findViewById<TextView>(R.id.currencyName)
        val bottomText = view.findViewById<TextView>(R.id.currencySymbol)

        val item = getItem(position)
        topText.text = "Góra $item"
        bottomText.text = "Dół $item"

        return view
    }
}