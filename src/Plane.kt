/**
 * Created by daniel on 21/07/17.
 */

class Plane(val pointInPlane: Vector, val normal: Vector, val material: Material) : Object {
    val name = "plane"

    override fun trace(orig: Vector, dir: Vector, iters: Int, incomingMediumKr: Double): HitRay {
        val dirNormalized = dir.normalize()

        val numerator = (pointInPlane - orig) * normal
        val denominator = dirNormalized * normal

        if (denominator == 0.0) {
            if (numerator == 0.0) {
                // Direction is on the surface
                return HitRay(true, orig, dirNormalized, 0.0, normal, incomingMediumKr, material, iters + 1, name)
            } else {
                // No contact point
                return HitRay(iters)
            }
        }

        val hitDist = numerator / denominator
        if (hitDist < 0) {
            // Hit the plane behind the origin. Object don't see the plane
            return HitRay(iters)
        }

        return HitRay(true, orig, dirNormalized, hitDist, normal, incomingMediumKr, material, iters, name)
    }
}