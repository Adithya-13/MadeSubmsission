package com.extcode.project.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    const val POPULARITY = "Popularity"
    const val VOTE = "Vote"
    const val NEWEST = "Newest"
    const val RANDOM = "Random"

    fun getSortedQueryMovies(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movieEntities where isTvShow = 0 ")
        when (filter) {
            POPULARITY -> {
                simpleQuery.append("ORDER BY popularity DESC")
            }
            NEWEST -> {
                simpleQuery.append("ORDER BY releaseDate DESC")
            }
            VOTE -> {
                simpleQuery.append("ORDER BY voteAverage DESC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryTvShows(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movieEntities where isTvShow = 1 ")
        when (filter) {
            POPULARITY -> {
                simpleQuery.append("ORDER BY popularity DESC")
            }
            NEWEST -> {
                simpleQuery.append("ORDER BY releaseDate DESC")
            }
            VOTE -> {
                simpleQuery.append("ORDER BY voteAverage DESC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryFavoriteMovies(filter: String): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("SELECT * FROM movieEntities where favorite = 1 and isTvShow = 0 ")
        when (filter) {
            POPULARITY -> {
                simpleQuery.append("ORDER BY popularity DESC")
            }
            NEWEST -> {
                simpleQuery.append("ORDER BY releaseDate DESC")
            }
            VOTE -> {
                simpleQuery.append("ORDER BY voteAverage DESC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryFavoriteTvShows(filter: String): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("SELECT * FROM movieEntities where Favorite = 1 and isTvShow = 1 ")
        when (filter) {
            POPULARITY -> {
                simpleQuery.append("ORDER BY popularity DESC")
            }
            NEWEST -> {
                simpleQuery.append("ORDER BY releaseDate DESC")
            }
            VOTE -> {
                simpleQuery.append("ORDER BY voteAverage DESC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}