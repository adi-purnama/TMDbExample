package com.adipurnama.tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adipurnama.tmdb.R
import com.adipurnama.tmdb.data.network.Movies
import com.adipurnama.tmdb.databinding.ItemMovieListBinding
import com.adipurnama.tmdb.utilitys.IMAGE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movie_list.view.*

/**
 * Created by Adi Purnama
 * @2019
 */
internal class MoviesAdapter(itemEvents: (Movies)->Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var data: List<Movies> = ArrayList()
    private val listener= itemEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemMovieListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(private val binding: ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movies) = with(itemView) {
            binding.data=item
            if (item.poster_path.isNotEmpty()) {
                Glide.with(context)
                    .load("$IMAGE_URL${item.poster_path}")
                    .apply(
                        RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .placeholder(R.drawable.ic_image_default)
                    .error(R.drawable.ic_image_default)
                    .into(image_movie)
            }
            setOnClickListener {
                listener(item)
            }
        }
    }

    fun setListData(data: List<Movies>) {
        this.data = data
        notifyDataSetChanged()
    }
}