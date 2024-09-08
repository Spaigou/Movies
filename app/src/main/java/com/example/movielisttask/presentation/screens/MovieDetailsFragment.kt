package com.example.movielisttask.presentation.screens

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
import coil.load
import com.example.movielisttask.MoviesApplication
import com.example.movielisttask.presentation.viewmodel.MoviesViewModel
import com.example.movielisttask.R
import com.example.movielisttask.databinding.MovieDetailsFragmentBinding
import com.example.movielisttask.presentation.viewmodel.MoviesViewModelFactory
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment() {
    private var _binding: MovieDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val moviesViewModel by activityViewModels<MoviesViewModel> {
        val moviesApplication = requireActivity().application as MoviesApplication
        MoviesViewModelFactory(
            moviesApplication.localMoviesRepository,
            moviesApplication.moviesRepository,
        )
    }
//    ИЛИ
//    private val movieViewModel by viewModels<MovieViewModel>(ownerProducer = { requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // подписка на изменения в выбранном фильме
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.selectedMovie.collect { movie ->
                    if (movie != null) {
                        binding.moviePoster.load(movie.posterUrl) {
                            placeholder(R.drawable.ic_launcher_foreground)
                            error(R.drawable.ic_launcher_background)
                        }
                        binding.movieTitle.text = movie.nameRu
                        binding.movieDescription.text = movie.description
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView")
    }

    companion object {
        private const val TAG = "MovieDetailsFragment"
    }
}
