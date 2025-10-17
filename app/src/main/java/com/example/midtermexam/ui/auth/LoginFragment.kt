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
    private val authViewModel: AuthViewModel by activityViewModels() // definisikan authViewModel untuk menyimpan data auth di sana
    private lateinit var successMessage: String // nantinya pesan registrasi berhasil akan ditaruh di sini

    // ambil input email dan password dari fragment dan juga tombol login
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

        // saat login button ditekan, arahkan ke fungsi login()
        loginBtn.setOnClickListener { login() }
    }

    private fun login() {
        // ambil value email dan password dari input
        val email = email.text.toString()
        val password = password.text.toString()
        val storedEmail = authViewModel.email
        // untuk saat ini, password belum dihash
        val storedPass = authViewModel.password

        if (email.isNotBlank() && password.isNotBlank()) { // jika email dan password tidak kosong...
            if (password.length < 8) // jika panjang password kurang dari 8 karakter, tampilkan pesan dengan Toast
                Toast.makeText(
                    requireContext(),
                    "Password min. 8 karakter",
                    Toast.LENGTH_SHORT
                )
                    .show()
            else { //jika panjang password sudah aman
                if (storedEmail.value.equals(email) && storedPass.value.equals(password)) { // cek apakah data sesuai dengan yang disimpan pada authViewModel
                    val fullName = authViewModel.fullName.value.toString() // ambil nama lengkap dari authViewModel
                    authViewModel.setIsLoggedIn(true) // set isLoggedIn menjadi true
                    authViewModel.setSuccessMessage("Selamat datang ${fullName}!") // set succesMessage yang akan ditampilkan di halaman Home
                    findNavController().navigate(R.id.action_LoginFragment_to_HomeFragment) // alihkan halaman ke Home
                } else { // jika email/password tidak sesuai
                    // tampilkan pesan error dengan Toast
                    Toast.makeText(requireContext(), "Email atau password invalid", Toast.LENGTH_SHORT).show()
                }
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