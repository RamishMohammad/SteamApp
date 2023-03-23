package com.example.steamapp.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.steamapp.R
import com.example.steamapp.api.SteamWebService
import com.example.steamapp.data.AppDataBase
import com.example.steamapp.data.UserDao
import com.example.steamapp.databinding.FragmentSignInBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.appcompat.widget.AppCompatEditText
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar


class SignInFragment : Fragment() {

    private companion object {
        const val steamid = 76561199487623107
    }

    private var _binding: FragmentSignInBinding? = null
    private lateinit var steamWebService: SteamWebService
    private lateinit var userDao: UserDao

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        steamWebService = SteamWebService.create()
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDataBase::class.java, "my-db"
        ).fallbackToDestructiveMigration().build()
        userDao = db.userDao()

        binding.signInButton.setOnClickListener {
            val username = binding.outlinedEditText.text.toString()
            val password = binding.outlinedEditText2.text.toString()
            Log.d(TAG, "Username: ${username}, Password: ${password}")

            lifecycleScope.launch(Dispatchers.Main) {
                val user = userDao.getUserByUsernameAndPassword(username, password)
                Log.d(TAG, "User: ${user}")
                Log.d(TAG, "Username: ${user?.username}, Password: ${user?.password}")
                if (user != null) {
                    // Valid user
                    val snack = Snackbar.make(it,"Successful Login",Snackbar.LENGTH_LONG)
                    snack.show()
                    val url = "${SteamWebService.BASE_URL}ISteamUser/GetPlayerSummaries/v2/?key=${SteamWebService.API_KEY}&steamids=$steamid"
                    findNavController().navigate(R.id.action_SignInFragment_to_ProfileFragment)

                    Log.d(TAG, "User login successful")
                    Log.d(TAG, "API URL: $url")

                } else {
                    // Invalid user
                    val snack = Snackbar.make(it,"Invalid User",Snackbar.LENGTH_LONG)
                    snack.show()
                    Log.d(TAG, "Invalid username or password")
                }
            }
            it.hideKeyboard()
        }

        // Clickable Text to RegisterFragment (Create Account)
        binding.textViewClick.setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_RegisterFragment)
        }



    }


    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
