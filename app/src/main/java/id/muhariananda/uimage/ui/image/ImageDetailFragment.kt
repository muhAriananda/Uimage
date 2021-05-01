package id.muhariananda.uimage.ui.image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.uimage.R
import id.muhariananda.uimage.data.models.UnsplashPhoto
import id.muhariananda.uimage.databinding.FragmentImageDetailBinding

@AndroidEntryPoint
class ImageDetailFragment : Fragment() {

    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    private val arg by navArgs<ImageDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arg.photo
        populateView(item)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun populateView(item: UnsplashPhoto) {
        binding.apply {

//            Glide.with(this@ImageDetailFragment)
//                .load(item.user.urls.small)
//                .circleCrop()
//                .into(imgProfil)
//
//            Glide.with(this@ImageDetailFragment)
//                .load(item.urls.regular)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .apply(
//                    RequestOptions()
//                        .placeholder(R.color.city_light)
//                        .error(R.drawable.ic_error_image)
//                ).into(imgDetail)


            val uri = Uri.parse(item.user.attributionUrls)
            val intentView = Intent(Intent.ACTION_VIEW, uri)
            tvUrls.paint.isUnderlineText = true
            tvUrls.setOnClickListener {
                context?.startActivity(intentView)
            }

            val sendIntent = Intent().also {
                it.action = Intent.ACTION_SEND
                it.type = "text/plain"
                it.putExtra(Intent.EXTRA_SUBJECT, item.user.subjectTitle)
                it.putExtra(Intent.EXTRA_TEXT, item.shareUrl)
            }
            btnShare.setOnClickListener {
                context?.startActivity(sendIntent)
            }

            tvDetailUsername.text = item.user.username
            tvDescription.text = item.description

            imgDetail.load(item.urls.regular) {
                crossfade(true)
                placeholder(R.color.city_light)
                transformations(
                    RoundedCornersTransformation(
                        bottomLeft = 70f,
                        bottomRight = 70f
                    )
                )
            }

            imgProfil.load(item.user.urls.small) {
                crossfade(true)
                placeholder(R.color.city_light)
                transformations(CircleCropTransformation())
            }
        }
    }

}