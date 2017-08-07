
class Matrix(val a11: Double, val a12: Double, val a13: Double,
             val a21: Double, val a22: Double, val a23: Double,
             val a31: Double, val a32: Double, val a33: Double) {
     constructor(v1: Vector, v2: Vector, v3: Vector) : this(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z)
    constructor() : this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

    operator fun times(v: Vector) : Vector {
        return Vector(a11 * v.x + a12 * v.y + a13 * v.z, a21 * v.x + a22 * v.y + a23 * v.z, a31 * v.x + a32 * v.y + a33 * v.z)
    }

    override fun toString() : String {
        return "[   " + a11 + "    " + a12 + "    " + a13 + "\n" +
                "    " + a21 + "    " + a22 + "    " + a23 + "\n" +
                "    " + a31 + "    " + a32 + "    " + a33 + "   ]"
    }

}

fun getRotationMatrix(phi: Double, theta: Double) : Matrix {
    return Matrix(
            sin(theta), cos(phi) * cos(theta), -sin(phi) * cos(theta),
            -cos(theta), cos(phi) * sin(theta), -sin(phi) * sin(theta),
            0.0, sin(phi), cos(theta)
    )
}

fun getRotationMatirx(dir: Vector) : Matrix {
    val dirProjection = Vector(dir.x, dir.y, 0.0)
    val phi = Math.acos(dir * dirProjection)
    val theta = Math.acos(Vector(1.0, 0.0, 0.0) * dirProjection)
    return getRotationMatrix(phi, theta)
}

fun getRotationMatirxV2(dir: Vector) : Matrix {
    val dirProjection = Vector(dir.x, dir.y, 0.0)
    val cosPhi = dir * dirProjection
    val cosTheta = Vector(1.0, 0.0, 0.0) * dirProjection
    val sinPhi = Math.sqrt(1 - Math.pow(cosPhi, 2.0))
    val sinTheta = Math.sqrt(1 - Math.pow(cosTheta, 2.0))
    return Matrix(
            sinTheta, cosPhi * cosTheta, -sinPhi * cosTheta,
            -cosTheta, cosPhi * cosTheta, -sinPhi * sinTheta,
            0.0, sinPhi, cosPhi
    )
}