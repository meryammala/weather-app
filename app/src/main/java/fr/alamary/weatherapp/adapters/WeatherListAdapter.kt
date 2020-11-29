package fr.alamary.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.alamary.weatherapi.domain.entities.ForecastEntity
import fr.alamary.weatherapp.R
import fr.alamary.weatherapp.globals.DateTimeUtils
import fr.alamary.weatherapp.globals.DateTimeUtils.Companion.fromUnixToDate
import fr.alamary.weatherapp.globals.GlobalUtils


class WeatherListAdapter(
    private var list: List<ForecastEntity>,
    private var context: Context,
    val type: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HOURLY = 1
        const val TYPE_DAILY = 2
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            TYPE_DAILY -> DailyListViewHolder(
                LayoutInflater.from(context).inflate(R.layout.daily_weather_layout, parent, false)
            )
            TYPE_HOURLY -> HourlyListViewHolder(
                LayoutInflater.from(context).inflate(R.layout.hourly_weather_layout, parent, false)
            )
            else ->
                HourlyListViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.hourly_weather_layout, parent, false)
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HourlyListViewHolder -> holder.bind(list[position], context)
            is DailyListViewHolder -> holder.bind(list[position], context,position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HourlyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var timeText = itemView.findViewById<TextView>(R.id.hour_textView)
        private var weatherIcon = itemView.findViewById<ImageView>(R.id.weather_icon)
        private var tempTextView = itemView.findViewById<TextView>(R.id.temp_textView)

        fun bind(forecastEntity: ForecastEntity, context: Context) {
            var date = fromUnixToDate(forecastEntity.dt.toLong())
            timeText.text = DateTimeUtils.toHour(date)
            weatherIcon.setImageDrawable(
                GlobalUtils.getDrawableFromString(
                    context,
                    "weather_${forecastEntity.icon}"
                )
            )
            tempTextView.text = context.resources.getString(R.string.temp_value,forecastEntity.temp.toString())
        }

    }

    class DailyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var day_textView = itemView.findViewById<TextView>(R.id.day_textView)
        private var weatherIcon = itemView.findViewById<ImageView>(R.id.weather_icon)
        private var tempTextView = itemView.findViewById<TextView>(R.id.temp_textView)

        fun bind(forecastEntity: ForecastEntity, context: Context, position: Int) {
            if (position == 0) {
                day_textView.text = context.resources.getString(R.string.tomorrow_label)
            } else {
                day_textView.text =
                    DateTimeUtils.toDayMonth(fromUnixToDate(forecastEntity.dt.toLong()))

            }
            weatherIcon.setImageDrawable(
                GlobalUtils.getDrawableFromString(
                    context,
                    "weather_${forecastEntity.icon}"
                )
            )
            tempTextView.text = context.resources.getString(
                R.string.temp_value,
                "${forecastEntity.tempMin}/${forecastEntity.tempMax}"
            )
        }

    }


}
