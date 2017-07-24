/**
 * Created by daniel on 20/07/17.
 */

data class HitRay(val hit: Boolean, val orig: Vector, val dir: Vector, val dist: Double, val normal: Vector, val reflection: Vector, val material: Material, val iters: Int, val name: String) {
    constructor(iters: Int) : this (false, Vector(), Vector(), Double.POSITIVE_INFINITY, Vector(), Vector(), Material(), iters, "<Nothing>")
    constructor(orig: Vector, dist: Double, dir: Vector, color: Color) : this(true, orig, dir, dist, Vector(), Vector(), Material(color), 0, "Light") // when you hit light
}