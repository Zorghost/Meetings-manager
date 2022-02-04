package de.htw_berlin.meetingmanager.meeting


data class Meeting(
    val id: Long = 0,
    val name: String = "",
    val place : String = "",
    val time : String = "",
    val participants : String ="",
    val description: String = ""
)

