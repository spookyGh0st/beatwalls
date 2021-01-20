package structure.math

import kotlin.math.cos
import kotlin.math.sin

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

    operator fun times(mat4: Mat4): Mat4 = Mat4(
        Vec4(
            x.x * mat4.x.x + x.y * mat4.y.x + x.z * mat4.z.x,
            x.x * mat4.x.y + x.y * mat4.y.y + x.z * mat4.z.y,
            x.x * mat4.x.z + x.y * mat4.y.z + x.z * mat4.z.z,
            x.x * mat4.x.w + x.y * mat4.y.w + x.z * mat4.z.w,
        ), Vec4(
            y.x * mat4.x.x + y.y * mat4.y.x + y.z * mat4.z.x,
            y.x * mat4.x.y + y.y * mat4.y.y + y.z * mat4.z.y,
            y.x * mat4.x.z + y.y * mat4.y.z + y.z * mat4.z.z,
            y.x * mat4.x.w + y.y * mat4.y.w + y.z * mat4.z.w,
        ), Vec4(
            z.x * mat4.x.x + z.y * mat4.y.x + z.z * mat4.z.x,
            z.x * mat4.x.y + z.y * mat4.y.y + z.z * mat4.z.y,
            z.x * mat4.x.z + z.y * mat4.y.z + z.z * mat4.z.z,
            z.x * mat4.x.w + z.y * mat4.y.w + z.z * mat4.z.w,
        ),
        Vec4(
            w.x * mat4.x.x + w.y * mat4.y.x + w.z * mat4.z.x,
            w.x * mat4.x.y + w.y * mat4.y.y + w.z * mat4.z.y,
            w.x * mat4.x.z + w.y * mat4.y.z + w.z * mat4.z.z,
            w.x * mat4.x.w + w.y * mat4.y.w + w.z * mat4.z.w,
        ),
    )

    companion object {
    }

    operator fun times(vec4: Vec4): Vec4 =
        Vec4(
            x.x * vec4.x + x.y * vec4.y + x.z * vec4.z + x.w * vec4.w,
            y.x * vec4.x + y.y * vec4.y + y.z * vec4.z + y.w * vec4.w,
            z.x * vec4.x + z.y * vec4.y + z.z * vec4.z + z.w * vec4.w,
            w.x * vec4.x + w.y * vec4.y + w.z * vec4.z + w.w * vec4.w,
        )
}

fun identityMat4() = Mat4(
    Vec4(1.0,0.0,0.0,0.0),
    Vec4(0.0,1.0,0.0,0.0),
    Vec4(0.0,0.0,1.0,0.0),
    Vec4(0.0,0.0,0.0,1.0),
)

fun scalingMat4(x: Double,y: Double, z: Double) = Mat4(
    Vec4(x,0.0,0.0,0.0),
    Vec4(0.0,y,0.0,0.0),
    Vec4(0.0,0.0,z,0.0),
    Vec4(0.0,0.0,0.0,1.0),
)

fun rotMat4x(A: Double) = Mat4(
    Vec4(1.0,0.0,0.0,0.0),
    Vec4(0.0, cos(A), -sin(A), 0.0),
    Vec4(0.0, sin(A), cos(A), 0.0),
    Vec4(0.0,0.0,0.0,1.0),
)
fun rotMat4y(A: Double) = Mat4(
    Vec4(cos(A), 0.0, sin(A), 0.0),
    Vec4(0.0, 1.0, 0.0, 0.0),
    Vec4(-sin(A), 0.0, cos(A), 0.0),
    Vec4(0.0, 0.0, 0.0, 1.0),
)

fun rotMat4z(A: Double) = Mat4(
    Vec4(cos(A), -sin(A), 0.0, 0.0),
    Vec4(sin(A), cos(A), 0.0, 0.0),
    Vec4(0.0, 0.0, 1.0, 0.0),
    Vec4(0.0, 0.0, 0.0, 1.0),
)


/**
 * Creates a rotation Matrix using some optimised math.
 * see Q36 from:
 * http://www.opengl-tutorial.org/assets/faq_quaternions/index.html#Q38
 */
fun rotMat4(angle: Vec4): Mat4 {
    val a =cos(angle.x)
    val b = sin(angle.x)
    val c = cos(angle.y)
    val d = sin(angle.y)
    val e = cos(angle.z)
    val f = sin(angle.z)

    val ad =   a * d;
    val bd =   b * d;
    return Mat4(
        Vec4(c*e, -c*f, d, 0.0),
        Vec4(bd*e + a*f, -bd*f+a*e, -b*c, 0),
        Vec4(-ad*e+b*f, ad*f+b*e, a*c, 0.0),
        Vec4(0.0,0.0,0.0,1.0)
    )
}

fun main(){
    val translation = Mat4(
        Vec4(1,0,0,10),
        Vec4(0,1,0,0),
        Vec4(0,0,1,0),
        Vec4(0,0,0,1),
    )
    val v = Vec4(10,10,10,1)
    println(translation*v)
}
