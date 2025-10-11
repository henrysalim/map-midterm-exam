package com.example.midtermexam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditProfile = view.findViewById<View>(R.id.acc_edtprof)
        val btnChangePassword = view.findViewById<View>(R.id.acc_edtpw)
        val btnAbout = view.findViewById<View>(R.id.acc_about)
        val btnTnc = view.findViewById<View>(R.id.acc_tnc)
        val btnLogout = view.findViewById<View>(R.id.acc_logout)

        addClickEffect(btnEditProfile) {
            findNavController().navigate(R.id.action_ProfileFragment_to_EditProfileFragment)
        }

        addClickEffect(btnChangePassword) {
            findNavController().navigate(R.id.action_ProfileFragment_to_ChangePasswordFragment)
        }

        addClickEffect(btnAbout) {
            findNavController().navigate(R.id.action_ProfileFragment_to_AboutFragment)
        }

        addClickEffect(btnTnc) {
            findNavController().navigate(R.id.action_ProfileFragment_to_TncFragment)
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
}
