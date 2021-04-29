package id.muhariananda.uimage.ui.image

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.muhariananda.uimage.data.ImageRepository
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val repository: ImageRepository
): ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val images = currentQuery.switchMap { query ->
        repository.getSearchResult(query).cachedIn(viewModelScope)
    }

    fun searchImage(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "art"
    }

}