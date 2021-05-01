package id.muhariananda.uimage.ui.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.muhariananda.uimage.R
import id.muhariananda.uimage.data.models.UnsplashPhoto
import id.muhariananda.uimage.databinding.ItemRowImageBinding

class ImageAdapter :
    PagingDataAdapter<UnsplashPhoto, ImageAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemRowImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { item ->
            holder.bind(item)
        }
    }

    class ImageViewHolder(private val binding: ItemRowImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UnsplashPhoto) {

            binding.apply {
                tvUsername.text = item.user.username
                imgThumb.layoutParams.height = item.height / 6

                Glide.with(itemView)
                    .load(item.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(
                        RequestOptions()
                            .placeholder(R.color.city_light)
                            .error(R.drawable.ic_error_image)
                    )
                    .into(imgThumb)

                Glide.with(itemView)
                    .load(item.user.urls.small)
                    .circleCrop()
                    .into(imgProfile)

            }

        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}