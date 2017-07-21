/**
 * Created by daniel on 20/07/17.
 */

data class Material(val isLight: Boolean, val color: Color, val albedo: Albedo, val reflectivity: Float) {
    constructor() : this (false, Color(), Albedo(), 0f)
    constructor(albedo: Albedo, reflectivity: Float) : this (false, Color(), albedo, reflectivity)
    constructor(color: Color) : this (true, color, Albedo(1f, 1f, 1f), 0f)

    fun shade(hitRay: HitRay) : Color {
        if (isLight) {
            return color
        }

        if (hitRay.iters > 3) {
            return Color()
        }

        val randomRay = hitRay.normal.randomCentered().normalize()
        val bouncedHitRay = Scene.trace(hitRay.dir * hitRay.dist, randomRay, hitRay.iters + 1)
        val incomingLight = bouncedHitRay.material.shade(bouncedHitRay)
        val directionCorrection = (hitRay.normal * randomRay).toFloat()
        return albedo * incomingLight * directionCorrection
    }
}