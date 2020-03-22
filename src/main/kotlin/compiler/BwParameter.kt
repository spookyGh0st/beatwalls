package compiler

interface BwParameter {
    var field: Any
    operator fun invoke(s: String)
    val repeatAdd: BwParameter
    val repeatScale: BwParameter
    fun repeat() {
    }
}

