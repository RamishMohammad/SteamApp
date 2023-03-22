package com.example.steamapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.steamapp.R
import com.example.steamapp.databinding.ActivityMainBinding
import com.example.steamapp.databinding.FragmentResultsBinding


class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_results, container, false)

        val gridView = view.findViewById(R.id.gridView) as GridView

        val arrayListImage = ArrayList<Int>()

        arrayListImage.add(R.drawable.ic_launcher_background)
        arrayListImage.add(R.drawable.ic_launcher_background)
        arrayListImage.add(R.drawable.ic_launcher_background)
        arrayListImage.add(R.drawable.ic_launcher_background)
        arrayListImage.add(R.drawable.ic_launcher_background)

        val name = arrayOf("Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread")

        val myAdapter = context?.let { MyAdapter(it, arrayListImage, name) }

        gridView.adapter = myAdapter

        gridView.setOnItemClickListener { adapterView, parent, position, l ->
            Toast.makeText(context, "Click on : " + name[position], Toast.LENGTH_LONG).show()
        }

//        _binding = FragmentResultsBinding.inflate(inflater, container, false)
//        return binding.root
        return view
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
