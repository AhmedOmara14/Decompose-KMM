package org.omaradev.kmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.omaradev.kmp.data.model.Picsum

class HomeViewModel : ViewModel() {
    private var _imagesStatus = MutableStateFlow<List<Picsum>>(emptyList())
    val imagesStatus = _imagesStatus.asStateFlow()

    private var homeRepository = HomeRepository()

    init {
        viewModelScope.launch {
            homeRepository.getImages().collect { images ->
                _imagesStatus.update {
                    it + images
                }
            }
        }
    }
}