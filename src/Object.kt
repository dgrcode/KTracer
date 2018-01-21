/**
 * Created by daniel on 20/07/17.
 */

/*
    Object is anything that can be set into the Scene. There are different type of objects:
      - Light absorbing objects:
        : Sphere

      - Light source object:
        - Global light
          : Sky

        - Directional light
 */

interface Object{
    val type: Object.TYPE
    fun trace(orig: Vector, dir: Vector, iters: Int, incomingMediumKr: Double) : HitRay

    enum class TYPE {
        LIGHT,
        DIRECTIONAL_LIGHT,
        OBJECT
    }
}