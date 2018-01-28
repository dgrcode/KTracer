import java.util.Random

/**
 * Created by daniel on 20/07/17.
 */

class Vector(val x: Double, val y: Double, val z: Double) {
    constructor() : this (0.0, 0.0, 0.0)

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

    operator fun times(n: Int) : Vector {
        return Vector(x * n, y * n, z * n)
    }

    operator fun times(n: Float) : Vector {
        return Vector(x * n, y * n, z * n)
    }

    operator fun div(n: Double) : Vector {
        return Vector(x / n, y / n, z / n)
    }

    fun equals(v: Vector) : Boolean {
        // TODO maybe override equals() in the future?
        return x == v.x && y == v.y && z == v.z
    }

    override fun toString() : String {
        return "[ $x, $y, $z ]"
    }

    fun modulo() : Double {
        return Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0) + Math.pow(z, 2.0))
    }

    fun normalize() : Vector {
        val mod = modulo()
        if (mod == .0) return Vector(.0, .0, .0)
        return Vector(x / mod, y / mod, z / mod)
    }

    fun crossProduct(v: Vector) : Vector {
        return Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)
    }

    fun randomCentered() : Vector {
        val randGenerator = Random()
        var test = Vector(randGenerator.nextDouble() * 2 - 1, randGenerator.nextDouble() * 2 - 1, randGenerator.nextDouble() * 2 - 1)
        while (test.modulo() > 1) {
            test = Vector(randGenerator.nextDouble() * 2 - 1, randGenerator.nextDouble() * 2 - 1, randGenerator.nextDouble() * 2 - 1)
        }

        //var cand = test.normalize()
        var cand = test

        if (cand * this < 0) {
            cand = -cand
        }

        return cand.normalize()
    }

    fun reflectionWithNormal(normal: Vector) : Vector {
        return this - normal * (this * normal) * 2.0
    }

    fun refractionWithNormal(normal: Vector, incomingMediumKr: Double, nextMediumKr: Double) : Vector {
        var refrNormal = normal
        if (this * normal > 0) {
            refrNormal = -normal
        }
        val cosIncoming = this * refrNormal
        val refractivityRelation = incomingMediumKr / nextMediumKr
        val sinRefractedPw2 = Math.pow(refractivityRelation, 2.0) * 1 - Math.pow(cosIncoming, 2.0)

        return this * refractivityRelation + refrNormal * (cosIncoming * refractivityRelation - Math.sqrt(1 - sinRefractedPw2))
    }

    fun isPerpendicularTo(v: Vector) : Boolean {
        return this * v == .0
    }
}

// TODO create normalized vector class to be able to use the typesystem to check that the vector we're passing around is actually a normalized one