package com.example.moviecataloguev2.ui.main.movie.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecataloguev2.R
import com.example.moviecataloguev2.databinding.FragmentPopularBinding
import com.example.moviecataloguev2.ui.main.OnRecyclerViewScrolled
import com.example.moviecataloguev2.ui.main.movie.ListGenreAdapter
import com.example.moviecataloguev2.ui.main.movie.ListMovieAdapter
import com.example.moviecataloguev2.utils.recyclerview.RecyclerViewScroll
import com.example.moviecataloguev2.utils.shimmerLoading.ShimmerLoading.start
import com.example.moviecataloguev2.utils.shimmerLoading.ShimmerLoading.stop
import com.stone.vega.library.VegaLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularFragment : Fragment() {
    private lateinit var binding: FragmentPopularBinding
    private val viewModel by viewModels<PopularViewModel>()
    @Inject lateinit var listMovieAdapter: ListMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObserver()
        viewModel.getPopular("en-US", 1)

        val listener = requireActivity() as OnRecyclerViewScrolled
        listener.show()
        binding.rvListMovie.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvListMovie.adapter = listMovieAdapter
        binding.rvListMovie.addOnScrollListener(object : RecyclerViewScroll() {
            override fun hide() {
                listener.hide()
            }

            override fun show() {
                listener.show()
            }
        })
    }

    private fun initObserver() {
        viewModel.results.observe(requireActivity(), {
            listMovieAdapter.submitList(it.result)
        })
        viewModel.loading.observe(requireActivity(), {
            if (it) binding.sflLoading.start() else binding.sflLoading.stop()
        })
        viewModel.error.observe(requireActivity(), {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onPause() {
        super.onPause()
        binding.sflLoading.stop()
    }
}