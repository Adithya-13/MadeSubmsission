package com.extcode.project.madesubmission1.di

import com.extcode.project.core.domain.usecase.MovieAppInteractor
import com.extcode.project.core.domain.usecase.MovieAppUseCase
import com.extcode.project.madesubmission1.detail.DetailViewModel
import com.extcode.project.madesubmission1.movies.MoviesViewModel
import com.extcode.project.madesubmission1.tvshows.TvShowsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieAppUseCase> { MovieAppInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { TvShowsViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}