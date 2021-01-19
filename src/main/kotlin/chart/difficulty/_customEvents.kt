package model.difficulty

import com.google.gson.annotations.SerializedName


data class _customEvents (
    var _time: Float,
    var _type: String,
    var _data: Any,  // todo add when working with Animation https://github.com/Aeroluna/NoodleExtensions/blob/master/Documentation/AnimationDocs.md
)
