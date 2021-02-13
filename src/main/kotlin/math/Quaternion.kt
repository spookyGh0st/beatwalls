package math

import kotlin.math.*

data class Quaternion(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0, var w: Double = 1.0){
    /** Creates a Quaternion from a euler rotation */
    constructor(euler: Vec3): this(){
        val roll   = euler.x
        val pitch     = euler.y
        val yaw    = euler.z

        val cy = cos(yaw * 0.5)
        val sy = sin(yaw * 0.5)
        val cp = cos(pitch * 0.5)
        val sp = sin(pitch * 0.5)
        val cr = cos(roll * 0.5)
        val sr = sin(roll * 0.5)

        x = sr * cp * cy - cr * sp * sy
        y = cr * sp * cy + sr * cp * sy
        z = cr * cp * sy - sr * sp * cy
        w = cr * cp * cy + sr * sp * sy
    }

    /** Creates a Quaternion from a rotation Matrix */
    constructor(rotationMat: Mat4): this(){
        val trace = rotationMat.trace()
        val s: Double

        if (trace>0.00000001){
            s = sqrt(trace) * 2
            x = ( rotationMat[1][2] - rotationMat[2][1] ) / s
            y = ( rotationMat[2][0]- rotationMat[0][2] ) / s
            z = ( rotationMat[0][1]- rotationMat[1][0]) / s
            w = 0.25 * s
        }else{
            if ( rotationMat[0][0]> rotationMat[1][1] && rotationMat[0][0]> rotationMat[2][2] )  {	// Column 0:
                s  = sqrt(1.0 + rotationMat[0][0] - rotationMat[1][1] - rotationMat[2][2]) * 2
                x = 0.25 * s
                y = (rotationMat[0][1]+ rotationMat[1][0]) / s
                z = (rotationMat[2][0]+ rotationMat[0][2] ) / s
                w = (rotationMat[1][2] - rotationMat[2][1] ) / s

            } else if ( rotationMat[1][1] > rotationMat[2][2] ) {			// Column 1:
                s  = sqrt(1.0 + rotationMat[1][1] - rotationMat[0][0] - rotationMat[2][2]) * 2
                x = (rotationMat[0][1]+ rotationMat[1][0]) / s
                y = 0.25 * s
                z = (rotationMat[1][2] + rotationMat[2][1] ) / s
                w = (rotationMat[2][0]- rotationMat[0][2] ) / s

            } else {						// Column 2:
                s  = sqrt(1.0 + rotationMat[2][2] - rotationMat[0][0] - rotationMat[1][1]) * 2
                x = (rotationMat[2][0]+ rotationMat[0][2] ) / s
                y = (rotationMat[1][2] + rotationMat[2][1] ) / s
                z = 0.25 * s
                w = (rotationMat[0][1]- rotationMat[1][0]) / s
            }
        }
    }

    /** Creates an Quaternion from an axis and rotation */
    constructor(axis: Vec3, angle: Double): this(){
        axis.normalize()
        val sinA = sin(angle / 2.0)
        val cosA = cos(angle / 2.0)
        x = axis.x * sinA
        y = axis.y * sinA
        z = axis.z * sinA
        w = cosA
    }

    val length get()  = sqrt(w * w + x * x + y * y + z * z)
    fun inverse() = Quaternion(-x, -y, -z, w)
    fun normalize(){
        val l = 1/length
        x*=l; y*=l; z*=l; w*=l
    }

    operator fun plus(q: Quaternion) = Quaternion(x + q.x, y + q.y, z + q.z, w + q.w)
    operator fun minus(q: Quaternion) = Quaternion(x - q.x, y - q.y, z - q.z, w - q.w)
    operator fun times(f: Float) = Quaternion(x * f, y * f, z * f, w * f)
    operator fun times(v: Vec3) = Quaternion(
        w * v.x + y * v.z - z * v.y,
        w * v.y + z * v.x - x * v.z,
        w * v.z + x * v.y - y * v.x,
        -x * v.x - y * v.y - z * v.z
    )
    operator fun timesAssign(q: Quaternion) {
        val tx = w * q.x + x * q.w + y * q.z - z * q.y
        val ty = w * q.y + y * q.w + z * q.x - x * q.z
        val tz = w * q.z + z * q.w + x * q.y - y * q.x
        val tw = w * q.w - x * q.x - y * q.y - z * q.z
        x= tx; y =ty; z = tz; w= tw
    }
    operator fun times(q: Quaternion) = Quaternion(
        x = w * q.x + x * q.w + y * q.z - z * q.y,
        y = w * q.y + y * q.w + z * q.x - x * q.z,
        z = w * q.z + z * q.w + x * q.y - y * q.x,
        w = w * q.w - x * q.x - y * q.y - z * q.z
    )
    operator fun div(f: Float) = this * (1f / f)
    operator fun unaryMinus() = Quaternion(-x, -y, -z, -w)

    fun rotate(v: Vec3): Vec3 {
        val q = this * v
        q *= this.inverse()
        return Vec3(q.x, q.y, q.z)
    }

    fun toRotationMat4(): Mat4{
        val xx = x * x
        val xy = x * y
        val xz = x * z
        val xw = x * w

        val yy = y * y
        val yz = y * z
        val yw = y * w

        val zz = z * z
        val zw = z * w
        return Mat4(
            Vec4(1 - 2 * (yy + zz), 2 * (xy + zw), 2 * (xz - yw), 0.0),
            Vec4(2 * xy - 2 * zw, 1 - (2 * xx + zz), 2 * (yz + xw), 0.0),
            Vec4(2 * (xz + yw), 2 * (yz - xw), 1 - 2 * (xx + yy), 0.0),
            Vec4(0.0, 0.0, 0.0, 1.0),
        )
    }

    fun toEuler(): Vec3 {
        val sinr_cosp: Double = 2 * (w * x + y * z)
        val cosr_cosp: Double = 1 - 2 * (x * x + y * y)
        val roll = atan2(sinr_cosp, cosr_cosp)

        val sarg = -2 * (x * z - w * y)
        val pitch = if (sarg <= -1) -0.5 * PI else if (sarg >= 1) 0.5 * PI else asin(sarg)

        val siny_cosp = 2 * (w * z + x * y)
        val cosy_cosp = 1 - 2 * (y * y + z * z)
        val yaw = atan2(siny_cosp, cosy_cosp)
        return Vec3(roll, pitch, yaw)
    }
}
