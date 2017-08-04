/**
 * Created by daniel on 20/07/17.
 */

class Pixel(val topLeft: Vector, val size: Double){
    fun center() : Vector {
        return Vector(topLeft.x + size / 2, topLeft.y, topLeft.z - size / 2)
    }

    fun getRandomPoint() : Vector {
        return Vector(topLeft.x + Math.random() * size, topLeft.y, topLeft.z - Math.random() * size)
    }
}