package com.example.movielisttask.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import com.example.movielisttask.presentation.screens.FavoritesFragment
import com.example.movielisttask.presentation.screens.MovieDetailsFragment
import com.example.movielisttask.presentation.screens.MoviesFragment
import com.example.movielisttask.presentation.viewmodel.MoviesViewModel
import com.example.movielisttask.presentation.viewmodel.MoviesViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val moviesViewModel by viewModels<MoviesViewModel> {
        val moviesApplication = application as MoviesApplication
        MoviesViewModelFactory(moviesApplication.favoritesRepository)
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
            moviesViewModel.movies.collect { movies ->
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
            if (navItem.itemId == R.id.movies) {
                switchPage(R.id.movies_fragment)
            } else if (navItem.itemId == R.id.favorites) {
                switchPage(R.id.favorites_fragment)
            }
            return@setOnItemSelectedListener true
        }
    }


    override fun onStart() {
        super.onStart()
        moviesViewModel.onStartCalled()
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

    private fun switchPage(pageId: Int) {
        supportFragmentManager.commit {
            if (pageId == R.id.favorites_fragment)
                replace<FavoritesFragment>(R.id.movies_fragment_container_view)
            else if (pageId == R.id.movies_fragment)
                replace<MoviesFragment>(R.id.movies_fragment_container_view)
            setReorderingAllowed(true)
            if (pageId != R.id.movies_fragment)
                addToBackStack(null)
        }
    }
}