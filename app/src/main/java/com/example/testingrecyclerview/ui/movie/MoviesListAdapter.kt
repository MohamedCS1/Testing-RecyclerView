package com.codingwithmitch.espressouitestexamples.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testingrecyclerview.R
import com.example.testingrecyclerview.data.Movie
import com.example.testingrecyclerview.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesListAdapter(private val interaction: Interaction? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_list_item, parent, false), interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Movie>) {
        val dataCommitCallback = Runnable {
            EspressoIdlingResource.decrement()
        }
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                differ.submitList(list ,dataCommitCallback)
            }
        }
    }

    class MovieViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        var movie_star_actors: TextView = itemView.findViewById(R.id.movie_star_actors)
        var movie_title: TextView = itemView.findViewById(R.id.movie_title)
        var movie_image: ImageView = itemView.findViewById(R.id.movie_image)

        fun bind(item: Movie) = with(itemView) {

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            movie_title.text = item.title
            Glide.with(itemView)
                .load(item.image)
                .into(movie_image)
            item.star_actors?.let {
                for(index in 0 until it.size){
                    var appendValue: String = it[index]
                    if(index < (it.size - 1)){
                        appendValue += ", "
                    }
                    movie_star_actors.append(appendValue)
                }
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Movie)
    }
}
