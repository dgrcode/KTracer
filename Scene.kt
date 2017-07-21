/**
 * Created by daniel on 20/07/17.
 */

object Scene{
    val objects = ArrayList<Object>()

    fun add(obj: Object) {
        objects.add(obj)
    }

    fun trace(orig: Vector, dir: Vector, iters: Int = 0) : HitRay {
        var firstHit = HitRay()
        for (obj in objects) {
            val tempHit = obj.trace(orig, dir)
            if (tempHit.dist < firstHit.dist) {
                firstHit = tempHit
            }
        }
        return firstHit
    }
}