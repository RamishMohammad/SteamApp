package com.example.steamapp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.steamapp.R
import com.example.steamapp.api.App

class GameListAdapter(private var appList: List<App>) :
    RecyclerView.Adapter<GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_list_item, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val app = appList[position]

        holder.bind(app)
    }

    override fun getItemCount() = appList.size

    fun setData(newList: List<App>) {
        appList = newList
        notifyDataSetChanged()
    }
}

class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val gameName: TextView = itemView.findViewById(R.id.game_name)

    fun bind(app: App) {
        gameName.text = app.name
    }
}


