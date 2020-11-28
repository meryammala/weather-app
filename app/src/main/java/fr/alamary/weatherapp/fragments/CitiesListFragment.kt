package fr.alamary.weatherapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapp.R
import fr.alamary.weatherapp.adapters.CitiesListAdapter
import fr.alamary.weatherapp.globals.BaseApplication
import fr.alamary.weatherapp.viewmodels.CitiesViewModel

class CitiesListFragment : Fragment(), CitiesListAdapter.CitiesAdapterClickCallback{

    private lateinit var citiesListAdapter : CitiesListAdapter
    private var citiesList : ArrayList<CityEntity> = arrayListOf()
    private lateinit var citiesRecyclerView: RecyclerView
    private lateinit var emptyListLayout: View
    private lateinit var viewModel : CitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.cities_fragment, container, false)
        setHasOptionsMenu(true)
        initViews(rootView)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.navigate_destination_button)
        button?.setOnClickListener {
            findNavController().navigate(R.id.flow_add_city_dest, null)
        }
        initViewModel()
        viewModel.getCities()
        initCitiesAdapter(citiesList)
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


    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CitiesViewModel::class.java)

        viewModel.getCitiesLiveData.observe(viewLifecycleOwner, Observer {
            citiesList = arrayListOf()
            citiesList.addAll(it)
            initCitiesAdapter(it)
        })
    }

    override fun onCityClicked(city: CityEntity) {
        val bundle = bundleOf("city" to city)
        findNavController().navigate(R.id.show_weather_action, bundle)
    }


}