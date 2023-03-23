package com.example.steamapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.steamapp.R
import com.example.steamapp.api.AppList
import com.example.steamapp.data.AppListStorage
import com.example.steamapp.data.GameListAdapter
import com.example.steamapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var filteredAppList: AppList
    private lateinit var gameListAdapter: GameListAdapter
    private val appListStorage = AppListStorage.getInstance()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameListAdapter = GameListAdapter(emptyList())
        filteredAppList = AppList(emptyList())
        binding.gameList.adapter = gameListAdapter
        binding.gameList.layoutManager = LinearLayoutManager(requireContext())

        binding.idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredList = appListStorage.filterListByName(newText)
                    gameListAdapter.setData(filteredList)
                    Log.d("SearchFragment", "Filtered list size: ${filteredList.size}")
                }
                return true
            }

        })

        binding.btnSearch.setOnClickListener {
            val query = binding.idSV.query.toString()
            updateFilteredList(query)
            it.hideKeyboard()


            val gameList = Array<String>(filteredAppList.apps.size) {i -> filteredAppList.apps[i].name}
            val directions = SearchFragmentDirections.actionSearchFragmentToResults(gameList)
            findNavController().navigate(directions)
        }
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun updateFilteredList(query: String) {
        val filteredList = appListStorage.filterListByName(query)
        gameListAdapter = GameListAdapter(filteredList)
        filteredAppList = AppList(filteredList)
        binding.gameList.adapter = gameListAdapter
        Log.d("SearchFragment", "Filtered list size: ${filteredList.size}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

