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
            //return Color(0f)
            //return Color((hitRay.iters / params.iterPerPixel).toFloat())
            return color
        }

        if (hitRay.iters > params.recursionLimit) {
            //return Color(0f, 1f, 0f)
            return Color()
        }

        val nextPoint = hitRay.orig + hitRay.dir * hitRay.dist + hitRay.normal * params.bias
        val randInt = Math.random();


        // Debugging returs
        //return Color((randomRay.z.toFloat() + 1) / 2, (randomRay.z.toFloat() + 1) / 2, Math.abs(randomRay.z.toFloat() + 1) / 2)
        //return albedo * incomingBouncedLight * directionCorrection + incomingReflectedLight * reflectivity
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


        if (randInt < reflectivity) {
            // compute only reflectivity ray
            val reflectionVector = hitRay.dir.reflectionWithNormal(hitRay.normal)

            val nextHitRay = Scene.trace(nextPoint, reflectionVector, hitRay.iters + 1)
            //val incomingReflectedLight = reflectedHitRay.material.shade(reflectedHitRay)
            val incomingLight = nextHitRay.material.shade(nextHitRay);
            return incomingLight;
        } else {
            // compute diffuse light ray
            val randomRay = hitRay.normal.randomCentered()
            val directionCorrection = (hitRay.normal * randomRay).toFloat()
            val nextHitRay = Scene.trace(nextPoint, randomRay, hitRay.iters + 1)
            //val incomingBouncedLight = bouncedHitRay.material.shade(bouncedHitRay)
            val incomingLight = nextHitRay.material.shade(nextHitRay);
            return albedo * incomingLight * directionCorrection
        }
    }
}