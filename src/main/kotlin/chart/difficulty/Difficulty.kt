package chart.difficulty

import java.io.File

data class Difficulty(
    val _version : String,
    val _events : List<_events>,
    val _notes : List<_notes>,
    val _obstacles : List<_obstacles>,
    val _waypoints : List<_waypoint>,
    val _customEvents : List<_customEvents>?,
    val _customData : _customData?,
){
    @Transient lateinit var file: File
    @Transient var bpm: Double = 0.0
    @Transient var offset: Double = 0.0
}
