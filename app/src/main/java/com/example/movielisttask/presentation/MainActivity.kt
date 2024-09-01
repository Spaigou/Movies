package com.example.movielisttask.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.example.movielisttask.R
import com.example.movielisttask.databinding.ActivityMainBinding
import com.example.movielisttask.presentation.screens.FavoritesFragment
import com.example.movielisttask.presentation.screens.MovieDetailsFragment
import com.example.movielisttask.presentation.screens.MoviesFragment
import com.example.movielisttask.presentation.viewmodel.MoviesViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val moviesViewModel by viewModels<MoviesViewModel>()

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
            moviesViewModel.selectedMovie.collect { selectedMovie ->
                if (selectedMovie != null) {
                    binding.bottomNavigation.isGone = true
                    supportFragmentManager.commit {
                        replace<MovieDetailsFragment>(R.id.movies_fragment_container_view)
                        setReorderingAllowed(true)
                        addToBackStack("MovieDetailsFragment")
                    }
                } else {
                    binding.bottomNavigation.isGone = false
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