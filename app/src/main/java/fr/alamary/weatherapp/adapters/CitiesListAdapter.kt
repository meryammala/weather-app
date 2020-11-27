package fr.alamary.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.alamary.weatherapi.domain.CityEntity
import fr.alamary.weatherapp.R

class CitiesListAdapter(private var list: List<CityEntity>, private var context: Context, private val clickCallback: CitiesAdapterClickCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CitiesListViewHolder(LayoutInflater.from(context).inflate(R.layout.city_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CitiesListViewHolder).bind(list[position],clickCallback)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class CitiesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var cityName = itemView.findViewById<TextView>(R.id.city_name_textView)

        fun bind(city : CityEntity,clickCallback : CitiesAdapterClickCallback){
            cityName.text = city.name
            itemView.setOnClickListener {
                clickCallback.onCityClicked(city)
            }
        }

    }

    interface CitiesAdapterClickCallback {
        fun onCityClicked(city: CityEntity)
    }

}
