package com.example.steamapp.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.credentials.CreateCredentialException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.credentials.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.steamapp.R
import com.example.steamapp.data.AppDataBase
import com.example.steamapp.data.User
import com.example.steamapp.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var submitButton: Button

    private lateinit var credentialManager: CredentialManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(34)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        credentialManager = CredentialManager.create(requireContext())

        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDataBase::class.java, "my-db"
        ).fallbackToDestructiveMigration().build()
        val userDao = db.userDao()

        usernameEditText = binding.usernameEdittext
        passwordEditText = binding.passwordEdittext
        submitButton = binding.registerButton

        submitButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val user = User(username = username, password = password)

            lifecycleScope.launch {
                if (username.isBlank() || password.isBlank()) {
                    Log.d(TAG, "Invalid username or password")
                    val snack = Snackbar.make(it,"All Fields Required",Snackbar.LENGTH_LONG)
                    snack.setAnchorView(R.id.bottomNavigationView)
                    snack.show()
                } else {
                    // Check if username already exists
                    val existingUser = userDao.getUsername(username)
                    Log.d(TAG, "User : $existingUser")
                    if (existingUser >= 1) {
                        val snacks = Snackbar.make(it,"Username already exists. Please select a different username.", Snackbar.LENGTH_LONG)
                        snacks.setAnchorView(R.id.bottomNavigationView)
                        snacks.show()
                        Log.d(TAG, "User already exists")
                    } else {
                        val id = userDao.insertUser(user)
                        // Android emulator doesn't support Google Password Manager
                        // So we can't actually call any code from the jetpack credentials library
                        // The code was done, so I'm going to leave it commented out just so I can say
                        // it could have worked
                        // createPasswordCredential(username, password)
                        val snack = Snackbar.make(it,"Account Creation Success", Snackbar.LENGTH_LONG)
                        snack.setAnchorView(R.id.bottomNavigationView)
                        snack.show()
                        findNavController().navigate(R.id.action_RegisterFragment_to_SignInFragment)
                        Log.d(TAG, "User inserted with id: $id")
                    }
                }
            }
            it.hideKeyboard()
        }

    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    /**
     * Inserts account into GooglePasswordManager
     */
    @RequiresApi(34)
    fun createPasswordCredential(username: String, password: String) {
        val createPasswordRequest = CreatePasswordRequest(
            id = username,
            password
        )

        lifecycleScope.launch {
            try {
                val result = activity?.let {
                    credentialManager.createCredential(
                        request = createPasswordRequest,
                        activity = it,
                    )
                }
                handlePasskeyRegistrationResult(result)
            } catch (e: CreateCredentialException) {
                Log.e(tag, "Failed to create password: $e")
            }
        }
    }

    private fun handlePasskeyRegistrationResult(result: CreateCredentialResponse?) {
        when (result) {
            CreatePasswordResponse() -> {
                Log.d(tag, "Password successfully stored!")
            }
            else -> {
                Log.d(tag, "Honestly not sure how we got here.")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

