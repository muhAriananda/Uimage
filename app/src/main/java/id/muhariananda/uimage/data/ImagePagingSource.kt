package id.muhariananda.uimage.data

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
        return try {
            val position = params.key ?: Costant.USPLASH_STARTING_PAGE_INDEX
            val response = apiService.searchPhoto(query, position, params.loadSize)
            val item = response.result

            val prevKey =
                if (position == Costant.USPLASH_STARTING_PAGE_INDEX) null else position - 1
            val nextKey =
                if (item.isEmpty()) null else position + 1

            LoadResult.Page(
                data = item,
                prevKey = prevKey,
                nextKey = nextKey
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