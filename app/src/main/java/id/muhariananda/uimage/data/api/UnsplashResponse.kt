package id.muhariananda.uimage.data.api

import id.muhariananda.uimage.data.models.UnsplashPhoto

data class UnsplashResponse(
    val results : List<UnsplashPhoto>
)