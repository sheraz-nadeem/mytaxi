package com.mytaxi.sheraz


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * A provider factory that persists ViewModels [ViewModel].
 * Used if the view model has a parameterized constructor.
 */
class ViewModelProviderFactory<V : Any> @Inject constructor(
    private val viewModel: V
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel.javaClass)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
