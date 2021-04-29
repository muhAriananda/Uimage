package id.muhariananda.uimage.data

import id.muhariananda.uimage.data.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val unsplashApi: UnsplashApi
) {

}