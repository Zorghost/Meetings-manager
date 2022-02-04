package de.htw_berlin.meetingmanager.meeting

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.htw_berlin.meetingmanager.R
import de.htw_berlin.meetingmanager.addMeeting.AddMeetingActivity
import kotlinx.android.synthetic.main.activity_meeting_detail.*
import kotlinx.android.synthetic.main.meeting_raw_layout.*
import java.io.File

class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
class MeetingActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private val newMeetingActivityRequestCode = 1
    private lateinit var rvMeetings : RecyclerView
    private lateinit var i : Intent

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
                val tvImage : ImageView = holder.itemView.findViewById(R.id.rowImageMeeting)

                tvName.text = model.name
                tvEmojis.text = model.time
                val imageName = model.time
                val storageRef = FirebaseStorage.getInstance().reference.child("meetingsImage/$imageName")

                val localfile = File.createTempFile("tempImage" , "jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    tvImage.setImageBitmap(bitmap)

                }
                    .addOnFailureListener{
                        tvImage.setImageResource(R.drawable.button)
                    }


                   i = Intent(this@MeetingActivity , meetingDetail::class.java)


                   holder.itemView.setOnClickListener{
                       i.putExtra("name", model.name)
                       i.putExtra("description" , model.description)
                       startActivity(i)
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