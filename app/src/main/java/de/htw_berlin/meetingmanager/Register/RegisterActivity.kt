package de.htw_berlin.meetingmanager.Register

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
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
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import de.htw_berlin.meetingmanager.R
import de.htw_berlin.meetingmanager.Login.LoginActivity
import de.htw_berlin.meetingmanager.meeting.MeetingActivity
import java.text.SimpleDateFormat
import java.util.*


const val PERSON_EMAIL = "email"
const val PERSON_USERNAME = "username"
const val PERSON_FIRSTNAME = "firstname"
const val PERSON_LASTNAME  = "lastname"
const val PERSON_POSITION = "position"
const val PERSON_AGE = "age"

class RegisterActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var etUsername: EditText
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etPosition: EditText
    private lateinit var etAge: EditText
    private lateinit var imageUri : Uri

    private lateinit var btnSignUp: Button

    // create Firebase authentication object and an Instance to our firebase database
    private lateinit var auth: FirebaseAuth
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var selected :Boolean = false
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
            if(selected == true) {
                uploadImage()
            }
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            selectImage()
        }

    }
    private fun selectImage()
    {
        val intent = Intent()
        intent.type ="image/*"
        intent.action = Intent.ACTION_GET_CONTENT
      if( intent.action.toBoolean())
      {
          selected = true
      }
        startActivityForResult(intent , 100)

    }
    private fun uploadImage()
    {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File...")
        progressDialog.setCancelable(false)
        progressDialog.show()


        val filename  = etEmail.text.toString()
        val storageReference = FirebaseStorage.getInstance().getReference("usersImage/$filename")
        storageReference.putFile(imageUri)
            .addOnSuccessListener {
                if(progressDialog.isShowing) progressDialog.dismiss()
            }
            .addOnFailureListener{
                if(progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this , "error uploading the image " , Toast.LENGTH_SHORT).show()
            }
    }
    private fun signUpUser() {
        // check pass
        if (etEmail.text.isBlank() || etPass.text.isBlank() || etUsername.text.isBlank() || etFirstname.text.isBlank() || etLastname.text.isBlank() || etPosition.text.isBlank()|| etAge.text.isBlank() ) {
            Toast.makeText(this, "Please fill all the data", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword( etEmail.text.toString() , etPass.text.toString()).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                addPerson( )
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
               startActivity(Intent(this, MeetingActivity::class.java))
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addPerson()
    {
        val person  = hashMapOf(
            PERSON_EMAIL to  etEmail.text.toString() ,
            PERSON_FIRSTNAME to  etFirstname.text.toString() ,
            PERSON_LASTNAME to  etLastname.text.toString() ,
            PERSON_POSITION to  etPosition.text.toString() ,
            PERSON_USERNAME to  etUsername.text.toString() ,
            PERSON_AGE to etAge.text.toString()
        )

        db.collection("Users").add(person)

    }
    fun onLoginClick(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            findViewById<ImageView>(R.id.imageView5).setImageURI(imageUri)
        }
    }
}