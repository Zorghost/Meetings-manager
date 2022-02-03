package de.htw_berlin.meetingmanager.addMeeting

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import de.htw_berlin.meetingmanager.R
import de.htw_berlin.meetingmanager.addMeeting.Person.PersonActivity
import kotlinx.android.synthetic.main.add_meeting_activity.*
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
    private lateinit var addMeetingParticipants : TextInputEditText
    private lateinit var addMeetingDescription: TextInputEditText


    //Our Database
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_meeting_activity)
        var format = SimpleDateFormat("dd MMM , YYYY" , Locale.GERMANY)
        var timeFormat = SimpleDateFormat("hh:mm a" ,Locale.GERMANY)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addMeeting()
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
        addMeetingName = findViewById(R.id.add_meeting_name)
        addMeetingPlace = findViewById(R.id.add_meeting_place)
        addMeetingDescription = findViewById(R.id.add_meeting_description)

    }



    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addMeeting() {
        val resultIntent = Intent()

        if (addMeetingName.text.isNullOrEmpty() ||addMeetingPlace.text.isNullOrEmpty() || addMeetingDescription.text.isNullOrEmpty() ) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
            Toast.makeText(this , "Don't leave fields empty please !"  , Toast.LENGTH_SHORT).show()
        } else {
            val Meeting  = hashMapOf(
                MEETING_NAME to  addMeetingName.text.toString() ,
                MEETING_TIME to  button3.text.toString() + " " + button2.text.toString()  ,
                MEETING_PLACE to addMeetingPlace.text.toString() ,
                MEETING_PARTICIPANTS to addMeetingParticipants.text.toString() ,
                MEETING_DESCRIPTION to addMeetingDescription.text.toString() )

            db.collection("Meetings").add(Meeting)
                .addOnSuccessListener {
                    Toast.makeText(this , "Completed "  , Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {e ->
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(ContentValues.TAG, e.toString());
                }

        }
        finish()
    }
}

