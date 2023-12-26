package com.example.movieapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.repository.MoviesRepository

class MoviesViewModelProviderFactory(val app: Application,val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieViewModel(app,moviesRepository) as T
        }
}