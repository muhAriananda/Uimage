package id.muhariananda.uimage.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import id.muhariananda.uimage.data.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getSearchResult(query: String) =
        Pager(
           config = PagingConfig(
               pageSize = 20,
               maxSize = 100,
               enablePlaceholders = false
           ),
            pagingSourceFactory = { ImagePagingSource(apiService, query) }
        ).liveData

}