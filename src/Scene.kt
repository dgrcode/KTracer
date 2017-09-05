/**
 * Created by daniel on 20/07/17.
 */

object Scene{
    val objects = ArrayList<Object>()

    val environmentKr = AIR_KR

    fun add(obj: Object) : Int{
        val nextPosition = objects.size
        objects.add(obj)
        return nextPosition
    }

    fun remove(idx: Int) {
        objects.removeAt(idx)
    }

    fun clear() {
        objects.clear()
    }

    fun trace(orig: Vector, dir: Vector, iters: Int) : HitRay {
        var firstHit = HitRay(iters)
        for (obj in objects) {
            val tempHit = obj.trace(orig, dir, iters, environmentKr)
            if (tempHit.hit && tempHit.dist > 0 && tempHit.dist < firstHit.dist) {
                /* TODO remove DEBUG */
                //if (firstHit.name != "<Nothing>") {
                //    println(tempHit.name + " is closer than " + firstHit.name + ": " + tempHit.dist + " < " + firstHit.dist)
                //}
                /* TODO remove DEBUG */
                firstHit = tempHit
            }
        }
        if (params.debug.global or params.debug.hitStack) {
            for (i in 1..iters) {
                print("  ")
            }
            print(firstHit.dist.toString() + " " + firstHit.name + firstHit.dir + "\n")
        }
        return firstHit
    }
}