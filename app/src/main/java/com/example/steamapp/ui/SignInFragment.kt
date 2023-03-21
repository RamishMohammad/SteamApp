package com.example.steamapp.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.steamapp.api.AuthenticateUserRequest
import com.example.steamapp.api.SteamService
import com.example.steamapp.data.AppDataBase
import com.example.steamapp.data.UserDao
import com.example.steamapp.databinding.FragmentSignInBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SignInFragment : Fragment() {

    private companion object {
        const val steamid = 76561199487623107
    }

    private var _binding: FragmentSignInBinding? = null
    private lateinit var steamService: SteamService
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

        steamService = SteamService.create()
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDataBase::class.java, "my-db"
        ).build()
        userDao = db.userDao()

        binding.signInButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                val user = userDao.getUserByUsernameAndPassword(username, password)
                if (user != null) {
                    // Valid user
                    Log.d(TAG, "User login successful")
                    val url = "${SteamService.BASE_URL}ISteamUser/GetPlayerSummaries/v2/?key=${SteamService.API_KEY}&steamids=$steamid"
                    Log.d(TAG, "API URL: $url")
                    // TODO: Handle the response accordingly
                } else {
                    // Invalid user
                    Log.d(TAG, "Invalid username or password")
                    // TODO: Handle the response accordingly
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

