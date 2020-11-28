package fr.alamary.weatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import fr.alamary.weatherapp.R
import fr.alamary.weatherapp.viewmodels.CitiesViewModel
import fr.alamary.weatherapp.viewmodels.WeatherViewModel

class WeatherFragment : Fragment() {

    lateinit var selectedCity : CityEntity
    lateinit var currentWeather : WeatherEntity
    lateinit var viewModel : WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.weather_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedCity = arguments?.getSerializable("city") as CityEntity
        initViewModel()
        viewModel.getWeather(selectedCity)

        view.findViewById<View>(
            R.id.backButton
        ).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.show_cities_action)
        )
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        viewModel.getWeatherSuccessLiveData.observe(viewLifecycleOwner,
        Observer {
            bindWeather(it)

        })
    }
    fun bindWeather(weatherEntity: WeatherEntity){
        Toast.makeText(activity,weatherEntity.toString(),Toast.LENGTH_LONG).show()
    }

}