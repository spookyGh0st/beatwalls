package structure.math

data class Mat3(
    val x: Vec3 = Vec3(),
    val y: Vec3 = Vec3(),
    val z: Vec3 = Vec3(),
    ){
    operator fun get(index: Int) = when(index) {
        0 -> x; 1 -> y; 2 -> z
        else -> throw IndexOutOfBoundsException()
    }

    operator fun times(mat3: Mat3): Mat3 = Mat3(
        Vec3(
            x.x * mat3.x.x + x.y * mat3.y.x + x.z * mat3.z.x,
            x.x * mat3.x.y + x.y * mat3.y.y + x.z * mat3.z.y,
            x.x * mat3.x.z + x.y * mat3.y.z + x.z * mat3.z.z,
        ), Vec3(
            y.x * mat3.x.x + y.y * mat3.y.x + y.z * mat3.z.x,
            y.x * mat3.x.y + y.y * mat3.y.y + y.z * mat3.z.y,
            y.x * mat3.x.z + y.y * mat3.y.z + y.z * mat3.z.z,
        ), Vec3(
            z.x * mat3.x.x + z.y * mat3.y.x + z.z * mat3.z.x,
            z.x * mat3.x.y + z.y * mat3.y.y + z.z * mat3.z.y,
            z.x * mat3.x.z + z.y * mat3.y.z + z.z * mat3.z.z,

            )
    )
}