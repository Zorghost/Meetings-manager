package de.htw_berlin.meetingmanager.Register

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

import de.htw_berlin.meetingmanager.addMeeting.*

import android.view.View
import de.htw_berlin.meetingmanager.R
import de.htw_berlin.meetingmanager.Login.LoginActivity
import de.htw_berlin.meetingmanager.meeting.MeetingActivity


const val PERSON_EMAIL = "email"
const val PERSON_PASSWORD = "password"
const val PERSON_USERNAME = "username"
const val PERSON_FIRSTNAME = "firstname"
const val PERSON_LASTNAME  = "lastname"
const val PERSON_POSITION = "position"
const val PERSON_AGE = 18

class RegisterActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var etUsername: EditText
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etPosition: EditText
    private lateinit var etAge: EditText

    private lateinit var btnSignUp: Button

    // create Firebase authentication object and an Instance to our firebase database
    private lateinit var auth: FirebaseAuth
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // View Bindings
        etEmail = findViewById(R.id.editTextEmail)
        etPass = findViewById(R.id.editTextPassword)
        etUsername = findViewById(R.id.editTextUsername)
        etFirstname = findViewById(R.id.editTextFirstname)
        etLastname = findViewById(R.id.editTextLastname)
        etPosition = findViewById(R.id.editTextPosition)
        etAge = findViewById(R.id.editTextAge)

        btnSignUp = findViewById(R.id.cirRegisterButton)

        // Initialising auth object
        auth = Firebase.auth

        btnSignUp.setOnClickListener {
            signUpUser()
        }


    }

    private fun signUpUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val username =etUsername.text.toString()
        val firstname = etFirstname.text.toString()
        val lastname = etLastname.text.toString()
        val position = etPosition.text.toString()
        val age = etAge.text.toString()
       val ageValue : Int = age.toInt()
        // check pass
        if (email.isBlank() || pass.isBlank() || username.isBlank() || firstname.isBlank() || lastname.isBlank() || position.isBlank()|| age.isBlank() ) {
            Toast.makeText(this, "Please fill all the data", Toast.LENGTH_SHORT).show()
            return
        }


        // If all credential are correct
        // We call createUserWithEmailAndPassword
        // using auth object and pass the
        // email and pass in it.
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                addPerson( )
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MeetingActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun addPerson()
    {
        val Person  = hashMapOf(
            PERSON_EMAIL to  etEmail.text.toString() ,
            PERSON_PASSWORD to  etPass.text.toString() ,
            PERSON_FIRSTNAME to  etFirstname.text.toString() ,
            PERSON_LASTNAME to  etLastname.text.toString() ,
            PERSON_POSITION to  etPosition.text.toString() ,
            PERSON_USERNAME to  etUsername.text.toString() ,
            PERSON_AGE to etAge.text.toString().toInt()   )

        db.collection("Users").add(Person)

    }
    fun onLoginClick(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

}