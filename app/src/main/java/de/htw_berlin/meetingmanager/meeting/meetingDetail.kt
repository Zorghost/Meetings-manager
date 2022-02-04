package de.htw_berlin.meetingmanager.meeting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import de.htw_berlin.meetingmanager.R

class meetingDetail : AppCompatActivity() {
    private lateinit var detailName : TextView
    private lateinit var detailDescription : TextView
    private lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting_detail)
        detailName = findViewById(R.id.textViewDetail)
        detailDescription = findViewById(R.id.textViewDetail2)
        button = findViewById(R.id.backButton)


         detailName.text = intent.getStringExtra("name")
        detailDescription.text = intent.getStringExtra("description")




        button.setOnClickListener {
            startActivity(Intent(this , MeetingActivity::class.java))
        }
    }
}