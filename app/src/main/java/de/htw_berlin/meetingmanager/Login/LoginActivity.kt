package de.htw_berlin.meetingmanager.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.htw_berlin.meetingmanager.R
import android.view.View

import de.htw_berlin.meetingmanager.Register.RegisterActivity
import de.htw_berlin.meetingmanager.meeting.MeetingActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText

    private lateinit var etPass: EditText
    private lateinit var btnLogIn: Button

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // View Bindings
        etEmail = findViewById(R.id.editTextEmail)
        etPass = findViewById(R.id.editTextPassword)
        btnLogIn = findViewById(R.id.cirLoginButton)
        auth = Firebase.auth

        btnLogIn.setOnClickListener {
            logInUser()
        }

    }
    private fun logInUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        // check pass
        if (email.isBlank() || pass.isBlank() ) {
            Toast.makeText(this, "Email or Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }


        // If all credential are correct
        // We call createUserWithEmailAndPassword
        // using auth object and pass the
        // email and pass in it.

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this , MeetingActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Logging In Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun onLoginClick(View: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
        overridePendingTransition(de.htw_berlin.meetingmanager.R.anim.slide_in_right, de.htw_berlin.meetingmanager.R.anim.stay)
    }
}