package com.tugcearas.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.tugcearas.weatherapp.databinding.FragmentPastLocationInfoBinding
import com.tugcearas.weatherapp.viewmodels.LocationInfoViewModel
import com.tugcearas.weatherapp.viewmodels.PastLocationInfoViewModel
import kotlinx.android.synthetic.main.fragment_past_location_info.*

class PastLocationInfoFragment : Fragment() {

    private val locationInfoViewModel: LocationInfoViewModel by activityViewModels()
    private val pastLocationInfoViewModel: PastLocationInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPastLocationInfoBinding.inflate(inflater, container, false)

        pastLocationInfoViewModel.getPastLocationInfo()
            .observe(viewLifecycleOwner, Observer { mwWeatherList ->
                binding.apply {
                    binding.mwWeather = mwWeatherList[0]
                    executePendingBindings()
                }
            })

        locationInfoViewModel.getLocationInfo()
            .observe(viewLifecycleOwner, Observer { mwLocationInfo ->

                pastLocationInfoViewModel.getPreviousDayWeather(
                    mwLocationInfo.woeid, mwLocationInfo.consolidated_weather[0].applicable_date
                )
                binding.apply {
                    mwLocationInfo.copy()
                    binding.title = mwLocationInfo.title
                    binding.parent = mwLocationInfo.parent.title
                    executePendingBindings()
                }

            })

        pastLocationInfoViewModel.getProgressBar().observe(viewLifecycleOwner, Observer {
            progress_bar.visibility = when {
                it -> {
                    View.VISIBLE
                }
                else -> {
                    View.INVISIBLE
                }
            }
        })

        binding.minMax.dateMore.setOnClickListener{
            showDatePicker()
        }
        binding.minMax.date.setOnClickListener {
            showDatePicker()
        }
        return binding.root
    }

    private fun showDatePicker() {
        val newFragment = DatePickerFragment()
        newFragment.show(parentFragmentManager, "datePicker")
    }
}
