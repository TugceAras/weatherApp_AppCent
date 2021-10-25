package com.tugcearas.weatherapp

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tugcearas.weatherapp.adapter.LocationAdapter
import com.tugcearas.weatherapp.databinding.FragmentSearchLocationBinding
import com.tugcearas.weatherapp.viewmodels.LocationSearchViewModel
import com.tugcearas.weatherapp.viewmodels.LocationSharedViewModel
import kotlinx.android.synthetic.main.fragment_search_location.*

class SearchLocationFragment : Fragment() {

    private val locationSearchViewModel: LocationSearchViewModel by activityViewModels()
    private val locationSharedViewModel: LocationSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSearchLocationBinding.inflate(inflater, container, false)

        // setup the adapter for search results
        val adapter =
            LocationAdapter(object :
                LocationAdapter.InteractionListener {
                override fun onItemSelected(mwLocationID: Int) {
                    locationSharedViewModel.selectedLocation(mwLocationID)
                    findNavController().navigate(R.id.action_searchLocationFragment_to_locationInfoFragment)
                }
            });

        binding.recentSearchList.adapter = adapter
        locationSearchViewModel.getLocations().observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
        })

        locationSearchViewModel.getProgressBar().observe(viewLifecycleOwner, Observer {
            progress_bar.visibility = when {
                it -> {
                    View.VISIBLE
                }
                else -> {
                    View.INVISIBLE
                }
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_box.doAfterTextChanged { s: Editable? ->
            locationSearchViewModel.search(s.toString())
        }
    }
}
