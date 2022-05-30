package ru.akbirov.weather.app.screens.main.tabs.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.akbirov.weather.R
import ru.akbirov.weather.app.model.weather.entities.ListItem
import ru.akbirov.weather.app.utils.DISPLAY_FORMAT
import ru.akbirov.weather.app.utils.getDate
import ru.akbirov.weather.databinding.ItemDetailsBinding

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {
    class DetailsViewHolder(val binding: ItemDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    var details = emptyList<ListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailsBinding.inflate(inflater, parent, false)

        return DetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val detail = details[position]

        with(holder) {
            binding.timeTV.text = getDate(detail.dt, DISPLAY_FORMAT)
            binding.temperatureTV.text = itemView.context.getString(
                R.string.temperature_detail,
                detail.main.temp,
                detail.weather[0].description
            )
        }
    }

    override fun getItemCount(): Int = details.size
}