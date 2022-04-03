package com.tech.mcity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tech.mcity.R
import com.tech.mcity.data.model.City

class CityAdapter():
    PagingDataAdapter<City, CityAdapter.CityViewHolder>(CityComparator){

        class CityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

            val cityTextView: TextView = itemView.findViewById(R.id.cityTextView)
            val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)

        }


       object CityComparator : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }



    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        holder.cityTextView.text = getItem(position)!!.name
        holder.countryTextView.text = getItem(position)!!.country

        holder.itemView.setOnClickListener {

            onItemClickListener?.let {

                it(getItem(position)!!)

            }
        }

    }

    private var onItemClickListener: ((City) -> Unit)? = null

    fun setOnItemClickListener(listener: (City) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.city_layout,
                parent,
                false
            )
        )
    }


}