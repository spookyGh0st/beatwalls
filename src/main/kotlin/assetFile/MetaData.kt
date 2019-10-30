package assetFile

import com.github.spookyghost.beatwalls.readBpm
import com.github.spookyghost.beatwalls.readHjsDuration
import com.github.spookyghost.beatwalls.readOffset

class MetaData(val bpm: Double, val hjd:Double, val offset:Double ){
}

fun readAsset() =
    MetaData(readBpm(), readHjsDuration(), readOffset())


