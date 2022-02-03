package de.htw_berlin.meetingmanager.meeting

import androidx.annotation.DrawableRes
import de.htw_berlin.meetingmanager.R

data class Meeting(
    val id: Long = 0,
    val name: String = "",
    val place : String = "",
    val time : String = "",
    val participants : String ="",
    val description: String = "",
    @DrawableRes
val image: Int? = R.drawable.meeting
){
    
}