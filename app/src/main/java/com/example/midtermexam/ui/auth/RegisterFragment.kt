package com.example.midtermexam.ui.auth

import android.os.Bundle
import android.util.Log
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

class RegisterFragment : Fragment() {
    // buat variabel untuk menampung elemen-elemen
    private lateinit var fullName: TextInputEditText
    private lateinit var username: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var registerBtn: Button

    // buat variabel dari AuthViewModel
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // cari elemen berdasarkan ID nya
        registerBtn = view.findViewById(R.id.registerBtn)
        fullName = view.findViewById(R.id.editTextFullName)
        username = view.findViewById(R.id.editTextUsername)
        email = view.findViewById(R.id.editTextEmail)
        password = view.findViewById(R.id.editTextPassword)

        // tambahkan on click listener ke button register
        registerBtn.setOnClickListener { register() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    private fun register() {
        // ambil nilai dari input
        val fullName = fullName.text.toString()
        val username = username.text.toString()
        val email = email.text.toString()
        val password = password.text.toString()

        // jika semua input tidak null
        if (fullName.isNotBlank() && username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            if (password.length < 8) // jika password kurang dari 8 karakter, tampilkan pesan melalui Toast
                Toast.makeText(
                    requireContext(),
                    "Password min. 8 karakter",
                    Toast.LENGTH_SHORT
                ).show()
            else { // jika panjang password sudah sesuai
                try {
                    // isi data pada AuthViewModel dengan data tsb
                    authViewModel.setFullName(fullName)
                    authViewModel.setUsername(username)
                    authViewModel.setEmail(email)
                    authViewModel.setPassword(password)
                    authViewModel.setSuccessMessage("Registrasi berhasil!")

                    // navigate ke halaman login
                    findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)

                    // jika ada error...
                } catch (e: Exception) {
                    // tampilkan error nya via Toast
                    Toast.makeText(requireContext(), "An error has ocurred: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.d("ERR_REGISTER", e.message.toString())
                }
            }

            // jika ada input yang masih kosong...
        } else {
            // tampilkan pesan
            Toast.makeText(requireContext(), "Semua input harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}