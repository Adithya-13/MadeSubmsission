package com.extcode.project.core.data.source.remote

import com.extcode.project.core.data.source.remote.network.ApiResponse
import com.extcode.project.core.data.source.remote.network.ApiService
import com.extcode.project.core.data.source.remote.response.MovieResponse
import com.extcode.project.core.data.source.remote.response.TvShowResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    private val apiKey = "" // REPLACE YOUR API KEY HERE

    suspend fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovies(apiKey)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvShowResponse>>> {
        return flow {
            try {
                val response = apiService.getTvShows(apiKey)
                val tvShowList = response.results
                if (tvShowList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}
