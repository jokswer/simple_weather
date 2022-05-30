package ru.akbirov.weather.app.screens.main.tabs.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import ru.akbirov.weather.R
import ru.akbirov.weather.app.model.geocode.entities.City
import ru.akbirov.weather.databinding.ItemCityBinding

typealias OnCityClick = (city: City) -> Unit

class SearchAdapter(private val onCityClick: OnCityClick) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(), View.OnClickListener {
    class SearchViewHolder(
        val binding: ItemCityBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    var cities = emptyList<City>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var selectedItemPosition: Int? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val city = cities[position]

        holder.itemView.tag = city
        holder.itemView.id = position

        holder.binding.nameTV.text =
            holder.itemView.context.getString(
                R.string.search_city,
                city.name,
                city.country,
                city.state
            )

        with(holder.itemView) {
            if (selectedItemPosition != null && selectedItemPosition == position) {
                setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.primaryColor,
                        null
                    )
                )
            } else {
                setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.transparent,
                        null
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = cities.size

    override fun onClick(view: View) {
        selectedItemPosition = view.id
        onCityClick(view.tag as City)
    }
}