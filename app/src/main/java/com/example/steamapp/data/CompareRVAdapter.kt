package com.example.steamapp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.steamapp.R
import com.example.steamapp.api.AppData
import com.squareup.picasso.Picasso

class CompareRVAdapter : RecyclerView.Adapter<CompareRVAdapter.CompareViewHolder>() {

    val games: MutableList<AppData> = mutableListOf()

    override fun getItemCount() = games.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompareViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_list_item, parent, false)
        return CompareViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompareViewHolder, position: Int) {
        holder.bind(games[position])
    }

    class CompareViewHolder(view: View): ViewHolder(view) {
        private val gameImage: ImageView = view.findViewById(R.id.compare_game_image)
        private val descriptionTV: TextView = view.findViewById(R.id.compare_game_desc_tv)

        // Requires an AppData object from SteamStoreService.getAppDetails()
        // Uses Picasso to load the header image into the imageview
        fun bind(game: AppData) {
            Picasso.get().load(game.header_image).into(gameImage)
            descriptionTV.text = game.short_description
        }
    }
}