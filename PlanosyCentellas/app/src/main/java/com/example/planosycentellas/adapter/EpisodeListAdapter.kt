package com.example.planosycentellas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planosycentellas.databinding.EpisodeBinding
import com.example.planosycentellas.model.Episode
import com.squareup.picasso.Picasso

class EpisodeListAdapter(listener: EpisodeClickListener, longListener: EpisodeLongClickListener):
    RecyclerView.Adapter<EpisodeListAdapter.EpisodeHolder>() {

    private var episodeList: List<Episode> = ArrayList()
    private var episodeClickListener: EpisodeClickListener = listener
    private var episodeLongClickListener: EpisodeLongClickListener = longListener

    interface EpisodeClickListener {
        fun onEpisodeClicked(url: String)
    }

    interface EpisodeLongClickListener {
        fun onEpisodeLongClicked(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = EpisodeBinding.inflate(inflater, parent, false)

        return EpisodeHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        holder.bind(episodeList[position].title, episodeList[position].image)
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

    fun setEpisodeList(episodeList: List<Episode>) {
        this.episodeList = episodeList
        notifyDataSetChanged()
    }

    inner class EpisodeHolder(episodeBinding: EpisodeBinding): RecyclerView.ViewHolder(episodeBinding.root),
        View.OnClickListener, View.OnLongClickListener {

        private val binding: EpisodeBinding = episodeBinding

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        fun bind(name: String, image: String) {
            setName(name)
            setImage(image)
        }

        private fun setName(name: String) {
            binding.elementText.text = name
        }

        private fun setImage(image: String) {
            Picasso.get().load(image).resize(75, 75).into(binding.elementImage)
        }

        override fun onClick(p0: View?) {
            episodeClickListener.onEpisodeClicked(episodeList[adapterPosition].url)
        }

        override fun onLongClick(p0: View?): Boolean {
            episodeLongClickListener.onEpisodeLongClicked(episodeList[adapterPosition].url)
            return true
        }
    }
}