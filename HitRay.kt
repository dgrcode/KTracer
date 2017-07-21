/**
 * Created by daniel on 20/07/17.
 */

class HitRay(val hit: Boolean, val dir: Vector, val normal: Vector, val reflection: Vector, val dist: Double, val material: Material, val iters: Int = 0) {
    constructor() : this (false, Vector(), Vector(), Vector(), Double.POSITIVE_INFINITY, Material())
    constructor(dist: Double, color: Color) : this(true, Vector(), Vector(), Vector(), dist, Material(color)) // when you hit light
}