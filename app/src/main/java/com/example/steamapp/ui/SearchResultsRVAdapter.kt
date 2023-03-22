package com.example.steamapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.steamapp.R

// on below line we are creating
// a course rv adapter class.
class SearchResultsRVAdapter(
    // on below line we are passing variables
    // as course list and context
    private val gameList: ArrayList<SearchResultsRVModel>,
    private val context: Context
) : RecyclerView.Adapter<SearchResultsRVAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultsRVAdapter.GameViewHolder {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.results_rv_item,
            parent, false
        )
        // at last we are returning our view holder
        // class with our item View File.
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchResultsRVAdapter.GameViewHolder, position: Int) {
        // on below line we are setting data to our text view and our image view.
        holder.gameNameTV.text = gameList[position].gameName
        holder.gameIV.setImageResource(gameList[position].gameImg)
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our size of our list
        return gameList.size
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our course name text view and our image view.
        val gameNameTV: TextView = itemView.findViewById(R.id.idTVGame)
        val gameIV: ImageView = itemView.findViewById(R.id.idIVGame)
    }
}