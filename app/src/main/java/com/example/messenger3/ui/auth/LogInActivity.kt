package com.example.messenger3.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.messenger3.R
import com.example.messenger3.ui.home.MainActivity
import com.example.messenger3.utils.toast
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        sign_up_log_in.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        log_in.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val email = email_log_in.text.toString()
        val password = password_log_in.text.toString()

//      Login returns 2 values: state of registration, message on exception
        LogInViewModel.loginUser(email, password)
        LogInViewModel.loginLiveData.observe(this, Observer {result ->
            if (result.first){
                Log.d("Messenger", "Succesfully logged in")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{
                Log.d("Messenger", "Failure")
                toast(result.second)
            }
        })

    }
}
