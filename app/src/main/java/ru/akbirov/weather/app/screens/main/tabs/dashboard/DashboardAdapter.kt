package ru.akbirov.weather.app.screens.main.tabs.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.akbirov.weather.R
import ru.akbirov.weather.app.model.weather.entities.ListItem
import ru.akbirov.weather.app.utils.DISPLAY_FORMAT
import ru.akbirov.weather.app.utils.getDate
import ru.akbirov.weather.databinding.ItemDetailsVerticalBinding

class DashboardAdapter() : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(val binding: ItemDetailsVerticalBinding) :
        RecyclerView.ViewHolder(binding.root)

    var details: List<ListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = details.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailsVerticalBinding.inflate(inflater, parent, false)

        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val detail = details[position]

        with(holder) {
            binding.dateTV.text = getDate(detail.dt, DISPLAY_FORMAT)
            binding.temperatureTV.text =
                itemView.context.getString(R.string.temperature, detail.main.temp)
            binding.descriptionTV.text = detail.weather[0].description
        }
    }
}