package com.ibrajix.feature.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ibrajix.feature.data.model.MovieList
import com.ibrajix.feature.databinding.LytPopularMoviesBinding
import com.ibrajix.feature.network.EndPoints

class MoviesAdapter(private val onClickListener: OnRelatedMoviesClickListener) : ListAdapter<MovieList, MoviesAdapter.MoviesViewHolder>(
    RelevantBooksDiffCallback()
) {

    class MoviesViewHolder private constructor(private val binding: LytPopularMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieList?) {


            binding.txtMovieName.text = item?.title?.uppercase()
            binding.txtRating.text = item?.vote_average.toString()
            binding.txtOverview.text = item?.overview

            val imageUrl = EndPoints.IMAGE_BASE_URL + item?.poster_path

            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(itemView.context)
                 .load(imageUrl)
                 .placeholder(circularProgressDrawable)
                 .transform(CenterInside(), RoundedCorners(24))
                 .into(binding.imgMovie)

        }

        companion object{
            fun from(parent: ViewGroup) : MoviesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LytPopularMoviesBinding.inflate(layoutInflater, parent, false)
                return MoviesViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
        holder.itemView.setOnClickListener {
            if (item != null) {
                onClickListener.onBookItemClicked(item)
            }
        }
    }

    class OnRelatedMoviesClickListener (val clickListener: (item: MovieList) -> Unit){
        fun onBookItemClicked(item: MovieList) = clickListener(item)
    }

    class RelevantBooksDiffCallback : DiffUtil.ItemCallback<MovieList>() {

        override fun areItemsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem == newItem
        }

    }

}