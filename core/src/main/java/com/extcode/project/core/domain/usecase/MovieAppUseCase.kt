package com.extcode.project.core.domain.usecase

import com.extcode.project.core.data.Resource
import com.extcode.project.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieAppUseCase {

    fun getAllMovies(sort: String): Flow<Resource<List<Movie>>>

    fun getAllTvShows(sort: String): Flow<Resource<List<Movie>>>

    fun getFavoriteMovies(sort: String): Flow<List<Movie>>

    fun getFavoriteTvShows(sort: String): Flow<List<Movie>>

    fun setMovieFavorite(movie: Movie, state: Boolean)
}