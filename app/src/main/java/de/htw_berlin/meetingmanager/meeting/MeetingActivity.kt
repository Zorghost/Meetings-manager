package de.htw_berlin.meetingmanager.meeting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.htw_berlin.meetingmanager.R
import de.htw_berlin.meetingmanager.Register.RegisterActivity
import de.htw_berlin.meetingmanager.addMeeting.AddMeetingActivity
import kotlinx.android.synthetic.main.activity_meeting_detail.*

class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
class MeetingActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private val newMeetingActivityRequestCode = 1
    private lateinit var rvMeetings : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting)
        rvMeetings = findViewById(R.id.rvMeetings)
        val query : CollectionReference = db.collection("Meetings")
        val options = FirestoreRecyclerOptions.Builder<Meeting>().setQuery(query , Meeting::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<Meeting, UserViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.meeting_raw_layout, parent, false)
                return UserViewHolder(view)
            }
                override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: Meeting) {
                val tvName : TextView = holder.itemView.findViewById(R.id.tv1)
                val tvEmojis : TextView = holder.itemView.findViewById(R.id.tv2)
                tvName.text = model.name
                tvEmojis.text = model.time

                    holder.itemView.setOnClickListener{
                        startActivity(Intent(this@MeetingActivity,
                            meetingDetail::class.java
                        ))
                    }
            }
        }
        rvMeetings.adapter = adapter
        rvMeetings.layoutManager = LinearLayoutManager(this)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }

    }

    private fun fabOnClick() {
        val intent = Intent(this, AddMeetingActivity::class.java)
        startActivityForResult(intent, newMeetingActivityRequestCode)
    }

}