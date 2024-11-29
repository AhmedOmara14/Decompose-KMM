package org.omaradev.kmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.omaradev.kmp.data.model.Product

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private var _imagesStatus = MutableStateFlow<List<Product>>(emptyList())
    val imagesStatus = _imagesStatus.asStateFlow()

    init {
        viewModelScope.launch {
            homeRepository.getImages().collect { images ->
                _imagesStatus.update {
                    it + images.getAllProducts()
                }
            }
        }
    }
}
