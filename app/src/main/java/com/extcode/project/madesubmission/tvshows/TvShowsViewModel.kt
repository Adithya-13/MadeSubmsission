package com.extcode.project.madesubmission.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.extcode.project.core.data.Resource
import com.extcode.project.core.domain.model.Movie
import com.extcode.project.core.domain.usecase.MovieAppUseCase

class TvShowsViewModel(private val movieAppUseCase: MovieAppUseCase) : ViewModel() {
    fun getTvShows(sort: String): LiveData<Resource<List<Movie>>> =
        movieAppUseCase.getAllTvShows(sort).asLiveData()
}