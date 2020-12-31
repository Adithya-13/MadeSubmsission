package com.extcode.project.core.data.source.local

import com.extcode.project.core.data.source.local.entity.MovieEntity
import com.extcode.project.core.data.source.local.room.MovieDao
import com.extcode.project.core.utils.SortUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val mMovieDao: MovieDao) {

    fun getAllMovies(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryMovies(sort)
        return mMovieDao.getMovies(query)
    }

    fun getAllTvShows(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryTvShows(sort)
        return mMovieDao.getTvShows(query)
    }

    fun getAllFavoriteMovies(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryFavoriteMovies(sort)
        return mMovieDao.getFavoriteMovies(query)
    }

    fun getAllFavoriteTvShows(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryFavoriteTvShows(sort)
        return mMovieDao.getFavoriteTvShows(query)
    }

    fun getMovieSearch(search: String): Flow<List<MovieEntity>> {
        return mMovieDao.getSearchMovies(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    fun getTvShowSearch(search: String): Flow<List<MovieEntity>> {
        return mMovieDao.getSearchTvShows(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovie(movies)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        mMovieDao.updateFavoriteMovie(movie)
    }
}