package com.example.midtermexam.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.midtermexam.R
import com.example.midtermexam.model.AuthViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {
    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var successMessage: String
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var loginBtn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // tampilkan success registraion message jika tidak kosong
        successMessage = authViewModel.successMessage.value.toString()
        if (successMessage.isNotBlank()) Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show()

        // inisialisasi value untuk username dan password
        email = view.findViewById(R.id.editTextEmail)
        password = view.findViewById(R.id.editTextPassword)
        loginBtn = view.findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener { login() }
    }

    private fun login() {
        val email = email.text.toString()
        val password = password.text.toString()
        val storedEmail = authViewModel.email
        // untuk saat ini, password belum dihash
        val storedPass = authViewModel.password

        if (email.isNotBlank() && password.isNotBlank()) {
            if (storedEmail.value.equals(email) && storedPass.value.equals(password)) {
                val fullName = authViewModel.fullName.value.toString()
                authViewModel.setIsLoggedIn(true)
                authViewModel.setSuccessMessage("Selamat datang ${fullName}!")
                findNavController().navigate(R.id.action_LoginFragment_to_HomeFragment)
            } else {
                Toast.makeText(requireContext(), "Email atau password invalid", Toast.LENGTH_SHORT).show()
            }
            // jika ada input yang masih kosong...
        } else {
            // tampilkan pesan
            Toast.makeText(requireContext(), "Semua input harus diisi", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}