package id.muhariananda.uimage.ui.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.muhariananda.uimage.databinding.FragmentImageSearchBinding

@AndroidEntryPoint
class ImageSearchFragment : Fragment() {

    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ImageSearchViewModel>()
    private val adapter by lazy { ImageAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerview()
        loadStateHandle()
        searchResults()

        viewModel.images.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupToolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        }
    }

    private fun setupRecyclerview() {
        binding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )

            recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ImageStateLoadAdapter { adapter.retry() },
                footer = ImageStateLoadAdapter { adapter.retry() },
            )

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }
    }

    private fun loadStateHandle() {
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                recyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                imgError.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error

                //empty list results
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerview.isVisible = true
                }
            }
        }
    }

    private fun searchResults() {
        binding.searchView.apply {
            this.isFocusable = true
            this.isIconified = false
            this.requestFocusFromTouch()

            if (query.isEmpty()) {
                this.setIconifiedByDefault(false)
            } else {
                this.setIconifiedByDefault(true)
            }

            this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        binding.recyclerview.scrollToPosition(0)
                        viewModel.searchImage(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
            this.clearFocus()
        }
    }
}