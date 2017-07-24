/**
 * Created by daniel on 20/07/17.
 */

data class HitRay(val hit: Boolean, val dir: Vector, val normal: Vector, val reflection: Vector, val dist: Double, val material: Material, val iters: Int, val name: String) {
    constructor(iters: Int) : this (false, Vector(), Vector(), Vector(), Double.POSITIVE_INFINITY, Material(), iters, "<Nothing>")
    constructor(dist: Double, color: Color) : this(true, Vector(), Vector(), Vector(), dist, Material(color), 0, "Light") // when you hit light
}