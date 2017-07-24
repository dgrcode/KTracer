/**
 * Created by daniel on 20/07/17.
 */

object Scene{
    val objects = ArrayList<Object>()

    fun add(obj: Object) {
        objects.add(obj)
    }

    fun trace(orig: Vector, dir: Vector, iters: Int) : HitRay {
        var firstHit = HitRay(iters)
        for (obj in objects) {
            val tempHit = obj.trace(orig, dir, iters)
            if (tempHit.hit && tempHit.dist > 0 && tempHit.dist < firstHit.dist) {
                /* TODO remove DEBUG */
                /*if (firstHit.name != "<Nothing>") {
                    println(tempHit.name + " is closer than " + firstHit.name + ": " + tempHit.dist + " < " + firstHit.dist)
                }*/
                /* TODO remove DEBUG */
                firstHit = tempHit
            }
        }
//        for (i in 1..iters) {
//            print("  ")
//        }
//        print(firstHit.dist.toString() + " " + firstHit.name + firstHit.dir +"\n")
        return firstHit
    }
}