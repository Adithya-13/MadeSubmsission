<h1 align="center">
  Submission Dicoding MADE : Movie App
</h1>
<p align="center">
  Submission 1 MADE (Menjadi Android Developer Expert) in Dicoding Course.
</p>
<p align="center">
  <a href="http://developer.android.com/index.html"><img alt="Platform" src="https://img.shields.io/badge/platform-Android-green.svg"></a>
  <a href="http://kotlinlang.org"><img alt="Kotlin" src="https://img.shields.io/badge/kotlin-1.4.20-blue.svg"></a>
  <a href="https://developer.android.com/studio/releases/gradle-plugin"><img alt="Gradle" src="https://img.shields.io/badge/gradle-4.1.1-yellow.svg"></a>
  <a href="https://github.com/Adithya-13/MadeSubmsission1/"><img alt="Star" src="https://img.shields.io/github/stars/Adithya-13/MadeSubmsission1"></a>
</p>

<p align="center">
  <img src="assets/thumbnail.png"/>
</p>

## Get Started

Clone or Download and Open it into Android Studio
```
    https://github.com/Adithya-13/MadeSubmsission1.git
```  

Change the API_KEY [here](https://github.com/Adithya-13/MadeSubmsission1/blob/master/core/src/main/java/com/extcode/project/core/data/source/remote/RemoteDataSource.kt)

```
class RemoteDataSource(private val apiService: ApiService) {

    private val apiKey = "" //REPLACE YOUR API KEY HERE!!

    suspend fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
       ....
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvShowResponse>>> {
       ....
    }

}
```

you can get API_KEY from [TheMovieDb](https://developers.themoviedb.org/3)

Change

## Demo

|All Movies|All Tv Shows|Detail Movies|Detail Tv Shows|
|--|--|--|--|
|![](assets/Movies.gif?raw=true)|![](assets/tv_shows.gif?raw=true)|![](assets/detail_movies.gif?raw=true)|![](assets/detail_tv_shows.gif?raw=true)|
|Search Movies|Search Tv Shows|Favorite Movies|Favorite Tv Shows|
|--|--|--|--|
|![](assets/search_movies.gif?raw=true)|![](assets/search_tv_shows.gif?raw=true)|![](assets/favorite_movies.gif?raw=true)|![](assets/favorite_tv_shows.gif?raw=true)|

## Feature
- MVVM (Model-View-ViewModel) Architecture Pattern
- Modularization (core module)
- Dynamic Feature (favorite module)
- Clean Architecture (data, domain, presentation)
- Dependency Injection with Koin
- ViewBinding
- Get All the Movies and Tv Shows
- Search the Movies and Tv Shows using Coroutines Flow
- Favorited Movies and Tv Shows using Room Persistence
- Sort the Movies and Tv Shows by: Popularity, Vote, Release Date, and Random using RawQuery
- Share the Movies and Tv Shows to other Application

## Dependencies
- [Bubble Navigation](https://github.com/gauravk95/bubble-navigation)
- [Custom FAB](https://github.com/Clans/FloatingActionButton)
- [Rounded Image View](https://github.com/vinc3m1/RoundedImageView)
- [Material Search View](https://github.com/MiguelCatalan/MaterialSearchView)
- [Glide](https://github.com/bumptech/glide)
- [AndroidX](https://mvnrepository.com/artifact/androidx)
- [Lifecycle & LiveData](https://developer.android.com/jetpack/androidx/releases/lifecycle)
- [Koin](https://github.com/InsertKoinIO/koin)
- [Retrofit](https://square.github.io/retrofit/)
- [Coroutines Flow](https://developer.android.com/kotlin/flow)
- [Room](https://developer.android.com/training/data-storage/room?gclid=Cj0KCQiA0MD_BRCTARIsADXoopYlw1cozWjwyR-ucLYa-aoqYlZeJmxG34JnhByjApMNwuchOcAzcy0aAgGHEALw_wcB&gclsrc=aw.ds)
