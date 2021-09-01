package com.example.moviecataloguev2.ui.main.movie.upcomming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecataloguev2.data.remote.model.Movie
import com.example.moviecataloguev2.databinding.FragmentUpcommingBinding
import com.example.moviecataloguev2.ui.main.OnRecyclerViewScrolled
import com.example.moviecataloguev2.ui.main.movie.ListMovieAdapter
import com.example.moviecataloguev2.utils.recyclerview.RecyclerViewScroll
import com.example.moviecataloguev2.utils.shimmerLoading.ShimmerLoading.start
import com.example.moviecataloguev2.utils.shimmerLoading.ShimmerLoading.stop
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpcommingFragment : Fragment() {
    private lateinit var binding: FragmentUpcommingBinding
    private val viewModel by viewModels<UpcommingViewModel>()
    @Inject lateinit var listMovieAdapter: ListMovieAdapter
    private var isLoading = false
    private val listMovie = arrayListOf<Movie?>()
    private var maxPage = 1
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcommingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObserver()
        viewModel.getUpcomming("en-US", 1)

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

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listMovie.size-1) {
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        if (currentPage < maxPage) {
            listMovie.add(null)
            listMovieAdapter.notifyItemInserted(listMovie.size - 1)

            viewModel.getUpcomming("en-US", ++currentPage)
        }
    }

    private fun initObserver() {
        viewModel.results.observe(requireActivity(), {
            if (!isLoading) {
                listMovie.addAll(it.result)
                listMovieAdapter.submitList(listMovie)
            } else {
                listMovie.removeAt(listMovie.size-1)
                val scrollPosition = listMovie.size
                listMovieAdapter.notifyItemRemoved(scrollPosition)
                listMovie.addAll(it.result)
                isLoading = false
            }
            maxPage = it.totalPages
            currentPage = it.page
        })
        viewModel.loading.observe(requireActivity(), {
            if (!isLoading) {
                if (it) binding.sflLoading.start() else binding.sflLoading.stop()
            }
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