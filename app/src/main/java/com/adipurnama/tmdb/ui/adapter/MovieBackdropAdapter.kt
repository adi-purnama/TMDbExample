package com.adipurnama.tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adipurnama.tmdb.R
import com.adipurnama.tmdb.data.network.ImagesBackDrop
import com.adipurnama.tmdb.databinding.ItemMovieBackdropBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movie_backdrop.view.*

/**
 * Created by Adi Purnama
 * @2019
 */
internal class MovieBackdropAdapter(itemEvents: (ImagesBackDrop)->Unit) :
    RecyclerView.Adapter<MovieBackdropAdapter.ViewHolder>() {

    private var data: List<ImagesBackDrop> = ArrayList()
    private val listener= itemEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemMovieBackdropBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(private val binding: ItemMovieBackdropBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImagesBackDrop) = with(itemView) {
            binding.data=item
            if (item.filePath.isNotEmpty()) {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${item.filePath}")
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

    fun setListData(data: List<ImagesBackDrop>) {
        this.data = data
        notifyDataSetChanged()
    }
}
