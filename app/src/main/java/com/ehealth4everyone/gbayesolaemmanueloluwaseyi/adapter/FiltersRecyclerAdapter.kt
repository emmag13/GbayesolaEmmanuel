package com.ehealth4everyone.gbayesolaemmanueloluwaseyi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.CarOwnersActivity
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.R
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.interfaces.ItemClickListener
import com.ehealth4everyone.restapi.models.FilterLists
import java.util.*

class FiltersRecyclerAdapter(private val context: Context, filterLists: List<FilterLists>?) :
    RecyclerView.Adapter<FiltersViewHolder>() {
    private var filterLists: List<FilterLists>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.filters_item, parent, false)
        return FiltersViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {

        //bind the data to the view:
        if (filterLists != null) {
            if (filterLists!![position].gender?.isEmpty() == true) {
                holder.gender.setText(R.string.no_gender)
            } else {
                holder.gender.text = filterLists!![position].gender?.capitalize(Locale.ROOT)
            }

            if (filterLists!![position].startYear.toString()
                    .isEmpty() || filterLists!![position].endYear.toString().isEmpty()
            ) {
                holder.dateRange.setText(R.string.no_date)
            } else {
                holder.dateRange.text = "Date Range:" + " " +
                        filterLists!![position].startYear.toString() + " - " + filterLists!![position].endYear.toString()
            }

            if (filterLists!![position].countries?.isEmpty() == true) {
                holder.countries.setText(R.string.no_country)
            } else {
                holder.countries.text =
                    filterLists!![position].countries.toString().replace("[", "").replace("]", "")

            }

            if (filterLists!![position].colors?.isEmpty() == true) {
                holder.colors.setText(R.string.no_color)
            } else {
                holder.colors.text =
                    filterLists!![position].colors.toString().replace("[", "").replace("]", "")
            }

            holder.setItemClickListener(object : ItemClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(context, CarOwnersActivity::class.java)
                    intent.putExtra(CarOwnersActivity.INTENT_GENDER, filterLists!![position].gender)
                    intent.putStringArrayListExtra(
                        CarOwnersActivity.INTENT_COUNTRIES,
                        filterLists!![position].countries
                    )
                    intent.putStringArrayListExtra(
                        CarOwnersActivity.INTENT_COLORS,
                        filterLists!![position].colors
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return filterLists!!.size
    }

    init {
        if (filterLists != null) {
            this.filterLists = filterLists
        }
    }
}
