package fr.alamary.weatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import fr.alamary.weatherapp.R
import fr.alamary.weatherapp.adapters.WeatherListAdapter
import fr.alamary.weatherapp.globals.GlobalUtils
import fr.alamary.weatherapp.viewmodels.CitiesViewModel
import fr.alamary.weatherapp.viewmodels.WeatherViewModel

class WeatherFragment : Fragment() {

    private lateinit var selectedCity: CityEntity
    private lateinit var viewModel: WeatherViewModel
    private lateinit var cityNameTextView: TextView
    private lateinit var weatherDescriptionTextView: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var temperatureTextView: TextView
    private lateinit var todayDateTextView: TextView
    private lateinit var feelsLikeTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var hourlyWeatherRecyclerView: RecyclerView
    private lateinit var dailyWeatherRecyclerView: RecyclerView
    private lateinit var hourlyForecastAdapter: WeatherListAdapter
    private lateinit var dailyForecastAdapter: WeatherListAdapter
    private lateinit var progresContainer: ConstraintLayout

    companion object{
        const val HOURLY = 1
        const val DAILY = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_fragment, container, false)
        setHasOptionsMenu(true)
        selectedCity = arguments?.getSerializable("city") as CityEntity
        initViews(view)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getWeather(selectedCity)

    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        viewModel.getWeatherSuccessLiveData.observe(viewLifecycleOwner,
            Observer {
                hideProgress()
                bindWeather(it)
            })
        viewModel.getWeatherFailureLiveData.observe(viewLifecycleOwner,{
            hideProgress()
        })
    }

    fun getWeather(cityEntity: CityEntity) {
        viewModel.getWeather(cityEntity)
        showProgress()
    }

    fun initViews(view: View) {
        cityNameTextView = view.findViewById(R.id.city_name_textView)
        weatherDescriptionTextView = view.findViewById(R.id.weather_description_textView)
        weatherIcon = view.findViewById(R.id.weather_icon)
        temperatureTextView = view.findViewById(R.id.weather_temp_textView)
        todayDateTextView = view.findViewById(R.id.current_date_textView)
        hourlyWeatherRecyclerView = view.findViewById(R.id.hourly_weather_recyclerView)
        dailyWeatherRecyclerView = view.findViewById(R.id.daily_weather_recyclerView)
        feelsLikeTextView = view.findViewById(R.id.feels_like_textView)
        humidityTextView = view.findViewById(R.id.humidity_textView)
        progresContainer = view.findViewById(R.id.progresContainer)
    }

    fun bindWeather(weatherEntity: WeatherEntity) {
        cityNameTextView.text = selectedCity.name!!.capitalize()
        weatherDescriptionTextView.text = weatherEntity.description.capitalize()
        weatherIcon.setImageDrawable(
            GlobalUtils.getDrawableFromString(
                requireActivity(),
                "weather_${weatherEntity.icon}"
            )
        )
        temperatureTextView.text =
            resources.getString(R.string.temp_value, weatherEntity.temp.toString())
        humidityTextView.text =
            resources.getString(R.string.humidity_label, weatherEntity.humidity.toString()) + "%"
        feelsLikeTextView.text =
            resources.getString(R.string.feels_like_label, weatherEntity.feelsLike.toString())

        dailyForecastAdapter = WeatherListAdapter(weatherEntity.daily, requireActivity(), DAILY)
        hourlyForecastAdapter = WeatherListAdapter(weatherEntity.hourly, requireActivity(), HOURLY)

        hourlyWeatherRecyclerView.adapter = hourlyForecastAdapter
        dailyWeatherRecyclerView.adapter = dailyForecastAdapter

    }
    fun showProgress(){
        progresContainer.visibility = View.VISIBLE
    }
    fun hideProgress(){
        progresContainer.visibility = View.GONE
    }

}