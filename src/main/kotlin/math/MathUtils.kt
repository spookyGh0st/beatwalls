package math

fun identityMat4() = Mat4(
    Vec4(1.0, 0.0, 0.0, 0.0),
    Vec4(0.0, 1.0, 0.0, 0.0),
    Vec4(0.0, 0.0, 1.0, 0.0),
    Vec4(0.0, 0.0, 0.0, 1.0),
)

fun translationMat4(v: Vec3) = Mat4(
    Vec4(1.0, 0.0, 0.0, v.x),
    Vec4(0.0, 1.0, 0.0, v.y),
    Vec4(0.0, 0.0, 1.0, v.z),
    Vec4(0.0, 0.0, 0.0, 1.0),
)

fun scalingMat4(v: Vec3) = Mat4(
    Vec4(v.x, 0.0, 0.0, 0.0),
    Vec4(0.0, v.y, 0.0, 0.0),
    Vec4(0.0, 0.0, v.z, 0.0),
    Vec4(0.0, 0.0, 0.0, 1.0),
)

operator fun Double.times(vec3: Vec3): Vec3 {
    return Vec3(vec3.x * this, vec3.y * this, vec3.z * this)
}

operator fun Int.times(vec3: Vec3): Vec3 {
    return Vec3(vec3.x * this, vec3.y * this, vec3.z * this)
}

operator fun Double.times(vec2: Vec2): Vec2 {
    return Vec2(vec2.x * this, vec2.y * this)
}

operator fun Int.times(vec2: Vec2): Vec2 {
    return Vec2(vec2.x * this, vec2.y * this)
}

operator fun Double.times(vec4: Vec4): Vec4 {
    return Vec4(vec4.x * this, vec4.y * this, vec4.z * this, vec4.w * this)
}

operator fun Int.times(vec4: Vec4): Vec4 {
    return Vec4(vec4.x * this, vec4.y * this, vec4.z * this, vec4.w * this)
}