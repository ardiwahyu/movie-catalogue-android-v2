package com.example.moviecataloguev2.ui.main.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.moviecataloguev2.R
import com.example.moviecataloguev2.databinding.FragmentMovieBinding
import com.example.moviecataloguev2.ui.main.movie.popular.PopularFragment
import com.example.moviecataloguev2.ui.main.movie.upcomming.UpcommingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changeFragment(PopularFragment())

        binding.tvNowPopular.setOnClickListener {
            changeFragment(PopularFragment())
        }
        binding.tvUpcomming.setOnClickListener {
            changeFragment(UpcommingFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        if (fragment is PopularFragment) {
            binding.dividerUpcomming.visibility = View.GONE
            binding.dividerPopular.visibility = View.VISIBLE
            binding.tvNowPopular.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlack))
            binding.tvUpcomming.setTextColor(ContextCompat.getColor(requireContext(), R.color.defaultColor))
        } else {
            binding.dividerUpcomming.visibility = View.VISIBLE
            binding.dividerPopular.visibility = View.GONE
            binding.tvUpcomming.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlack))
            binding.tvNowPopular.setTextColor(ContextCompat.getColor(requireContext(), R.color.defaultColor))
        }
        childFragmentManager.beginTransaction().apply {
            replace(binding.fcvFragment.id, fragment)
            disallowAddToBackStack()
            commit()
        }
    }
}