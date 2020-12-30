package com.extcode.project.core.domain.usecase

import com.extcode.project.core.data.Resource
import com.extcode.project.core.domain.model.Movie
import com.extcode.project.core.domain.repository.IMovieAppRepository
import kotlinx.coroutines.flow.Flow

class MovieAppInteractor(private val iMovieAppRepository: IMovieAppRepository) : MovieAppUseCase {

    override fun getAllMovies(sort: String): Flow<Resource<List<Movie>>> {
        return iMovieAppRepository.getAllMovies(sort)
    }

    override fun getAllTvShows(sort: String): Flow<Resource<List<Movie>>> =
        iMovieAppRepository.getAllTvShows(sort)

    override fun getFavoriteMovies(sort: String): Flow<List<Movie>> =
        iMovieAppRepository.getFavoriteMovies(sort)

    override fun getFavoriteTvShows(sort: String): Flow<List<Movie>> =
        iMovieAppRepository.getFavoriteTvShows(sort)

    override fun setMovieFavorite(movie: Movie, state: Boolean) =
        iMovieAppRepository.setMovieFavorite(movie, state)

}