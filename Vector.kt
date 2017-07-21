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

    fun modulo() : Double {
        return Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0) + Math.pow(z, 2.0))
    }

    fun normalize() : Vector {
        val mod = modulo()
        return Vector(x / mod, y / mod, z / mod)
    }

    fun randomCentered() : Vector {
        val phi = Math.random() * Math.PI
        val theta = Math.random() * Math.PI
        val randVect = Vector(
                Math.cos(phi) * Math.cos(theta),
                Math.cos(phi) * Math.sin(theta),
                Math.sin(phi))

        return randVect + this
    }
}