/**
 * Created by daniel on 20/07/17.
 */

class Sphere(val center: Vector, val radius: Double, val material: Material) : Object {
    override fun trace(orig: Vector, dir: Vector): HitRay {
        val normDir = dir.normalize()
        val inSqrt =
                Math.pow((normDir * (orig - center)), 2.0) -
                Math.pow((orig - center).modulo(), 2.0) +
                Math.pow(radius, 2.0)

        if (inSqrt < 0) return HitRay()

        val hitDist = - normDir * (orig - center) - Math.sqrt(inSqrt)
        val hitPoint = normDir * hitDist
        val normalAtHit = getNormalAt(hitPoint)
        val reflectionVector = normDir - normalAtHit * (normDir * normalAtHit) * 2.0

        return HitRay(true, normDir, normalAtHit, reflectionVector, hitDist, material)
    }

    fun getNormalAt(point: Vector) : Vector {
        return (point - center).normalize()
    }
}