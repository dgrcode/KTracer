/**
 * Created by daniel on 20/07/17.
 */

class Sky(val light: Color) : Object {
    val name = "light"
    override val type = Object.TYPE.LIGHT

    override fun trace(orig: Vector, dir: Vector, iters: Int, incomingMediumKr: Double): HitRay {
        //if (dir.z < 0) return HitRay(iters)
        return HitRay(orig, 50000.0, dir, light)//Color(iters / 12f, iters / 12f, iters / 12f))
    }
}