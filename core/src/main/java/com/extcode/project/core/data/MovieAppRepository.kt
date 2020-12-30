package com.extcode.project.core.data

import android.util.Log
import com.extcode.project.core.data.source.local.LocalDataSource
import com.extcode.project.core.data.source.remote.RemoteDataSource
import com.extcode.project.core.data.source.remote.network.ApiResponse
import com.extcode.project.core.data.source.remote.response.MovieResponse
import com.extcode.project.core.data.source.remote.response.TvShowResponse
import com.extcode.project.core.domain.model.Movie
import com.extcode.project.core.domain.repository.IMovieAppRepository
import com.extcode.project.core.utils.AppExecutors
import com.extcode.project.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieAppRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieAppRepository {

    override fun getAllMovies(sort: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies(sort).map {
                    Log.d("localDataa", it.toString())
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                Log.d("movieList", movieList.toString())
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getAllTvShows(sort: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<TvShowResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllTvShows(sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> {
                return remoteDataSource.getTvShows()
            }

            override suspend fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = DataMapper.mapTvShowResponsesToEntities(data)
                localDataSource.insertMovies(tvShowList)
            }
        }.asFlow()

    override fun getFavoriteMovies(sort: String): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteMovies(sort).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteTvShows(sort: String): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteTvShows(sort).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setMovieFavorite(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movieEntity, state) }
    }
}