package de.htw_berlin.meetingmanager.addMeeting

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.htw_berlin.meetingmanager.R
import de.htw_berlin.meetingmanager.addMeeting.Person.PersonActivity
import de.htw_berlin.meetingmanager.databinding.PersonActivityBinding

import de.htw_berlin.meetingmanager.meeting.MeetingActivity
import kotlinx.android.synthetic.main.add_meeting_activity.*
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

const val MEETING_NAME = "name"
const val MEETING_TIME = "time"
const val MEETING_PLACE = "place"
const val MEETING_PARTICIPANTS = "participants"
const val MEETING_DESCRIPTION = "description"


class AddMeetingActivity : AppCompatActivity() {
    private lateinit var addMeetingName: TextInputEditText
    private lateinit var addMeetingPlace: TextInputEditText
    private lateinit var addMeetingDescription: TextInputEditText
    private lateinit var imageUri : Uri


    //Our Database
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var selected : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_meeting_activity)


        var format = SimpleDateFormat("dd MMM , YYYY" , Locale.GERMANY)
        var timeFormat = SimpleDateFormat("hh:mm a" ,Locale.GERMANY)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addMeeting()
            if(selected == true) {
                uploadImage()
            }
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            selectImage()
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            val now = Calendar.getInstance()

            val timePicker = TimePickerDialog(this , TimePickerDialog.OnTimeSetListener{view , hours , minutes ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY , hours)
                selectedTime.set(Calendar.MINUTE , minutes)

                Toast.makeText(this , "time : " + timeFormat.format(selectedTime.time) , Toast.LENGTH_SHORT).show()
                button2.text = timeFormat.format(selectedTime.time)

            },
                now.get(Calendar.HOUR_OF_DAY) ,now.get(Calendar.MINUTE), false )

            timePicker.show()
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            val now =Calendar.getInstance()
            val datePicker = DatePickerDialog(this , DatePickerDialog.OnDateSetListener{
                    view , year , month , dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR ,year)
                selectedDate.set(Calendar.MONTH ,month)
                selectedDate.set(Calendar.DAY_OF_MONTH ,dayOfMonth)
                val date = format.format(selectedDate.time)
                Toast.makeText(this , "date : $date", Toast.LENGTH_SHORT).show()
                button3.text = date
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()


        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(this  , PersonActivity::class.java))
        }

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            startActivity(Intent(this , MeetingActivity::class.java))
        }

        addMeetingName = findViewById(R.id.add_meeting_name)
        addMeetingPlace = findViewById(R.id.add_meeting_place)
        addMeetingDescription = findViewById(R.id.add_meeting_description)

    }




    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addMeeting() {

        if (addMeetingName.text.isNullOrEmpty() ||addMeetingPlace.text.isNullOrEmpty() || addMeetingDescription.text.isNullOrEmpty() ) {
            Toast.makeText(this , "Don't leave fields empty please !"  , Toast.LENGTH_SHORT).show()
        } else {

            val Meeting  = hashMapOf(
                MEETING_NAME to  addMeetingName.text.toString() ,
                MEETING_TIME to  button3.text.toString() + " " + button2.text.toString()  ,
                MEETING_PLACE to addMeetingPlace.text.toString() ,
                MEETING_PARTICIPANTS to "Participants" ,
                MEETING_DESCRIPTION to addMeetingDescription.text.toString() )

            db.collection("Meetings").add(Meeting)
                .addOnSuccessListener {
                    Toast.makeText(this , "Completed "  , Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                }

        }
       startActivity(Intent(this , MeetingActivity::class.java))
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

    private fun uploadImage(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File...")
        progressDialog.setCancelable(false)
        progressDialog.show()


        val filename  = button3.text.toString() + " " + button2.text.toString()
        val storageReference = FirebaseStorage.getInstance().getReference("meetingsImage/$filename")
        storageReference.putFile(imageUri)
            .addOnSuccessListener {
                if(progressDialog.isShowing) progressDialog.dismiss()
            }
            .addOnFailureListener{
                if(progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this , "error uploading the image " , Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            findViewById<ImageView>(R.id.imageView).setImageURI(imageUri)
        }
    }

}

