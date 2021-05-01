package id.muhariananda.uimage.ui.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.muhariananda.uimage.R
import id.muhariananda.uimage.data.models.UnsplashPhoto
import id.muhariananda.uimage.databinding.ItemRowImageBinding

class ImageAdapter(private val listener: OnClickItemListener) :
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

    inner class ImageViewHolder(private val binding: ItemRowImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    item?.let {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(item: UnsplashPhoto) {

            binding.apply {
                tvUsername.text = item.user.username
                imgThumb.layoutParams.height = item.height / 6

                imgThumb.load(item.urls.regular) {
                    transformations(RoundedCornersTransformation(50f))
                    crossfade(true)
                    placeholder(R.color.city_light)
                    error(R.drawable.ic_error_image)
                }

                imgProfile.load(item.user.urls.small) {
                    placeholder(R.color.city_light)
                    transformations(CircleCropTransformation())
                }

            }

        }
    }

    interface OnClickItemListener {
        fun onItemClick(item: UnsplashPhoto)
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