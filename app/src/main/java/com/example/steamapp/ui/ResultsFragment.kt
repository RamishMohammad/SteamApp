package com.example.steamapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.steamapp.R
import com.example.steamapp.data.SearchResultsRVAdapter
import com.example.steamapp.databinding.FragmentResultsBinding


class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null

    private val args: ResultsFragmentArgs by navArgs()

    // on below line we are creating variables
    // for our swipe to refresh layout,
    // recycler view, adapter and list.
    lateinit var gameRV: RecyclerView
    lateinit var gameRVAdapter: SearchResultsRVAdapter
    lateinit var gameList: ArrayList<SearchResultsRVModel>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_results, container, false)


        // on below line we are initializing
        // our views with their ids.
        gameRV = view.findViewById(R.id.idRVGames)

        // on below line we are initializing our list
        gameList = ArrayList()

        // on below line we are creating a variable
        // for our grid layout manager and specifying
        // column count as 3
        val layoutManager = GridLayoutManager(context, 3)

        gameRV.layoutManager = layoutManager

        // on below line we are initializing our adapter
        gameRVAdapter = context?.let { SearchResultsRVAdapter(gameList, it) }!!

        // on below line we are setting
        // adapter to our recycler view.
        gameRV.adapter = gameRVAdapter

        // on below line we are adding data to our list
//        gameList.add(SearchResultsRVModel("foobar", R.drawable.ic_game_sample_foreground))
//        gameList.add(SearchResultsRVModel("foobar", R.drawable.ic_game_sample_foreground))
//        gameList.add(SearchResultsRVModel("foobar", R.drawable.ic_game_sample_foreground))
//        gameList.add(SearchResultsRVModel("foobar", R.drawable.ic_game_sample_foreground))
//        gameList.add(SearchResultsRVModel("foobar", R.drawable.ic_game_sample_foreground))

        for(game in args.gameList) {
            gameList.add(SearchResultsRVModel(game, R.drawable.ic_game_sample_foreground))
        }


        var adapter = context?.let { SearchResultsRVAdapter(gameList, it) }!!
        gameRV.adapter = adapter
        adapter.setOnItemClickListener(object: SearchResultsRVAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val newPosition = position + 1
                Toast.makeText(context, "You Clicked on item no. $newPosition", Toast.LENGTH_SHORT).show()
                showPopupMenu()
            }
        })

        // on below line we are notifying adapter
        // that data has been updated.
        gameRVAdapter.notifyDataSetChanged()

        return view
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(context, view?.findViewById(R.id.idIVGame))

        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem ->

            when (item.itemId) {

                R.id.action_item1 -> {
                    Toast.makeText(context, "action item1 clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.action_item2 -> {
                    Toast.makeText(context, "action item2 clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.action_item3 -> {
                    Toast.makeText(context, "action item3 clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.action_item4 -> {
                    Toast.makeText(context, "action item3 clicked", Toast.LENGTH_SHORT).show()
                }
            }
            true
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}