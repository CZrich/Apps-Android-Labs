package com.plataformas.lab07

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class BuildingAdapter(
    private var buildingsList: List<Building>,
    private val onItemClick: (Building) -> Unit = {}
) : RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder>(), Filterable {

    private var filteredList: MutableList<Building> = buildingsList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_building, parent, false)
        return BuildingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun updateList(newList: List<Building>) {
        buildingsList = newList
        filteredList = newList.toMutableList()
        notifyDataSetChanged()
    }

    inner class BuildingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.ivBuilding)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        fun bind(building: Building) {
            imageView.setImageResource(building.imageRes)
            tvTitle.text = building.title
            tvCategory.text = building.category
            tvDescription.text = building.description
            itemView.setOnClickListener { onItemClick(building) }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.values = buildingsList
                    filterResults.count = buildingsList.size
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.getDefault())
                    val filtered = buildingsList.filter { it.title.lowercase(Locale.getDefault()).contains(filterPattern) }
                    filterResults.values = filtered
                    filterResults.count = filtered.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = if (results?.values is MutableList<*>) {
                    results.values as MutableList<Building>
                } else {
                    mutableListOf()
                }
                notifyDataSetChanged()
            }
        }
    }
}