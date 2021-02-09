package com.ehealth4everyone.gbayesolaemmanueloluwaseyi.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.R
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.interfaces.ItemClickListener

class FiltersViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var dateRange: TextView = itemView.findViewById(R.id.dateRange)
    var gender: TextView = itemView.findViewById(R.id.gender)
    var countries: TextView = itemView.findViewById(R.id.countries)
    var colors: TextView = itemView.findViewById(R.id.color)


    private var itemClickListener: ItemClickListener? = null
    fun setItemClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(view: View) {
        itemClickListener!!.onClick(view)
    }

    init {
        itemView.setOnClickListener(this)
    }
}