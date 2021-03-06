package interpreter

import chart.difficulty.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.ceil

class BpmAdjuster(val diff: Difficulty) {
    private val baseBpm: Double = diff.bpm
    private val changes: ArrayList<Pair<_BPMChanges, Double>> = mapChangesToBeat(diff._customData?._BPMChange)
    val offset = baseBpm / 60000 * diff.offset

    private fun mapChangesToBeat(bpmChanges: List<_BPMChanges>?): ArrayList<Pair<_BPMChanges, Double>> {
        val l = arrayListOf(_BPMChanges(baseBpm, 0.0, 4, 4) to 0.0)
        //if we have no bpm changes, we just use the default one
        if(bpmChanges == null)
            return l
        val sortedChanges = bpmChanges.sortedBy { it._time }

        var beat = 0.0
        sortedChanges.forEach {
            val c = l.last().first // last bpm change
            val traversedBeats = (it._time - c._time) / baseBpm * c._BPM
            beat += ceil(traversedBeats-0.01)
            l.add(it to beat)
        }
        l.sortedBy { it.second }
        return l
    }

    fun correctElements(obstacle: List<_obstacles>, notes: List<_notes>, events: List<_events>){
        runBlocking {
            launch { obstacle.forEach { correctObstacle(it) } }
            launch { notes.forEach { correctNote(it) } }
            launch { events.forEach { correctEvent(it) } }
        }
    }

    fun correctObstacle(element: _obstacles) {
        val c = lastChange(element._time)
        val time = c.first._time + (element._time-c.second) /baseBpm * c.first._BPM

        element._time = time + offset
        if (element._duration > 0)
            element._duration / baseBpm *c.first._BPM
    }

    fun correctNote(element: _notes) {
        val c = lastChange(element._time)
        val time = c.first._time + (element._time-c.second) /baseBpm * c.first._BPM

        element._time = time + offset
    }

    fun correctEvent(element: _events) {
        val c = lastChange(element._time)
        val time = c.first._time + (element._time-c.second) /baseBpm * c.first._BPM

        element._time = time + offset
    }

    fun findTime(time:Double): Double {
        val lastChangePair = lastChange(time)
        val change = lastChangePair.first
        val beat = lastChangePair.second
        return change._time + (time-beat) /baseBpm * change._BPM
    }

    private fun lastChange(beat: Double) =
         changes.lastOrNull { it.second <= beat }?: changes.last()
}