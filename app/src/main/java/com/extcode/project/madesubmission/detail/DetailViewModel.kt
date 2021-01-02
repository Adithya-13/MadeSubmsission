package com.extcode.project.madesubmission.detail

import androidx.lifecycle.ViewModel
import com.extcode.project.core.domain.model.Movie
import com.extcode.project.core.domain.usecase.MovieAppUseCase

class DetailViewModel(private val movieAppUseCase: MovieAppUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) {
        movieAppUseCase.setMovieFavorite(movie, newStatus)
    }
}
