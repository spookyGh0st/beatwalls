package chart.difficulty

import assetFile.MetaData
import mu.KotlinLogging
import structure.Define
import structure.helperClasses.SpookyWall
import structure.WallStructure
import structure.wallbender.generateBendAndRepeatWalls
import kotlin.math.ceil

private val logger = KotlinLogging.logger {}

class BpmAdjuster(diff: Difficulty, private val meta: MetaData) {
    private val baseBpm: Double = meta.bpm
    private val changes: ArrayList<Pair<_BPMChanges, Double>> = mapChangesToBeat(diff._customData._BPMChanges)

    private fun mapChangesToBeat(changes: ArrayList<_BPMChanges>): ArrayList<Pair<_BPMChanges, Double>> {
        val l = arrayListOf(_BPMChanges(baseBpm, 0.0, 4, 4) to 0.0)
        var beat = 0.0
        changes.forEach {
            val c = l.last().first
            val traversedBeats = (it._time - c._time) / baseBpm * c._BPM
            beat += ceil(traversedBeats)
            l.add(it to beat)
        }
        l.sortedBy { it.second }
        return l
    }

    fun generate(w: WallStructure): List<_obstacles> {
        val walls = w.generateBendAndRepeatWalls()

        // adjusts the neccesary values
        walls.forEach { it.startTime += w.beat }
        walls.forEach { it.adjustToBPM() }
        walls.forEach { it.addOffset() }

        // adds the njsOffset if time is true
        if (w.time)
            walls.forEach { it.startTime += meta.hjd }

        if (w !is Define || w.isTopLevel)
            logger.info { "Added ${w.name()} with ${w.spookyWalls.size} walls on beat ${w.beat}." }
        // creates Obstacles
        return walls.map { it.to_obstacle(meta.hjd) }
    }

    private fun SpookyWall.addOffset(){
        val offset = meta.bpm / 60000 * meta.offset
        this.startTime += offset
    }

    private fun SpookyWall.adjustToBPM(){
        val c = lastChange(this.startTime).first
        this.startTime = findTime(this.startTime)
        if (this.duration > 0)
            this.duration / baseBpm *c._BPM
    }

    fun findTime(time:Double): Double {
        val lastChangePair = lastChange(time)
        val change = lastChangePair.first
        val beat = lastChangePair.second
        return change._time + (time-beat) /baseBpm * change._BPM
    }

    private fun lastChange(beat: Double) =
         changes.last { it.second <= beat }
}