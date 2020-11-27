package fr.alamary.weatherapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import fr.alamary.weatherapi.domain.CityEntity
import fr.alamary.weatherapp.R
import fr.alamary.weatherapp.adapters.CitiesListAdapter
import fr.alamary.weatherapp.globals.BaseApplication

class CitiesListFragment : Fragment(), CitiesListAdapter.CitiesAdapterClickCallback{

    private lateinit var citiesListAdapter : CitiesListAdapter
    private var citiesList : List<CityEntity> = listOf()
    private lateinit var citiesRecyclerView: RecyclerView
    private lateinit var emptyListLayout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.cities_fragment, container, false)
        setHasOptionsMenu(true)
        initViews(rootView)
        initCitiesAdapter(citiesList)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.navigate_destination_button)
        button?.setOnClickListener {
            findNavController().navigate(R.id.flow_add_city_dest, null)
        }
    }

    private fun initCitiesAdapter(cities : List<CityEntity>){
        if(cities.isNotEmpty()){
            emptyListLayout.visibility = View.GONE
            citiesListAdapter = CitiesListAdapter(cities,BaseApplication.applicationContext(),this)
            citiesRecyclerView.adapter = citiesListAdapter
        }else{
            emptyListLayout.visibility = View.VISIBLE
        }


    }

    fun initViews(view: View){
        citiesRecyclerView = view.findViewById(R.id.cities_recyclerView)
        emptyListLayout = view.findViewById(R.id.empty_content_layout)
    }


    override fun onCityClicked(city: CityEntity) {
        findNavController().navigate(R.id.show_weather_action, null)
    }


}