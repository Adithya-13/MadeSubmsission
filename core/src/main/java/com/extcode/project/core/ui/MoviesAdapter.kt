package com.extcode.project.core.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.extcode.project.core.R
import com.extcode.project.core.databinding.ItemCardListBinding
import com.extcode.project.core.domain.model.Movie
import java.util.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun getSwipedData(swipedPosition: Int): Movie = listData[swipedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemCardListBinding =
            ItemCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemCardListBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class MovieViewHolder(private val binding: ItemCardListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                title.text = movie.title
                date.text = movie.releaseDate
                language.text = movie.originalLanguage
                popularity.text =
                    itemView.context.getString(
                        R.string.popularity_d,
                        movie.popularity.toString()
                    )
                userScore.text = movie.voteAverage.toString()

                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.baseUrlImage, movie.posterPath))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = GONE
                            return false
                        }
                    })
                    .into(poster)

//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
//                        putExtra(DetailActivity.EXTRA_TYPE, DetailType.MOVIE.ordinal)
//                        putExtra(DetailActivity.EXTRA_ID, movie.id)
//                    }
//                    itemView.context.startActivity(intent)
//                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}