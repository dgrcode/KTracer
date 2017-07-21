/**
 * Created by daniel on 20/07/17.
 */

class Sky(val light: Color) : Object {
    override fun trace(orig: Vector, dir: Vector): HitRay {
        if (dir.z < 0) return HitRay()
        return HitRay(5000.0, light)
    }
}