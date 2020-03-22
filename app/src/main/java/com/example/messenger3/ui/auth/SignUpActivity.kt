package com.example.messenger3.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.messenger3.R
import com.example.messenger3.ui.home.MainActivity
import com.example.messenger3.utils.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        FirebaseAuth.getInstance().currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        sign_up_button.setOnClickListener {
            signup()
        }

        log_in_button_sign_in_activity.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }


    private fun signup(){
        val email = email_sign_in.text.toString()
        val password = password_sign_in.text.toString()

//      Registration returns 2 values: state of registration, message on exception
        SignUpViewModel.registerUser(email, password)
        SignUpViewModel.signupLiveData.observe(this, Observer {result ->
            if (result.first){
                Log.d("Messenger", "Register state: success")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{
                Log.w("Messenger", "Register state: ${result.second}")
                toast("Registration failed: ${result.second}")
            }
        })


    }

}
