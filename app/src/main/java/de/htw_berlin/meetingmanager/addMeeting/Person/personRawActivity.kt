package de.htw_berlin.meetingmanager.addMeeting.Person


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.htw_berlin.meetingmanager.R


class PersonRawActivity : AppCompatActivity() {
private lateinit var layout : LinearLayout
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_raw_layout)

        layout = findViewById(R.id.layoutPerson)
layout.setOnClickListener{
    startActivity(Intent(this , Profile::class.java))
}
    }


}
