interface Geometry2D {
    fun getRandomPoint(): Vector
}

class GeometryPoint () : Geometry2D {
    override fun getRandomPoint(): Vector {
        return Vector(.0, .0, .0)
    }
}

class GeometryCircle (val radius: Double) : Geometry2D {
    override fun getRandomPoint(): Vector {
        var x = Math.random()
        var z = Math.random()
        while (x * x + z * z > 1) {
            x = Math.random()
            z = Math.random()
        }
        return Vector(x, .0, z) * radius
    }

}