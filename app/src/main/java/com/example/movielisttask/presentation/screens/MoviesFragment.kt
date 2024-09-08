package com.example.movielisttask.presentation.screens

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movielisttask.MoviesApplication
import com.example.movielisttask.R
import com.example.movielisttask.presentation.viewmodel.MoviesViewModel
import com.example.movielisttask.databinding.MoviesFragmentBinding
import com.example.movielisttask.presentation.recycler.MovieListAdapter
import com.example.movielisttask.presentation.viewmodel.MoviesViewModelFactory
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch


class MoviesFragment : Fragment() {
    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!
    private val moviesViewModel by activityViewModels<MoviesViewModel> {
        val moviesApplication = requireActivity().application as MoviesApplication
        MoviesViewModelFactory(
            moviesApplication.localMoviesRepository,
            moviesApplication.moviesRepository,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieAdapter = MovieListAdapter { moviesViewModel.onMovieClicked(it.kinopoiskId) }

        binding.recyclerView.apply {
            layoutManager = determineLayoutManager()
            adapter = movieAdapter
        }

        // следить за изменениями
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.displayMovies.collect { movies ->
                    movieAdapter.submitList(movies)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.genres.collect { genres ->
                    val arrayAdapter = ArrayAdapter(
                        requireContext(),
                        R.layout.genre_item,
                        genres.map {
                            it.genre.replaceFirstChar { letter ->
                                letter.uppercaseChar()
                            }
                        }
                    )
                    binding.genreSpinner.adapter = arrayAdapter
                }
            }
        }

        binding.genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                moviesViewModel.onGenreSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                moviesViewModel.onNoneGenreSelected()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView")
    }

    private fun determineLayoutManager(): RecyclerView.LayoutManager {
        return if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(requireContext())
        else StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    }

    companion object {
        private const val TAG = "MoviesFragment"
    }
}