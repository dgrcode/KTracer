class Lens(var center: Vector, val geometry: Geometry2D) {
    fun moveTo(newPosition: Vector) {
        center = newPosition
    }

    fun getRandomPoint() : Vector {
        //val randP = center + sensor.rotationMatrix * geometry.getRandomPoint()
        //if (params.debug.fieldOfView) println("distance between center and random point: ${(randP - center).modulo()}")
        return center + sensor.rotationMatrix * geometry.getRandomPoint()
    }

    fun generateRay(pixel: Pixel): Pair<Vector, Vector> {
        val lensOrigin = getRandomPoint()
        val pixelPoint = pixel.getRandomPoint()

        val centerDirection = (pixelPoint - center).normalize()
        val focalPoint = center + centerDirection * focalDist

        val rayDirection = (focalPoint - lensOrigin).normalize()

        return Pair(lensOrigin, rayDirection)
    }
}