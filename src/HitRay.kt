/**
 * Created by daniel on 20/07/17.
 */

data class HitRay(val hit: Boolean, val orig: Vector, val dir: Vector, val dist: Double, val normal: Vector, val material: Material, val iters: Int, val name: String) {
    constructor(iters: Int) : this (false, Vector(), Vector(), Double.POSITIVE_INFINITY, Vector(), Material(), iters, "<Nothing>")
    constructor(orig: Vector, dist: Double, dir: Vector, color: Color) : this(true, orig, dir, dist, Vector(), Material(color), 0, "light") // when you hit light
}