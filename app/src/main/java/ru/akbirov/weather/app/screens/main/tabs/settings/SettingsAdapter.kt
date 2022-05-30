package ru.akbirov.weather.app.screens.main.tabs.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import ru.akbirov.weather.app.model.common.Setting
import ru.akbirov.weather.databinding.ItemSettingsBinding

class SettingsAdapter(
    private val settings: List<Setting>,
) : BaseAdapter() {
    override fun getCount(): Int = settings.size

    override fun getItem(position: Int) = settings[position]

    override fun getItemId(position: Int): Long = settings[position].id

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val binding = view?.tag as ItemSettingsBinding? ?: createBinding(parent.context)

        val setting = getItem(position)

        binding.nameTV.text = setting.name

        return binding.root
    }

    private fun createBinding(context: Context): ItemSettingsBinding {
        val binding = ItemSettingsBinding.inflate(LayoutInflater.from(context))
        binding.root.tag = binding

        return binding
    }
}