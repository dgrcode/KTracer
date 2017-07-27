/**
 * Created by daniel on 20/07/17.
 */

class Vector(val x: Double, val y: Double, val z: Double) {
    constructor() : this (0.0, 0.0, 0.0) // TODO is this ok?

    operator fun plus(v: Vector) : Vector {
        return Vector(x + v.x, y + v.y, z + v.z)
    }

    operator fun unaryMinus() : Vector {
        return Vector(-x, -y, -z)
    }

    operator fun minus(v: Vector) : Vector {
        return this + (-v)
    }

    operator fun times(v: Vector) : Double {
        return x * v.x + y * v.y + z * v.z
    }

    operator fun times(d: Double) : Vector {
        return Vector(x * d, y * d, z * d)
    }

    override fun toString() : String {
        return "[ " + x + ", " + y + ", " + z + " ]"
    }

    fun modulo() : Double {
        return Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0) + Math.pow(z, 2.0))
    }

    fun normalize() : Vector {
        val mod = modulo()
        return Vector(x / mod, y / mod, z / mod)
    }

    fun randomCentered() : Vector {
        var test = Vector(Math.random() * 2 - 1, Math.random() * 2 - 1, Math.random() * 2 - 1)
        while (test.modulo() > 1) {
            test = Vector(Math.random() * 2 - 1, Math.random() * 2 - 1, Math.random() * 2 - 1)
        }

        var cand = test.normalize()

        if (cand * this < 0) {
            cand = -cand
        }

        return cand
    }
}