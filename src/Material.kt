/**
 * Created by daniel on 20/07/17.
 */

class Material(val isLight: Boolean, val color: Color, val albedo: Albedo, val reflectivity: Float, val Kr: Double) {
    // Empty constructor
    constructor() : this (true, Color(), Albedo(), 0f, NON_REFRACTIVE_KR)
    // Debugging constructor
//    constructor() : this (true, Color(1f, 0f, 0f), Albedo(), 0f, NON_REFRACTIVE_KR)
    // Basic Material
    constructor(albedo: Albedo, reflectivity: Float) : this (false, Color(), albedo, reflectivity, NON_REFRACTIVE_KR)
    // Refractive material
    constructor(albedo: Albedo, reflectivity: Float, Kr: Double) : this (false, Color(), albedo, reflectivity, Kr)
    // Light
    constructor(color: Color) : this (true, color, Albedo(1f, 1f, 1f), 0f, NON_REFRACTIVE_KR)

    fun shade(hitRay: HitRay) : Color {
        if (isLight) {
            //return Color(0f)
            //return Color((hitRay.iters / params.iterPerPixel).toFloat())
            return color
        }

        if (hitRay.iters > params.recursionLimit) {
            if (params.debug.global or params.debug.recursionLimit) return Color(0f, 1f, 0f)
            return Color()
        }


        // Debugging returns
        //return Color((randomRay.z.toFloat() + 1) / 2, (randomRay.z.toFloat() + 1) / 2, Math.abs(randomRay.z.toFloat() + 1) / 2)
        //return albedo * incomingBouncedLight * directionCorrection + incomingReflectedLight * reflectivity
        // Color as albedo
        //return albedo * Color(1f)

        /*
        val reflectionVector = hitRay.dir - hitRay.normal * (hitRay.dir * hitRay.normal) * 2.0
        if (hitRay.iters > 0) {
            //abs z
            //return Color(Math.abs(reflectionVector.z.toFloat()), Math.abs(reflectionVector.z.toFloat()), Math.abs(reflectionVector.z.toFloat()))
            //abs y
            //return Color(Math.abs(reflectionVector.y.toFloat()), Math.abs(reflectionVector.y.toFloat()), Math.abs(reflectionVector.y.toFloat()))
            //rel y
            //return Color((reflectionVector.y.toFloat() + 1) / 2, (reflectionVector.y.toFloat() + 1) / 2, (reflectionVector.y.toFloat() + 1) / 2)
            if (reflectionVector.y < 0) {
                return Color(0f)
            }
            return Color(reflectionVector.y.toFloat(), 0f, 0f)
        }

        val debHitRay = Scene.trace(nextPoint, reflectionVector, hitRay.iters + 1)
        return debHitRay.material.shade(debHitRay)
        */


        /*
        // DEBUG Display Value
        val reflectionVector = hitRay.dir.reflectionWithNormal(hitRay.normal)
        val value = reflectionVector.z.toFloat()
        if (value < 0) {
            return Color(0f, 0f, Math.abs(value))
        } else {
            return Color(Math.abs(value), 0f, 0f)
        }
        */


        var randInt: Double
        val nextPointWithoutBias = hitRay.orig + hitRay.dir * hitRay.dist
        val nextPoint: Vector

        // `theta1` is the hitting angle; `theta2` is the outgoing ray angle
        val incomingNormal: Vector // normal pointing to the incoming ray
        val rayFromInside: Boolean
        val nextMediumKr: Double
        var cosTheta1 = hitRay.dir * hitRay.normal
        if (cosTheta1 > 0) {
            rayFromInside = true
            nextMediumKr = Scene.environmentKr
            incomingNormal = -hitRay.normal
            if ((Kr != NON_REFRACTIVE_KR) and (params.debug.global or params.debug.hitStack)) {
                for (i in 1..hitRay.iters) {
                    print("  ")
                }
                print("HITTING FROM INSIDE\n")
            }
        } else {
            rayFromInside = false
            nextMediumKr = Kr
            incomingNormal = hitRay.normal
            cosTheta1 = -cosTheta1
            if ((Kr != NON_REFRACTIVE_KR) and (params.debug.global or params.debug.hitStack)) {
                for (i in 1..hitRay.iters) {
                    print("  ")
                }
                print("HITTING FROM OUTSIDE\n")
            }
        }

        val cosI = Math.abs(hitRay.dir * incomingNormal)

        if (Kr != NON_REFRACTIVE_KR) {
            val transmittance = 1 - shlicks(hitRay.incomingMediumKr, nextMediumKr, cosI)
            //return Color(transmittance.toFloat())
            if (params.debug.global or params.debug.refraction) {
                for (i in 1..hitRay.iters) {
                    print("  ")
                }
                print("TRANSMITANCE: " + transmittance + "\n")
            }
            randInt = Math.random()

            if (randInt < transmittance) {
                if (params.debug.global or params.debug.refractionAngle) {
                    for (i in 1..hitRay.iters) {
                        print("  ")
                    }
                    print("ANGLES: refr:" + Math.acos(cosTheta1) * 180 / Math.PI + "; crit:" + getCriticalAngle(hitRay.incomingMediumKr / nextMediumKr) * 180 / Math.PI + "\n")
                }
                if (params.debug.global or params.debug.refraction) {
                    for (i in 1..hitRay.iters) {
                        print("  ")
                    }
                    print("REFRACTIVE RAY: CROSSING THE INTERFACE\n")
                }

                /* TODO FIX:
                    * Modulo is not 1 ??
                    * hitDist is ~bias ??
                */
                val refractivityRelation = hitRay.incomingMediumKr / nextMediumKr

                val tParallelMod = refractivityRelation * Math.sqrt(1 - cosI * cosI)
                val tPerpendicularMod = Math.sqrt(1 - tParallelMod * tParallelMod)
                val tParallel = (hitRay.dir + incomingNormal * cosI).normalize()
                val tPerpendicular = -incomingNormal
                val refractionVector = tParallel * tParallelMod + tPerpendicular * tPerpendicularMod

                if (params.debug.any and (Math.abs(refractionVector.modulo() - 1.0) > params.bias)) println("RED ALERT!!! Refraction modulo: " + refractionVector.modulo())
                //println("Modulo incoming normal: " + incomingNormal.modulo())
                //val refractionVector = hitRay.dir * refractivityRelation + incomingNormal * (cosI * refractivityRelation + Math.sqrt(1 - sinTheta2Pw2))

                if (params.debug.global or params.debug.refractionAngle) {
                    for (i in 1..hitRay.iters) {
                        print("  ")
                    }
                    val angle = Math.acos(refractionVector.normalize() * hitRay.dir.normalize()) * 180 / Math.PI
                    print("Angle deviated: %.2f DEBUG: %.20f\n".format(angle, refractionVector.normalize() * hitRay.dir.normalize()))
                }
//
                nextPoint = nextPointWithoutBias + refractionVector * params.bias
                val nextHitRay = Scene.trace(nextPoint, refractionVector, hitRay.iters + 1)
                val incomingLight = nextHitRay.material.shade(nextHitRay)
                return incomingLight
            }
        }

        randInt = Math.random()
        if (randInt < reflectivity) {
            if (params.debug.global or params.debug.refraction) {
                for (i in 1..hitRay.iters) {
                    print("  ")
                }
                print("REFLECTIVE RAY: BOUNCING\n")
            }
            if (rayFromInside) {
                nextPoint = nextPointWithoutBias - hitRay.normal * params.bias
            } else {
                nextPoint = nextPointWithoutBias + hitRay.normal * params.bias
            }
            // compute only reflectivity ray
            val reflectionVector = hitRay.dir.reflectionWithNormal(hitRay.normal)
            /*val nextPoint = nextPointWithoutBias + reflectionVector * params.bias*/
            val nextHitRay = Scene.trace(nextPoint, reflectionVector, hitRay.iters + 1)
            //val incomingReflectedLight = reflectedHitRay.material.shade(reflectedHitRay)
            val incomingLight = nextHitRay.material.shade(nextHitRay)
            return incomingLight
        } else {
            if (params.debug.global or params.debug.refraction) {
                for (i in 1..hitRay.iters) {
                    print("  ")
                }
                print("DIFFUSE LIGHT RAY: RANDOM RAY\n")
            }
            /*if (Kr == NON_REFRACTIVE_KR) {
                return Color(0f, 0f, 0f)
            }*/
            nextPoint = nextPointWithoutBias + hitRay.normal * params.bias

            // compute diffuse light ray
            val randomRay = hitRay.normal.randomCentered()
            val directionCorrection = (hitRay.normal * randomRay).toFloat()
            val trace = Scene.trace(nextPoint, randomRay, hitRay.iters + 1)
            val nextHitRay = trace
            //val incomingBouncedLight = bouncedHitRay.material.shade(bouncedHitRay)
            val incomingLight = nextHitRay.material.shade(nextHitRay)
            return albedo * incomingLight * directionCorrection
        }
    }

