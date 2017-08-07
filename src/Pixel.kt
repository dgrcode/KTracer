/**
 * Created by daniel on 20/07/17.
 */

class Pixel(val topLeft: Vector, val normal: Vector, val size: Double){
    fun center() : Vector {
        //return Vector(topLeft.x + size / 2, topLeft.y, topLeft.z - size / 2)
        val rot = getRotationMatirxV2(normal)
        println(rot)
        return topLeft + rot * Vector(size / 2, 0.0, - size / 2)
    }

    fun getRandomPoint() : Vector {
        //return Vector(topLeft.x + Math.random() * size, topLeft.y, topLeft.z - Math.random() * size)
        return topLeft + getRotationMatirxV2(normal) * Vector(Math.random() * size, 0.0, - Math.random() * size / 2)
    }
}