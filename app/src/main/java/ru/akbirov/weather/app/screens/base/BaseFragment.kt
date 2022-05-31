package ru.akbirov.weather.app.screens.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import ru.akbirov.weather.R
import ru.akbirov.weather.app.utils.observeEvent


abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showErrorMessageEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.showSuccessMessageEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    protected fun getNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.default_fade_in)
            .setExitAnim(R.anim.default_fade_out)
            .setPopEnterAnim(R.anim.default_fade_in)
            .setPopExitAnim(R.anim.default_fade_out)
            .build()
    }
}