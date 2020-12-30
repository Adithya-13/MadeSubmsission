package com.extcode.project.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListMovieResponse(

	@field:SerializedName("results")
	val results: List<MovieResponse>
)

data class MovieResponse(

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("popularity")
	val popularity: Double,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("vote_count")
	val voteCount: Int,

	@field:SerializedName("poster_path")
	val posterPath: String
)
