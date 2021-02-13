package math

data class Mat4(
    val x: Vec4 = Vec4(),
    val y: Vec4 = Vec4(),
    val z: Vec4 = Vec4(),
    val w: Vec4 = Vec4(),
){
    operator fun get(index: Int) = when(index) {
        0 -> x; 1 -> y; 2 -> z; 3 -> w
        else -> throw IndexOutOfBoundsException()
    }

    operator fun times(mat4: Mat4): Mat4 {
        val m = Mat4()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                for (p in 0 until 4) {
                    m[i][j] += this[i][p] * mat4[p][j]
                }
            }
        }
        return m
    }

    operator fun times(vec4: Vec4): Vec4 =
        Vec4(
            x.x * vec4.x + x.y * vec4.y + x.z * vec4.z + x.w * vec4.w,
            y.x * vec4.x + y.y * vec4.y + y.z * vec4.z + y.w * vec4.w,
            z.x * vec4.x + z.y * vec4.y + z.z * vec4.z + z.w * vec4.w,
            w.x * vec4.x + w.y * vec4.y + w.z * vec4.z + w.w * vec4.w,
        )

    fun trace() = 1 + this[0][0] + this[1][1] + this[2][2]
}
