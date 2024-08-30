package com.example.movielisttask

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.example.movielisttask.databinding.ActivityMainBinding
import com.example.movielisttask.screens.FavoritesFragment
import com.example.movielisttask.screens.MovieDetailsFragment
import com.example.movielisttask.screens.MoviesFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val movieViewModel by viewModels<MovieViewModel>()

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
            movieViewModel.selectedMovie.collect { selectedMovie ->
                if (selectedMovie != null) {
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