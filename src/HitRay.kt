/**
 * Created by daniel on 20/07/17.
 */

data class HitRay(
        val hit: Boolean, // Has hit an object?
        val orig: Vector, // Origin of the ray that hit
        val dir: Vector, // Diretion of the ray that hit
        val dist: Double, // Distance from the origin to the hit point
        val normal: Vector, // Normal of the object hit at hit point
        val incomingMediumKr: Double, // Refraction index in the medium in which the ray is moving
        val material: Material, // Material of the object hit
        val iters: Int, // Number of iterations. Used to stop the recursion limit
        val name: String // Name of the object hit. Used for debugging the stack trace of hits in the recursions
) {
    constructor(iters: Int) : this (
            false, Vector(), Vector(), Double.POSITIVE_INFINITY, Vector(), Scene.environmentKr, Material(), iters, "<Nothing>"
    )
    constructor(orig: Vector, dist: Double, dir: Vector, color: Color) : this(
            true, orig, dir, dist, Vector(), Scene.environmentKr, Material(color), 0, "light"
    ) // when you hit light
}