    private fun shlicks(nu1: Double, nu2: Double, cosTheta1: Double) : Double {
        if (params.debug.global or params.debug.refraction) println("\tprevKr: %.5f; nextKr: %.5f".format(nu1, nu2))
        val refractivityRelation = nu1 / nu2
        //val criticAngle = getCriticalAngle(refractivityRelation)

        /*if (refractivityRelation >= 1 && Math.acos(cosTheta1) > criticAngle) {
            println("\tCritical angle!")
            return 1.0
        }*/

        var cosAlpha = cosTheta1
        if (refractivityRelation > 1) {
            val sinTheta1Pw2 = 1 - cosTheta1 * cosTheta1
            val sinTheta2Pw2 = refractivityRelation * refractivityRelation * sinTheta1Pw2
            if (sinTheta2Pw2 > 1.0) {
                println("\tCritical angle!")
                return 1.0
            }
            val cosTheta2 = Math.sqrt(1 - sinTheta2Pw2)
            cosAlpha = cosTheta2
        }

        var r0 = (nu1 - nu2) / (nu1 + nu2)
        r0 *= r0

        val r1 = 1 - cosAlpha

        return r0 + (1 - r0) * r1 * r1 * r1 * r1 * r1
    }

    private fun fresnel() : Double {
        // TODO implement Fresnel equations too
        println("FRESNEL EQUATIONS ARE NOT IMPLEMENTED YET")
        return 1.0
    }

    private fun getCriticalAngle(refractivityRelation: Double) : Double {
        if (refractivityRelation < 1) return Math.PI / 2
        val criticalAngle: Double
        if (refractivityRelation <= 1) {
            criticalAngle = Math.asin(1.0 * refractivityRelation)
        } else {
            criticalAngle = Math.asin(1.0 / refractivityRelation)
        }
        return criticalAngle
    }

    companion object {
        val GOLD = Material(Albedo(0.898f, 0.749f, 0.396f), 0.4f)
    }
}

//data class Refractivity(val chance: Float, val medium: Medium)




/* DEBUG */
/*
fun main(args: Array<String>) {
    val m = Material(Albedo(), 0f, Medium.AIR)
    var test = true
    var ang = 0.0
    val nu1 = 1.33f
    val nu2 = 1f
    println("Critical angle: %.5f".format(m.getCriticalAngle(nu1 / nu2) * 180 / Math.PI))
    while (test) {
        println("cos: %.2f; transmittance: %.5f".format(ang, 1 - m.shlicks(nu1, nu2, Math.cos(ang * Math.PI / 180))))
        ang += .1
        if (ang >= 90.0) test = false
    }
}
/* DEBUG */