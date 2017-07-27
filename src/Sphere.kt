/**
 * Created by daniel on 20/07/17.
 */

class Sphere(val center: Vector, val radius: Double, val material: Material) : Object {
    val name = "Sphere"

    override fun trace(orig: Vector, dir: Vector, iters: Int): HitRay {
        val dirNormalized = dir.normalize()
        val inSqrt =
                Math.pow((dirNormalized * (orig - center)), 2.0) -
                Math.pow((orig - center).modulo(), 2.0) +
                Math.pow(radius, 2.0)

        if (inSqrt < 0) return HitRay(iters)

        val hitDistNeg = - dirNormalized * (orig - center) - Math.sqrt(inSqrt)
        val hitDistPos = - dirNormalized * (orig - center) + Math.sqrt(inSqrt)
        if (hitDistNeg < 0 && hitDistPos < 0) {
            return HitRay(iters)
        }
        var hitDist: Double = 0.0
        if (hitDistNeg > 0 && hitDistPos > 0) {
            hitDist = Math.min(hitDistNeg, hitDistPos)
        } else {
            hitDist = Math.max(hitDistNeg, hitDistPos)
        }

        val hitPoint = dirNormalized * hitDist
        val normalAtHit = getNormalAt(hitPoint)
        val reflectionVector = dirNormalized - normalAtHit * (dirNormalized * normalAtHit) * 2.0

        return HitRay(true, orig, dirNormalized, hitDist, normalAtHit, reflectionVector, material, iters, name)
    }

    fun getNormalAt(point: Vector) : Vector {
        return (point - center).normalize()
    }
}