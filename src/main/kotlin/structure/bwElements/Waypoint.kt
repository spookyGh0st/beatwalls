package structure.bwElements

data class Waypoint<T: Any>(
        val t: Double,
        val value: T,
        val easing: String? = null,
){
        fun toList(a: (t: T) -> List<Double>): List<Any> {
                val l = mutableListOf<Any>()
                l.addAll(a(value))
                l.add(t)
                if (easing!= null) l.add(easing)
                return l.toList()
        }
}