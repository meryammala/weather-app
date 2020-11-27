package fr.alamary.weatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapp.R
import fr.alamary.weatherapp.globals.ConnectionInternetLiveData
import fr.alamary.weatherapp.viewmodels.CitiesViewModel

class AddCityFragment : Fragment() {
    lateinit var viewModel: CitiesViewModel
    lateinit var cityNameEditText: TextInputEditText
    lateinit var addCityButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)


        return inflater.inflate(R.layout.add_city_fragment, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initViewModel()
        addCityButton.setOnClickListener{
            viewModel.addCity(cityNameEditText.text.toString())
        }


    }

    fun initViews(view: View) {
        addCityButton = view.findViewById<Button>(R.id.addCityActionButton)
        cityNameEditText = view.findViewById<TextInputEditText>(R.id.text_input_edit_text_city)

    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CitiesViewModel::class.java)

        viewModel.addCityFailureLiveData.observe(viewLifecycleOwner, addCityFailureObserver())
        viewModel.addCitySuccessLiveData.observe(viewLifecycleOwner, addCitySuccessObserver())
    }

    fun addCitySuccessObserver(): Observer<CityEntity> {
        return Observer {
            Toast.makeText(activity, "${it.name} has been added", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.show_cities_action, null)
        }
    }

    fun addCityFailureObserver(): Observer<Throwable> {
        return Observer {
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()

        }
    }
}