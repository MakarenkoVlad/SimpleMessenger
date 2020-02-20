package com.example.messenger3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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
            val email = email_log_in.text.toString()
            val password = password_log_in.text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                        Log.d("Messenger", "Succesfully logged in")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                }
                .addOnFailureListener {
                    Log.d("Messenger", "Failure: ${it.message}")
                    Toast.makeText(this, "Failure: ${it.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
