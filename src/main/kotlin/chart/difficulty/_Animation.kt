package chart.difficulty
typealias PointDefinition = List<List<Any>>

@Suppress("ClassName")
data class _Animation(
    var _position: PointDefinition? = null,
    var _definitePosition: PointDefinition? = null,
    var _rotation: PointDefinition? = null,
    var _localRotation: PointDefinition? = null,
    var _scale: PointDefinition? = null,
    var _dissolve: PointDefinition? = null,
    var _dissolveArrow: PointDefinition? = null,
    var _color: PointDefinition? = null,
    var _interactable: PointDefinition? = null,
)
