internal class TriangleTest {

    val origin = Vector(.0, .0, .0)

    val v0 = Vector(-10.0, 100.0, 0.0)
    val v1 = Vector(10.0, 100.0, 0.0)
    val v2 = Vector(0.0, 100.0, 10.0)
    val material = Material(Albedo(0f, 0.3f, 0.3f), 0f)

    val triangle = Triangle(
            v0,
            v1,
            v2,
            material
    )

    @org.junit.jupiter.api.Test
    fun getName() {
        assert(triangle.name.equals("triangle"), {
            "Name should always be 'triangle'"
        })
    }

    @org.junit.jupiter.api.Test
    fun getType() {
        assert(triangle.type.equals(Object.TYPE.OBJECT), {
            "Type should always be Object"
        })
    }

    @org.junit.jupiter.api.Test
    fun getNormal() {
        assert((triangle.v0 - triangle.v1).isPerpendicularTo(triangle.normal), {
            "The normal should be perpendicular to the edges"
        })
        assert((triangle.v1 - triangle.v2).isPerpendicularTo(triangle.normal), {
            "The normal should be perpendicular to the edges"
        })
        assert((triangle.v2 - triangle.v0).isPerpendicularTo(triangle.normal), {
            "The normal should be perpendicular to the edges"
        })
    }

    @org.junit.jupiter.api.Test
    fun trace() {
        // ray from the center of the triangle
        val centerHitRay = triangle.trace(
                origin,
                Vector(.0, 1.0, .0),
                0,
                NON_REFRACTIVE_KR
        )
        assert(centerHitRay.hit == true, {
            "A ray to the center should hit the triangle"
        })
        assert(centerHitRay.dist == 100.0)
        assert(centerHitRay.material == material)
        assert(centerHitRay.iters == 0)
        val hitPointCenter = origin + Vector(.0, 1.0, .0) * centerHitRay.dist
        assert(centerHitRay.normal == triangle.getNormalAt(hitPointCenter))

        // ray from the outside of the triangle
        val outsideHitRay = triangle.trace(
                Vector(100.0, 100.0, 100.0),
                Vector(.0, 1.0, .0),
                0,
                NON_REFRACTIVE_KR
        )
        assert(outsideHitRay.hit == false, {
            "A ray to the outside should not hit the triangle"
        })
    }

    @org.junit.jupiter.api.Test
    fun hitDistance() {
       // probably not needed if trace() works as expected, as this is actually a subroutine of that method
    }

    @org.junit.jupiter.api.Test
    fun getNormalAt() {
        assert(triangle.normal == triangle.getNormalAt(v0))
    }

    @org.junit.jupiter.api.Test
    fun getMaterial() {
        assert(triangle.material == material)
    }

}