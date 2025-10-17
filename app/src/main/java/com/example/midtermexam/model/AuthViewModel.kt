package com.example.midtermexam.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    /*
        buat variabel untuk menyimpan data register & login dummy dengan MutableLiveData agar
        data bersifat observable (jika berubah, ada listenernya dan kita dapat segera update)
    */
    var isLoggedIn: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var fullName: MutableLiveData<String?> = MutableLiveData<String?>(null)
    var username: MutableLiveData<String?> = MutableLiveData<String?>(null)
    var email: MutableLiveData<String?> = MutableLiveData<String?>(null)
    var password: MutableLiveData<String?> = MutableLiveData<String?>(null)
    var successMessage: MutableLiveData<String?> = MutableLiveData<String?>(null)

    // buat setter layaknya pada OOP umumnya
    fun setIsLoggedIn(value: Boolean) {
        isLoggedIn.value = value
    }

    fun setFullName(value: String?) {
        fullName.value = value
    }

    fun setUsername(value: String?) {
        username.value = value
    }

    fun setEmail(value: String?) {
        email.value = value
    }

    fun setPassword(value: String?) {
        password.value = value
    }

    fun setSuccessMessage(value: String?) {
        successMessage.value = value
    }
}