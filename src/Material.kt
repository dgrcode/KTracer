/**
 * Created by daniel on 20/07/17.
 */

data class Material(val isLight: Boolean, val color: Color, val albedo: Albedo, val reflectivity: Float) {
    constructor() : this (true, Color(), Albedo(), 0f)
    //constructor() : this (true, Color(1f, 0f, 0f), Albedo(), 0f)
    constructor(albedo: Albedo, reflectivity: Float) : this (false, Color(), albedo, reflectivity)
    constructor(color: Color) : this (true, color, Albedo(1f, 1f, 1f), 0f)

    fun shade(hitRay: HitRay) : Color {
        if (isLight) {
            return color
        }

        if (hitRay.iters > 10) {
            //return Color(0f, 1f, 0f)
            return Color()
        }

        val randomRay = hitRay.normal.randomCentered()
        val nextPoint = hitRay.orig + hitRay.dir * hitRay.dist + hitRay.normal * 0.01
        val bouncedHitRay = Scene.trace(nextPoint, randomRay, hitRay.iters + 1)
        val incomingBouncedLight = bouncedHitRay.material.shade(bouncedHitRay)

        val reflectedHitRay = Scene.trace(nextPoint, hitRay.reflection, hitRay.iters + 1)
        val incomingReflectedLight = reflectedHitRay.material.shade(reflectedHitRay)

        val directionCorrection = (hitRay.normal * randomRay).toFloat()

        //return Color((randomRay.z.toFloat() + 1) / 2, (randomRay.z.toFloat() + 1) / 2, Math.abs(randomRay.z.toFloat() + 1) / 2)
        return albedo * incomingBouncedLight * directionCorrection * (1 - reflectivity)+ incomingReflectedLight * reflectivity
        //albedo *
        //return Color(Math.abs(hitRay.normal.z.toFloat()), Math.abs(hitRay.normal.z.toFloat()), Math.abs(hitRay.normal.z.toFloat()))
        // incomingLight// * directionCorrection
        //return incomingReflectedLight
    }
}