package com.example.midtermexam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.midtermexam.R
import com.example.midtermexam.databinding.FragmentProfileBinding
import com.example.midtermexam.model.AuthViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {

    // View binding untuk mengakses komponen di layout XML
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // ViewModel untuk mengambil data autentikasi pengguna (nama, username, dan email)
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout fragment_profile.xml ke dalam binding
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Observasi LiveData dari AuthViewModel untuk menampilkan nama pengguna
        authViewModel.fullName.observe(viewLifecycleOwner) { name ->
            binding.accName.text = name ?: "Nama belum diisi"
        }

        // 🔹 Navigasi ke halaman Edit Profile
        addClickEffect(binding.accEdtprof) {
            findNavController().navigate(R.id.action_ProfileFragment_to_EditProfileFragment)
        }

        // 🔹 Navigasi ke halaman Change Password
        addClickEffect(binding.accEdtpw) {
            findNavController().navigate(R.id.action_ProfileFragment_to_ChangePasswordFragment)
        }

        // 🔹 Navigasi ke halaman About
        addClickEffect(binding.accAbout) {
            findNavController().navigate(R.id.action_ProfileFragment_to_AboutFragment)
        }

        // 🔹 Navigasi ke halaman Privacy Policy
        addClickEffect(binding.accTnc) {
            findNavController().navigate(R.id.action_ProfileFragment_to_TncFragment)
        }

        // 🔹 Tombol Logout dengan konfirmasi
        addClickEffect(binding.accLogout) {
            showLogoutConfirmationDialog()
        }
    }

    /**
     * Fungsi untuk menambahkan efek animasi ripple saat tombol disentuh.
     * - Scaling button mengecil untuk memberikan efek visual bahwa button sedang di klik.
     * - Mengembalikan ukuran semula saat dilepas (Bouncing).
     * Fungsi ini juga menetapkan aksi (onClick).
     */
    private fun addClickEffect(view: View, onClick: () -> Unit) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> v.animate().scaleX(0.97f).scaleY(0.97f).setDuration(100).start()
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
            }
            false // biar event click tetap berfungsi
        }
        view.setOnClickListener { onClick() }
    }

    /**
     * Menampilkan dialog konfirmasi saat pengguna menekan tombol Logout.
     */
    private fun showLogoutConfirmationDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout Confirmation")
            .setMessage("Are you sure you want to Logout?")
            .setPositiveButton("Yes, Log me Out") { d, _ ->
                // Karena belum terhubung total dengan backend, oleh karena itu opsi ini akan mengclose pop up nya juga //
                d.dismiss()
            }
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
            .create()

        dialog.show()
    }

    // Bersihkan binding untuk mencegah memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
