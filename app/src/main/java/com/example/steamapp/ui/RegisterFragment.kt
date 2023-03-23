package com.example.steamapp.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.steamapp.R
import com.example.steamapp.api.SteamWebService
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



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    snack.show()
                } else {
                    val id = userDao.insertUser(user)
                    val snack = Snackbar.make(it,"Account Creation Success", Snackbar.LENGTH_LONG)
                    snack.show()
                    findNavController().navigate(R.id.action_RegisterFragment_to_SignInFragment)
                    Log.d(TAG, "User inserted with id: $id")
                }
            }
            it.hideKeyboard()

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

