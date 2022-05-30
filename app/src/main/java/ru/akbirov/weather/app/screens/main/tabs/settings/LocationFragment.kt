package ru.akbirov.weather.app.screens.main.tabs.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import ru.akbirov.weather.R
import ru.akbirov.weather.databinding.FragmentLocationBinding

class LocationFragment : Fragment(R.layout.fragment_location) {

    private lateinit var binding: FragmentLocationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLocationBinding.bind(view)

        setupStartTab()

        binding.tabsLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                onTabSelect(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}

        })
    }

    private fun setupStartTab() {
        binding.tabsLayout.getTabAt(0)?.let {
            onTabSelect(it)
        }
    }

    private fun onTabSelect(tab: TabLayout.Tab) {
        if (tab.text.toString() == resources.getString(R.string.search)) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.tabsFragmentContainer, SearchFragment()).commit()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(R.id.tabsFragmentContainer, MapFragment()).commit()
        }
    }
}