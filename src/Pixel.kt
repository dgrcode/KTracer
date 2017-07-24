/**
 * Created by daniel on 20/07/17.
 */

class Pixel(val topLeft: Vector, val size: Double){
    fun center() : Vector {
        return Vector(topLeft.x + size / 2, topLeft.y, topLeft.z - size / 2)
    }
}