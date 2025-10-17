package com.example.midtermexam.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.midtermexam.R
import com.example.midtermexam.databinding.FragmentEditProfileBinding
import com.example.midtermexam.model.AuthViewModel

class EditProfileFragment : Fragment() {

    // View binding untuk mengakses elemen UI di layout fragment_edit_profile.xml
    private lateinit var binding: FragmentEditProfileBinding

    // Menggunakan shared ViewModel (AuthViewModel) agar data bisa diakses antar fragment
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout dan inisialisasi binding
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Menampilkan data nama, username, dan email pengguna dari ViewModel ke dalam input form
        authViewModel.fullName.observe(viewLifecycleOwner) { name ->
            binding.firstnameEditText.setText(name ?: "")
        }

        authViewModel.username.observe(viewLifecycleOwner) { uname ->
            binding.usernameEditText.setText(uname ?: "")
        }

        authViewModel.email.observe(viewLifecycleOwner) { email ->
            binding.emailEditText.setText(email ?: "")
        }

        // 🔹 Tombol Cancel: kembali ke halaman Profile tanpa menyimpan perubahan
        binding.cancelbtn.setOnClickListener {
            findNavController().navigate(R.id.action_EditProfileFragment_to_ProfileFragment)
        }

        // Tombol save changes belum ada function/logicnya karena belum menyentuh tahap backend
    }
}
