package math

data class Mat3(
    val x: Vec3 = Vec3(),
    val y: Vec3 = Vec3(),
    val z: Vec3 = Vec3(),
    ){
    operator fun get(index: Int) = when(index) {
        0 -> x; 1 -> y; 2 -> z
        else -> throw IndexOutOfBoundsException()
    }


    operator fun times(mat4: Mat4): Mat4 {
        val m = Mat4()
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                for (p in 0 until 3) {
                    m[i][j] += this[i][p] * mat4[p][j]
                }
            }
        }
        return m
    }
}