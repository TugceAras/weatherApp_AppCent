package com.tugcearas.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tugcearas.weatherapp.backend.data.MWLocation
import com.tugcearas.weatherapp.databinding.ViewLocationBinding

class LocationAdapter(private val listener: InteractionListener) :
    ListAdapter<MWLocation, RecyclerView.ViewHolder>(LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LocationViewHolder(
            listener,
            ViewLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LocationViewHolder).bind(getItem(position))
    }

    class LocationViewHolder(
        private val listener: InteractionListener,
        private val binding: ViewLocationBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.mwLocation?.let { mwLocation ->
                    listener.onItemSelected(mwLocation.woeid)
                }
            }
        }

        fun bind(item: MWLocation) {
            binding.apply {
                mwLocation = item
                executePendingBindings()
            }
        }
    }

    private class LocationDiffCallback : DiffUtil.ItemCallback<MWLocation>() {
        override fun areItemsTheSame(oldItem: MWLocation, newItem: MWLocation): Boolean {
            return oldItem.woeid == newItem.woeid
        }

        override fun areContentsTheSame(oldItem: MWLocation, newItem: MWLocation): Boolean {
            return oldItem == newItem
        }
    }

    interface InteractionListener {
        fun onItemSelected(mwLocationID: Int)
    }
}