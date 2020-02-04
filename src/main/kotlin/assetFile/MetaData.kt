package assetFile

import beatwalls.readBpm
import beatwalls.readHjsDuration
import beatwalls.readOffset

class MetaData(val bpm: Double, val hjd: Double, val offset: Double)

fun readAsset() =
    MetaData(
        readBpm(),
        readHjsDuration(),
        readOffset()
    )


