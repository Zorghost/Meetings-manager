package de.htw_berlin.meetingmanager.addMeeting.Person

import androidx.annotation.DrawableRes
import de.htw_berlin.meetingmanager.R

data class Person (
    val id: Long = -1,
    val email : String = " " ,
    val username: String = " ",
    val age : Int = 5 ,
    val firstname : String = " ",
    val lastname : String =" ",
    val position: String = " ",
    @DrawableRes
    val image: Int? = R.drawable.person
){

    override fun toString(): String {
        return "$firstname $lastname ($position)"
    }
}
