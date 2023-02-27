package com.example.testingrecyclerview.data.source

import com.example.testingrecyclerview.data.Movie

interface MoviesDataSource {

    fun getMovie(movieId: Int): Movie?

    fun getMovies(): List<Movie>
}