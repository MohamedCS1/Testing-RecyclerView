package com.example.testingrecyclerview.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codingwithmitch.espressouitestexamples.ui.movie.StarActorsFragment
import com.example.testingrecyclerview.R
import com.example.testingrecyclerview.data.Movie
import com.example.testingrecyclerview.data.source.MoviesDataSource


class MovieDetailFragment
constructor(
    val requestOptions: RequestOptions,
    val moviesDataSource: MoviesDataSource
): Fragment(){

    private val TAG: String = "AppDebug"

    private lateinit var movie_description:TextView
    private lateinit var movie_directiors: TextView
    private lateinit var movie_image: ImageView
    private lateinit var movie_title: TextView
    private lateinit var movie_star_actors: TextView
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            args.getInt("movie_id").let{ movieId ->
                moviesDataSource.getMovie(movieId)?.let{ movieFromRemote ->
                    movie = movieFromRemote
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        movie_description = view.findViewById(R.id.movie_description)
        movie_directiors = view.findViewById(R.id.movie_directiors)
        movie_image = view.findViewById(R.id.movie_image)
        movie_title = view.findViewById(R.id.movie_title)
        movie_star_actors = view.findViewById(R.id.movie_star_actors)
        return view     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieDetails()

        movie_directiors.setOnClickListener {
            val bundle = Bundle()
            bundle.putStringArrayList("args_directors", movie.directors)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, DirectorsFragment::class.java, bundle)
                ?.addToBackStack("DirectorsFragment")
                ?.commit()
        }

        movie_star_actors.setOnClickListener {
            val bundle = Bundle()
            bundle.putStringArrayList("args_actors", movie.star_actors)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, StarActorsFragment::class.java, bundle)
                ?.addToBackStack("StarActorsFragment")
                ?.commit()
        }
    }

    private fun setMovieDetails(){
        Glide.with(this@MovieDetailFragment)
            .applyDefaultRequestOptions(requestOptions)
            .load(movie.image)
            .into(movie_image)
        movie_title.text = movie.title
        movie_description.text = movie.description
    }

}
