/**
 * Created by daniel on 24/01/18.
 */

class Triangle(val v0: Vector, val v1: Vector, val v2: Vector, val material: Material) : Object {
    val name = "triangle"
    override val type = Object.TYPE.OBJECT

    val EPSILON = params.bias

    val edge1: Vector = v1 - v0
    val edge2: Vector = v2 - v0
    val normal: Vector = edge1.crossProduct(edge2)

    override fun trace(orig: Vector, dir: Vector, iters: Int, incomingMediumKr: Double): HitRay {
        val dirNormalized = dir.normalize()
        val (isHit, hitDist) = hitDistance(orig, dirNormalized, iters)

        if (!isHit) return HitRay(iters)

        val hitPoint = orig + dirNormalized * hitDist
        val normalAtHit = getNormalAt(hitPoint)

        /*
        //This can be used in the future to only show the triangle if it's hit from one side

        if (normalAtHit * dir > 0) {
            // Hitting from the inside
            if (material.Kr == NON_REFRACTIVE_KR) {
                return HitRay(iters)
            }

            return HitRay(true, orig, dirNormalized, hitDist, normalAtHit, material.Kr, material, iters, name)
        }
        */

        return HitRay(true, orig, dirNormalized, hitDist, normalAtHit, incomingMediumKr, material, iters, name)
    }

    fun hitDistance(orig: Vector, dir: Vector, iters: Int): Pair<Boolean, Double> {
        val h: Vector = dir.crossProduct(edge2)
        val a = h * edge1
        if (a > -EPSILON && a < EPSILON) {
            return Pair(false, .0)
        }

        val f = 1 / a
        val s: Vector = orig - v0
        val u = f * (s * h)
        if (u < 0.0 || u > 1.0) {
            return Pair(false, .0)
        }

        val q: Vector = s.crossProduct(edge1)
        val v = f * (dir * q)
        if (v < 0.0 || u + v > 1.0) {
            return Pair(false, .0)
        }

        val dist = f * (edge2 * q)
        if (dist > EPSILON) {
            return Pair(true, dist)
        } else {
            return Pair(false, .0)
        }

    }

    fun getNormalAt(point: Vector) : Vector {
        // So far I don't do smooth normals
        return normal
    }

    fun getPlaneContainer() : Plane {
        val a: Vector = v1 - v0
        val b: Vector = v2 - v0
        val planeNormal: Vector = a.crossProduct(b)
        return Plane(v0, planeNormal, Material())
    }
}