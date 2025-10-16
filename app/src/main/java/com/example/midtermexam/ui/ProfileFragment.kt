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

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observasi data user
        authViewModel.fullName.observe(viewLifecycleOwner) { name ->
            binding.accName.text = name ?: "Nama belum diisi"
        }

        // Klik tombol navigasi pakai binding langsung
        addClickEffect(binding.accEdtprof) {
            findNavController().navigate(R.id.action_ProfileFragment_to_EditProfileFragment)
        }
        addClickEffect(binding.accEdtpw) {
            findNavController().navigate(R.id.action_ProfileFragment_to_ChangePasswordFragment)
        }
        addClickEffect(binding.accAbout) {
            findNavController().navigate(R.id.action_ProfileFragment_to_AboutFragment)
        }
        addClickEffect(binding.accTnc) {
            findNavController().navigate(R.id.action_ProfileFragment_to_TncFragment)
        }
        addClickEffect(binding.accLogout) {
            // logout logic di sini
        }
    }

    // efek klik biar tombol ada animasi kecil
    private fun addClickEffect(view: View, onClick: () -> Unit) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> v.animate().scaleX(0.97f).scaleY(0.97f).setDuration(100).start()
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
            }
            false
        }
        view.setOnClickListener { onClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
