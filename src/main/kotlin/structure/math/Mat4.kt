package structure.math

import java.io.File
import kotlin.math.*

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

    operator fun times(vec4: Vec4): Vec4 =
        Vec4(
            x.x * vec4.x + x.y * vec4.y + x.z * vec4.z + x.w * vec4.w,
            y.x * vec4.x + y.y * vec4.y + y.z * vec4.z + y.w * vec4.w,
            z.x * vec4.x + z.y * vec4.y + z.z * vec4.z + z.w * vec4.w,
            w.x * vec4.x + w.y * vec4.y + w.z * vec4.z + w.w * vec4.w,
        )

    fun trace() = 1 + this[0][0] + this[1][1] + this[2][2]
}

fun identityMat4() = Mat4(
    Vec4(1.0,0.0,0.0,0.0),
    Vec4(0.0,1.0,0.0,0.0),
    Vec4(0.0,0.0,1.0,0.0),
    Vec4(0.0,0.0,0.0,1.0),
)

fun translationMat4(v: Vec3) = Mat4(
    Vec4(1.0,0.0,0.0,v.x),
    Vec4(0.0,1.0,0.0,v.y),
    Vec4(0.0,0.0,1.0,v.z),
    Vec4(0.0,0.0,0.0,1.0),
)

fun scalingMat4(v: Vec3) = Mat4(
    Vec4(v.x,0.0,0.0,0.0),
    Vec4(0.0,v.y,0.0,0.0),
    Vec4(0.0,0.0,v.z,0.0),
    Vec4(0.0,0.0,0.0,1.0),
)

// http://www.opengl-tutorial.org/assets/faq_quaternions/index.html#Q54
fun rotMat4(quat: Vec4): Mat4{
    val xx      = quat.x * quat.x
    val xy      = quat.x * quat.y
    val xz      = quat.x * quat.z
    val xw      = quat.x * quat.w

    val yy      = quat.y * quat.y
    val yz      = quat.y * quat.z
    val yw      = quat.y * quat.w

    val zz      = quat.z * quat.z
    val zw      = quat.z * quat.w
    return Mat4(
        Vec4(1-2*(yy+zz),   2*(xy+zw),      2*(xz-yw),      0.0),
        Vec4(2*xy-2*zw,     1-(2*xx+zz),    2*(yz+xw),      0.0),
        Vec4(2*(xz+yw),     2*(yz-xw),      1-2*(xx + yy),  0.0),
        Vec4(0.0,           0.0,            0.0,            1.0),
    )
}

fun quat(mat: Mat4): Vec4 {
    val trace = mat.trace()
    val s: Double
    val x: Double
    val y: Double
    val z: Double
    val w: Double

    if (trace>0.00000001){
        s = sqrt(trace) * 2
        x = ( mat[1][2] - mat[2][1] ) / s
        y = ( mat[2][0]- mat[0][2] ) / s
        z = ( mat[0][1]- mat[1][0]) / s
        w = 0.25 * s
    }else{
        if ( mat[0][0]> mat[1][1] && mat[0][0]> mat[2][2] )  {	// Column 0:
            s  = sqrt( 1.0 + mat[0][0]- mat[1][1] - mat[2][2] ) * 2
            x = 0.25 * s
            y = (mat[0][1]+ mat[1][0]) / s
            z = (mat[2][0]+ mat[0][2] ) / s
            w = (mat[1][2] - mat[2][1] ) / s

        } else if ( mat[1][1] > mat[2][2] ) {			// Column 1:
            s  = sqrt( 1.0 + mat[1][1] - mat[0][0]- mat[2][2] ) * 2
            x = (mat[0][1]+ mat[1][0]) / s
            y = 0.25 * s
            z = (mat[1][2] + mat[2][1] ) / s
            w = (mat[2][0]- mat[0][2] ) / s

        } else {						// Column 2:
            s  = sqrt( 1.0 + mat[2][2] - mat[0][0]- mat[1][1] ) * 2
            x = (mat[2][0]+ mat[0][2] ) / s
            y = (mat[1][2] + mat[2][1] ) / s
            z = 0.25 * s
            w = (mat[0][1]- mat[1][0]) / s
        }
    }
    return Vec4(x,y,z,w)
}

fun quat(euler: Vec3): Vec4 {
    val yaw     = euler.x
    val pitch   = euler.y
    val roll    = euler.z
    val qx = sin(roll/2) * cos(pitch/2) * cos(yaw/2) - cos(roll/2) * sin(pitch/2) * sin(yaw/2)
    val qy = cos(roll/2) * sin(pitch/2) * cos(yaw/2) + sin(roll/2) * cos(pitch/2) * sin(yaw/2)
    val qz = cos(roll/2) * cos(pitch/2) * sin(yaw/2) - sin(roll/2) * sin(pitch/2) * cos(yaw/2)
    val qw = cos(roll/2) * cos(pitch/2) * cos(yaw/2) + sin(roll/2) * sin(pitch/2) * sin(yaw/2)
    return Vec4(qx, qy, qz, qw)
}

fun euler(q: Vec4): Vec3 {
    val x = q.x
    val y = q.y
    val z = q.z
    val w = q.w
    val t0 = +2.0 * (w * x + y * z)
    val t1 = +1.0 - 2.0 * (x * x + y * y)
    val roll = atan2(t0, t1)
    var t2 = +2.0 * (w * y - z * x)
    t2 = if (t2 > +1.0) +1.0 else t2
    t2 = if (t2 < -1.0) -1.0 else t2
    val pitch = asin(t2)
    val t3 = +2.0 * (w * z + x * y)
    val t4 = +1.0 - 2.0 * (y * y + z * z)
    val yaw = atan2(t3, t4)
    return Vec3(yaw, pitch, roll)
}
