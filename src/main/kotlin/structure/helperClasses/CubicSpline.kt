package structure.helperClasses

import kotlin.math.sqrt

class CubicSpline(m_points: List<Vec3>) {

    val splinePoints = m_points.size
    val mCoeffs = mutableListOf<MutableList<Vec3>>()
    val mLengths = mutableListOf<Double>()

    init {
        val n = splinePoints-1
        val a = mutableListOf<Vec3>()

        for (i in 1 until n){
            a[i] = 3* ((m_points[i + 1] - 2* m_points[i] + m_points[i - 1]))
        }

        val l = mutableListOf<Double>()
        val mu = mutableListOf<Double>()
        val z = mutableListOf<Vec3>()

        l[n] = 1.0
        l[0] = 1.0
        mu[0] = 0.0
        z[n] = Vec3()
        z[0] = z[n]
        mCoeffs[n][2] = Vec3()

        for (i in 1 until n){
            l[i] = 4-mu[i - 1]
            mu[i] = 1/l[i]
            z[i] = (a[i] - z[i - 1]) / l[i]
        }

        for (i in 0..n){
            mCoeffs[i][0] = m_points[i]
        }

        for (j in (n-1)..0){
            mCoeffs[j][2] = z[j] - mu[j] * mCoeffs[j + 1][2]
            mCoeffs[j][3] = (1.0 / 3.0)*(mCoeffs[j + 1][2] - mCoeffs[j][2])
            mCoeffs[j][1] = m_points[j + 1] - m_points[j] - mCoeffs[j][2] - mCoeffs[j][3]
        }

        for (k in 0 ..n){
            mLengths[k] = integrate(k, 1.0)
        }
    }

    fun splineAtTime(time: Double): Vec3 {
        var t = time
        if(t>= splinePoints)
            t = splinePoints.toDouble()
        var spline = t.toInt()
        val fractional = (t - spline)

        spline %= (splinePoints - 1)

        val x = fractional
        val xx = x*x
        val xxx = x*xx

        return mCoeffs[spline][0] + mCoeffs[spline][1] * fractional + mCoeffs[spline][2] * xx + mCoeffs[spline][3] * xxx
    }


    fun constVelocitySplineAtTime(time: Double): Vec3 {
        var t = time
        var spline = 0
        while (t > mLengths[spline]) {
            t -= mLengths[spline]
            spline += 1
        }
        var s: Double = t / mLengths[spline] // Here's our initial guess.

        // Do some Newton-Rhapsons.
        s = (s - (integrate(spline, s) - t) / arcLengthIntegrand(spline, s))
        s = (s - (integrate(spline, s) - t) / arcLengthIntegrand(spline, s))
        s = (s - (integrate(spline, s) - t) / arcLengthIntegrand(spline, s))
        s = (s - (integrate(spline, s) - t) / arcLengthIntegrand(spline, s))
        s = (s - (integrate(spline, s) - t) / arcLengthIntegrand(spline, s))
        s = (s - (integrate(spline, s) - t) / arcLengthIntegrand(spline, s))
        return splineAtTime(spline + s)
    }



    // Composite Simpson's Rule, Burden & Faires - Numerical Analysis 9th, algorithm 4.1
    private fun integrate(spline: Int, t: Double): Double {
        val n = 16
        val h = t / n
        val xI0: Double = arcLengthIntegrand(spline, t)
        var xI1 = 0.0
        var xI2 = 0.0
        for (i in 0 until n) {
            val X = i * h
            if (i % 2 == 0)
                xI2 += arcLengthIntegrand(spline, X)
            else xI1 += arcLengthIntegrand(spline, X)
        }
        return h * (xI0 + 2 * xI2 + 4 * xI1) * (1.0f / 3)
    }


    private fun arcLengthIntegrand(spline: Int, t: Double): Double {
        val tt = t * t
        val dv: Vec3 = mCoeffs[spline][1] + 2 * mCoeffs[spline][2] * t + 3 * mCoeffs[spline][3] * tt
        val xx: Double = dv.x * dv.x
        val yy: Double = dv.y * dv.y
        val zz: Double = dv.z * dv.z
        return sqrt(xx + yy + zz)
    }

}