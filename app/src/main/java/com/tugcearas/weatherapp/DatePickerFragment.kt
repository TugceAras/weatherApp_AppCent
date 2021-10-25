package com.tugcearas.weatherapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.tugcearas.weatherapp.viewmodels.PastLocationInfoViewModel
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val pastLocationInfoViewModel: PastLocationInfoViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker

        val dialog = DatePickerDialog(
            this.requireContext(), this, pastLocationInfoViewModel.cal.get(
                Calendar.YEAR
            ), pastLocationInfoViewModel.cal.get(
                Calendar.MONTH
            ), pastLocationInfoViewModel.cal.get(
                Calendar.DATE
            )
        )

        val fromCal = Calendar.getInstance()
        fromCal.set(Calendar.YEAR, 2014)
        fromCal.set(Calendar.MONTH, 0)
        fromCal.set(Calendar.DATE, 1)

        val toCal = Calendar.getInstance()
        toCal.add(Calendar.DATE, -1)

        dialog.datePicker.minDate = fromCal.timeInMillis
        dialog.datePicker.maxDate = toCal.timeInMillis
        return dialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        pastLocationInfoViewModel.selectedDate(year, month, day)
        dismiss()
    }
}