package com.example.movielisttask.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.example.movielisttask.MoviesApplication
import com.example.movielisttask.R
import com.example.movielisttask.databinding.ActivityMainBinding
import com.example.movielisttask.presentation.screens.MovieDetailsFragment
import com.example.movielisttask.presentation.screens.MoviesFragment
import com.example.movielisttask.presentation.viewmodel.MoviesViewModel
import com.example.movielisttask.presentation.viewmodel.MoviesViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val moviesViewModel by viewModels<MoviesViewModel> {
        val moviesApplication = application as MoviesApplication
        MoviesViewModelFactory(
            moviesApplication.localMoviesRepository,
            moviesApplication.remoteMoviesRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MoviesFragment>(R.id.movies_fragment_container_view)
            }
        }

        lifecycleScope.launch {
            moviesViewModel.displayMovies.collect { movies ->
                if (movies.isNotEmpty()) {
                    binding.progressBar.isGone = true
                }
            }
        }

        lifecycleScope.launch {
            moviesViewModel.selectedMovie.collect { selectedMovie ->
                if (selectedMovie != null &&
                    supportFragmentManager.findFragmentById(R.id.movies_fragment_container_view) !is MovieDetailsFragment
                ) {
                    binding.bottomNavigation.isGone = true
                    supportFragmentManager.commit {
                        replace<MovieDetailsFragment>(R.id.movies_fragment_container_view)
                        setReorderingAllowed(true)
                        addToBackStack("MovieDetailsFragment")
                    }
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { navItem ->
            moviesViewModel.onMenuItemClicked(navItem.itemId)
            return@setOnItemSelectedListener true
        }
    }

    override fun onStop() {
        super.onStop()
        moviesViewModel.onStopCalled()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            moviesViewModel.onBackPressed()
            binding.bottomNavigation.isGone = false
        } else {
            super.onBackPressed()
        }
    }
}