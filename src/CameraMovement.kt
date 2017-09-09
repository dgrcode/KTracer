interface CameraMovement{
    // TODO make the lens movements composable. For example: Rotation of the angle + translation linear
    fun cameraUpdater() : (Int) -> Pair<Vector, Vector>
}

class RotationHorizontal(val dist: Double, val height: Double, val alpha1: Double = 0.0, val alpha2: Double = 360.0) : CameraMovement {

    override fun cameraUpdater(): (Int) -> Pair<Vector, Vector> {
        return fun(frame: Int) : Pair<Vector, Vector> {
            val step = (alpha2 - alpha1) / 60
            val alpha = frame * step * Math.PI / 180
            val x = dist * cos(alpha)
            val y = dist * sin(alpha)
            return Pair(Vector(x, y, height), Vector(-x, -y, -height).normalize())
        }
    }
}

class RotationVertical(val dist: Double, val alpha1: Double = 0.0, val alpha2: Double = 85.0) : CameraMovement {

    override fun cameraUpdater(): (Int) -> Pair<Vector, Vector> {
        return fun(frame: Int) : Pair<Vector, Vector> {
            val step = (alpha2 - alpha1) / 60
            val alpha = frame * step * Math.PI / 180
            val x = dist * cos(alpha)
            val z = dist * sin(alpha)
            return Pair(Vector(x, 0.0, z), Vector(-x, 0.0, -z).normalize())
        }
    }
}

class TraslationLinear(val initialPos: Vector, val finalPos: Vector) : CameraMovement {

    override fun cameraUpdater(): (Int) -> Pair<Vector, Vector> {
        return fun(frame: Int) : Pair<Vector, Vector> {
            // moving in y coordinate
            val position = initialPos + (finalPos - initialPos) * frame / 60.0
            return Pair(position, Vector(0.0, -position.y, -250.0).normalize())
        }
    }
}