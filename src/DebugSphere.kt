
class DebugSphere(val center: Vector, val radius: Double, val material: Material) : Object {
    override fun trace(orig: Vector, dir: Vector, iters: Int, incomingMediumKr: Double): HitRay {
        return HitRay(iters)
    }
}