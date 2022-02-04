package de.htw_berlin.meetingmanager.addMeeting.Person
import de.htw_berlin.meetingmanager.addMeeting.Person.Person
import de.htw_berlin.meetingmanager.addMeeting.Person.Profile

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
import java.io.File

class PersonViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
class PersonActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var rvPersons : RecyclerView
    private lateinit var d : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_activity)
        rvPersons = findViewById(R.id.rcyPerson)
        val query : CollectionReference = db.collection("Users")
        val options = FirestoreRecyclerOptions.Builder<Person>().setQuery(query , Person::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<Person, PersonViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_raw_layout, parent, false)
                return PersonViewHolder(view)
            }

            override fun onBindViewHolder(holder: PersonViewHolder, position: Int, model: Person) {
                val tvName : TextView = holder.itemView.findViewById(R.id.personName)
                val tvEmojis : TextView = holder.itemView.findViewById(R.id.personPosition)
                val tvImagePerson : ImageView = holder.itemView.findViewById(R.id.rowImagePerson)

                tvName.text = "   " + model.firstname + " "+ model.lastname
                tvEmojis.text = "   " + model.position

                val imageName = model.email
                val storageRef = FirebaseStorage.getInstance().reference.child("usersImage/$imageName")

                val localfile = File.createTempFile("tempImage" , "jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    tvImagePerson.setImageBitmap(bitmap)

                }
                    .addOnFailureListener{
                        tvImagePerson.setImageResource(R.drawable.person)
                    }


                d = Intent(this@PersonActivity , Profile::class.java)


                holder.itemView.setOnClickListener{
                    d.putExtra("firstname", model.firstname)
                    d.putExtra("lastname" , model.lastname)
                    d.putExtra("email" , model.email)
                    d.putExtra("position" , model.position)
                    d.putExtra("description" , model.age)
                    startActivity(d)
                }
            }
        }
        rvPersons.adapter = adapter
        rvPersons.layoutManager = LinearLayoutManager(this)





    }



}
