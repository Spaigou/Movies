package com.example.movielisttask.presentation.screens

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movielisttask.MoviesApplication
import com.example.movielisttask.databinding.MoviesFragmentBinding
import com.example.movielisttask.presentation.recycler.MovieListAdapter
import com.example.movielisttask.presentation.viewmodel.MoviesViewModel
import com.example.movielisttask.presentation.viewmodel.MoviesViewModelFactory
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!
    private val moviesViewModel by activityViewModels<MoviesViewModel> {
        val moviesApplication = requireActivity().application as MoviesApplication
        MoviesViewModelFactory(moviesApplication.localMoviesRepository)
    }
//    ИЛИ
//    private val movieViewModel by viewModels<MovieViewModel>(ownerProducer = { requireActivity() })

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
                moviesViewModel.movies.collect { movies ->
                    val favorites = movies.filter { it.isFavorite }
                    movieAdapter.submitList(favorites)
                }
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
        private const val TAG = "FavoritesFragment"
    }
}