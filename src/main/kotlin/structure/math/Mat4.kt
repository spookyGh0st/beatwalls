package structure.math

data class Mat4(
    val x: Vec4 = Vec4(),
    val y: Vec4 = Vec4(),
    val z: Vec4 = Vec4(),
    val t: Vec4 = Vec4(),
    ){
    operator fun get(index: Int) = when(index) {
        0 -> x; 1 -> y; 2 -> z; 3 -> t
        else -> throw IndexOutOfBoundsException(index)
    }

    operator fun times(mat4: Mat4): Mat4 = Mat4(
        Vec4(
            x.x * mat4.x.x + x.y * mat4.y.x + x.z * mat4.z.x,
            x.x * mat4.x.y + x.y * mat4.y.y + x.z * mat4.z.y,
            x.x * mat4.x.z + x.y * mat4.y.z + x.z * mat4.z.z,
            x.x * mat4.x.t + x.y * mat4.y.t + x.z * mat4.z.t,
        ), Vec4(
            y.x * mat4.x.x + y.y * mat4.y.x + y.z * mat4.z.x,
            y.x * mat4.x.y + y.y * mat4.y.y + y.z * mat4.z.y,
            y.x * mat4.x.z + y.y * mat4.y.z + y.z * mat4.z.z,
            y.x * mat4.x.t + y.y * mat4.y.t + y.z * mat4.z.t,
        ), Vec4(
            z.x * mat4.x.x + z.y * mat4.y.x + z.z * mat4.z.x,
            z.x * mat4.x.y + z.y * mat4.y.y + z.z * mat4.z.y,
            z.x * mat4.x.z + z.y * mat4.y.z + z.z * mat4.z.z,
            z.x * mat4.x.t + z.y * mat4.y.t + z.z * mat4.z.t,
        ),
        Vec4(
            t.x * mat4.x.x + t.y * mat4.y.x + t.z * mat4.z.x,
            t.x * mat4.x.y + t.y * mat4.y.y + t.z * mat4.z.y,
            t.x * mat4.x.z + t.y * mat4.y.z + t.z * mat4.z.z,
            t.x * mat4.x.t + t.y * mat4.y.t + t.z * mat4.z.t,
        ),
    )
}