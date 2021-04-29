package id.muhariananda.uimage.ui.image.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.muhariananda.uimage.data.UnsplashRepository
import javax.inject.Inject

@HiltViewModel
class ImageHomeViewModel @Inject constructor(
    private val repository: UnsplashRepository
): ViewModel() {

}