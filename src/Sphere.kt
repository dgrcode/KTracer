/**
 * Created by daniel on 20/07/17.
 */

class Sphere(val center: Vector, val radius: Double, val material: Material) : Object {
    val name = "sphere"

    override fun trace(orig: Vector, dir: Vector, iters: Int): HitRay {
        val dirNormalized = dir.normalize()
        val inSqrt =
                Math.pow((dirNormalized * (orig - center)), 2.0) -
                Math.pow((orig - center).modulo(), 2.0) +
                Math.pow(radius, 2.0)

        if (inSqrt < 0) return HitRay(iters)

        val hitDistNeg = - dirNormalized * (orig - center) - Math.sqrt(inSqrt)
        val hitDistPos = - dirNormalized * (orig - center) + Math.sqrt(inSqrt)
        var smallestDist = minOf(hitDistNeg, hitDistPos)
        var biggestDist = maxOf(hitDistNeg, hitDistPos)

        /*
        if (Math.abs(smallestDist) < params.bias) {
            smallestDist = 0.0
        }
        if (Math.abs(biggestDist) < params.bias) {
            biggestDist = 0.0
        }
        */


        if (smallestDist < 0 && biggestDist < 0) {
            return HitRay(iters)
        }
        var hitDist: Double = 0.0
        if (smallestDist > 0 && biggestDist> 0) {
            hitDist = smallestDist
        } else {
            hitDist = biggestDist
        }

        /* TODO DEBUG */
        if (params.debug) {
            for (i in 1..iters) {
                print("  ")
            }
            if (hitDist < 1) {
                print("" + smallestDist + ";" + biggestDist + "; SMALL HITDIST!\n")
            } else {
                print("" + smallestDist + ";" + biggestDist + "\n")
            }
        }
        /* TODO DEBUG */

        val hitPoint = orig + dirNormalized * hitDist
        val normalAtHit = getNormalAt(hitPoint)

        if (normalAtHit * dir > 0) {
            // Hitting from the inside
            return HitRay(iters)
        }

        return HitRay(true, orig, dirNormalized, hitDist, normalAtHit, material, iters, name)
    }

    fun getNormalAt(point: Vector) : Vector {
        return (point - center).normalize()
    }
}