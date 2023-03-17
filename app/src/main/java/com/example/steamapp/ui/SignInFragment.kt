package com.example.steamapp.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.steamapi.api.AuthenticateUserRequest
import com.example.steamapi.api.SteamAuth
import com.example.steamapi.api.SteamService
import com.example.steamapp.databinding.FragmentSignInBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private lateinit var steamService: SteamService

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        steamService = SteamService.create()

        binding.signInButton.setOnClickListener {
            val steamid = binding.usernameEditText.text?.toString() ?: ""
            val password = binding.passwordEditText.text.toString()
            val errorMessage = "An error occurred. Please try again later."


            if (steamid.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    val request = AuthenticateUserRequest(steamid, password, SteamService.API_KEY)
                    val response = steamService.authenticateUser(request)
                    if (response.isSuccessful) {
                        val steamAuth = response.body()?.response
                        if (steamAuth != null && steamAuth.success) {
                            // User is authenticated
                            Log.d(TAG, "API call succeeded with status code: ${response.code()}")
                            Log.d(TAG, "Response data: ${response.body()}")

                        } else {
                            Log.d(TAG, "FAILEDDD")
                            // TODO: Show an error message to the user
                        }
                    } else {
                        Log.d(TAG, "API FAILEDDD")
                        Log.e(TAG, response.errorBody()?.string() ?: "Error body is null")


                        // TODO: Show an error message to the user
                    }
                }
            } else {
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
