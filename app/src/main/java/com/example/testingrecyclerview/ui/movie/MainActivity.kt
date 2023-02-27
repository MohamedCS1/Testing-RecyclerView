package com.example.testingrecyclerview.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.bumptech.glide.request.RequestOptions
import com.example.testingrecyclerview.R
import com.example.testingrecyclerview.data.source.MoviesDataSource
import com.example.testingrecyclerview.data.source.MoviesRemoteDataSource
import com.example.testingrecyclerview.factory.MovieFragmentFactory
import com.example.testingrecyclerview.ui.UICommunicationListener

class MainActivity : AppCompatActivity() ,UICommunicationListener{

    lateinit var progress_bar:ProgressBar

    lateinit var requestOptions: RequestOptions
    lateinit var moviesDataSource: MoviesDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        supportFragmentManager.fragmentFactory = MovieFragmentFactory(
            requestOptions,
            moviesDataSource
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress_bar = findViewById(R.id.progress_bar)
        init()

    }

    override fun loading(isLoading: Boolean) {
        if (isLoading)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.INVISIBLE
    }



    private fun init(){
        if(supportFragmentManager.fragments.size == 0){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieListFragment::class.java, null)
                .commit()
        }
    }

    private fun initDependencies(){
        if(!::requestOptions.isInitialized){
            // glide
            requestOptions = RequestOptions
                .placeholderOf(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        }
        if(!::moviesDataSource.isInitialized){
            // Data Source
            moviesDataSource = MoviesRemoteDataSource()
        }
    }
}