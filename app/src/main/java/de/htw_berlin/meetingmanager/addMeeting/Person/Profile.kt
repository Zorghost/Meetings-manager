package de.htw_berlin.meetingmanager.addMeeting.Person

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import de.htw_berlin.meetingmanager.R

class Profile : AppCompatActivity() {
    private lateinit var Name : TextView
    private lateinit var Email : TextView
    private lateinit var Position : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Name = findViewById(R.id.FirstSecondName)
        Email = findViewById(R.id.emailOutput)
        Position = findViewById(R.id.position)

        Name.text = intent.getStringExtra("firstname") +" " +intent.getStringExtra("lastname")
        Email.text = intent.getStringExtra("email")
        Position.text = intent.getStringExtra("position")

        findViewById<Button>(R.id.button7).setOnClickListener {
            startActivity(Intent(this , PersonActivity::class.java))
        }
    }
}
