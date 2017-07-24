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
    fun trace(orig: Vector, dir: Vector, iters: Int) : HitRay
    fun trace(dir: Vector): HitRay {
        return trace(camera.position, dir, 1)
    }
}