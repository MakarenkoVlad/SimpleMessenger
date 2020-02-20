package com.example.messenger3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            doSignUp()
        }

        log_in_button_sign_in_activity.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
//        Toast.makeText(this, "${FirebaseAuth.getInstance().currentUser?.email}", Toast.LENGTH_LONG).show()
    }


    private fun doSignUp(){
        val email = email_sign_in.text.toString()
        val password = password_sign_in.text.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("Messenger", "Register state: success")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                Log.w("Messenger", "Register state: ${it.message}")
                Toast.makeText(this,  "Registration failed: ${it.message}", Toast.LENGTH_LONG).show()
            }

    }

}
