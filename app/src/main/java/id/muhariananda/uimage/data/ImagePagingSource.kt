package id.muhariananda.uimage.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.muhariananda.uimage.data.api.ApiService
import id.muhariananda.uimage.data.models.UnsplashPhoto
import id.muhariananda.uimage.utils.Costant
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: Costant.USPLASH_STARTING_PAGE_INDEX
        return try {
            val response = apiService.searchPhoto(query, position, params.loadSize)
            val images = response.results
            Log.d("TAG", images.toString())

            LoadResult.Page(
                data = images,
                prevKey = if (position == Costant.USPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (images.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }

}