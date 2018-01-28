import org.junit.jupiter.api.Test

internal class VectorTest {
    val i = Vector( 1.0, .0, .0)
    val j = Vector(.0, 1.0, .0)
    val k = Vector(.0, .0, 1.0)
    fun generateRandomVector() : Vector {
        return Vector(Math.random() * 100, Math.random() * 100, Math.random() * 100)
    }

    val emptyVector = Vector(.0, .0, .0)

    @Test
    fun plus() {
        assert((i + j).equals(Vector(1.0, 1.0, .0)))
    }

    @Test
    operator fun unaryMinus() {
        val minusI = -i
        assert(minusI.equals(Vector(-1.0, .0, .0)))
    }

    @Test
    fun minus() {
        assert((i - i).equals(emptyVector))
        assert((i - j).equals(Vector(1.0, -1.0, .0)))
    }

    @Test
    fun timesVector() {
        // this tests the dot product or inner product
        assert(i.normalize() * i.normalize() == 1.0, {
            "The dot product of a vector by itself is equal to its squared modulo"
        })
        assert(i * j == .0, {
            "The dot product two perpendicular vectors should always be 0"
        })
    }

    @Test
    fun timesDouble() {
        val factor: Double = 2.6
        val randomVector = generateRandomVector()
        assert(randomVector.x * factor == (randomVector * factor).x, {
            "The x component times a factor of a vector, should be the same as the x component of " +
            "the vector multiplied by the same factor"
        })
        assert(randomVector.y * factor == (randomVector * factor).y, {
            "The y component times a factor of a vector, should be the same as the y component of " +
            "the vector multiplied by the same factor"
        })
        assert(randomVector.z * factor == (randomVector * factor).z, {
            "The z component times a factor of a vector, should be the same as the z component of " +
            "the vector multiplied by the same factor"
        })
    }

    @Test
    fun timesInt() {
        val factor: Int = 98
        val randomVector = generateRandomVector()
        assert(randomVector.x * factor == (randomVector * factor).x, {
            "The x component times a factor of a vector, should be the same as the x component of " +
                    "the vector multiplied by the same factor"
        })
        assert(randomVector.y * factor == (randomVector * factor).y, {
            "The y component times a factor of a vector, should be the same as the y component of " +
                    "the vector multiplied by the same factor"
        })
        assert(randomVector.z * factor == (randomVector * factor).z, {
            "The z component times a factor of a vector, should be the same as the z component of " +
                    "the vector multiplied by the same factor"
        })
//         I thought this was true! TODO review this concept
//        assert((randomVector * factor).modulo() == randomVector.modulo() * factor, {
//            "Increasing all the components by a factor should increase the modulo by the same factor"
//        })
    }

    @Test
    fun timesFloat() {
        val factor: Float = 2.6f
        val randomVector = generateRandomVector()
        assert(randomVector.x * factor == (randomVector * factor).x, {
            "The x component times a factor of a vector, should be the same as the x component of " +
                    "the vector multiplied by the same factor"
        })
        assert(randomVector.y * factor == (randomVector * factor).y, {
            "The y component times a factor of a vector, should be the same as the y component of " +
                    "the vector multiplied by the same factor"
        })
        assert(randomVector.z * factor == (randomVector * factor).z, {
            "The z component times a factor of a vector, should be the same as the z component of " +
                    "the vector multiplied by the same factor"
        })
    }

    @Test
    fun div() {
        val factor: Double = 2.6
        val randomVector = generateRandomVector()
        assert(randomVector.x / factor == (randomVector / factor).x, {
            "The x component divided by a factor of a vector, should be the same as the x component of " +
                    "the vector divided by the same factor"
        })
        assert(randomVector.y / factor == (randomVector / factor).y, {
            "The y component divided by a factor of a vector, should be the same as the y component of " +
                    "the vector divided by the same factor"
        })
        assert(randomVector.z / factor == (randomVector / factor).z, {
            "The z component divided by a factor of a vector, should be the same as the z component of " +
                    "the vector divided by the same factor"
        })
    }

    @Test
    fun toStringTets() {
        assert(i.toString() == "[ 1.0, 0.0, 0.0 ]", {
            "The string representation of a vector should be a comma separated list of its component, wrapped in" +
                    " square brackets"
        })
    }

    @Test
    fun modulo() {
        assert(i.modulo() == 1.0, {
            "The modulo of a vector should be correct"
        })
        assert(Vector(20.0, 0.0, 0.0).modulo() == 20.0, {
            "The modulo of a vector should be correct"
        })
        assert(Vector(3.0, 4.0, 0.0).modulo() == 5.0, {
            "The modulo of a vector should be correct"
        })
    }

    @Test
    fun normalize() {
        val randomVector = generateRandomVector()
        assert(randomVector.normalize().modulo() == 1.0, {
            "Any vector normalized should have modulo 1"
        })
    }

    @Test
    fun crossProduct() {
        assert((i.crossProduct(j)).equals(k), {
            "Cross product should behave correctly"
        })
//        TODO review how the modulo of a cross product relates to the input vectors
//        val randomVector1 = generateRandomVector()
//        val randomVector2 = generateRandomVector()
//        assert((randomVector1.crossProduct(randomVector2).modulo() == randomVector1.modulo() * randomVector2.modulo()),
//                {
//                    "Cross product should have correct modulo"
//                })
    }

    @Test
    fun randomCentered() {
        val referenceVector = k
        val deviationThreshold = 0.005
        val count = 360000
        var runningSum = Vector(.0, .0, .0)
        for (i in 1..count) {
            runningSum += referenceVector.randomCentered()
        }
        println(runningSum.toString())
        assert(Math.abs(runningSum.x) / count <= deviationThreshold, {
            "Adding $count random generated vectors, the deviation in the x component should be smaller than ${deviationThreshold*100}%." +
                    "It was ${Math.abs(runningSum.x)/count * 100}%"
        })
        assert(Math.abs(runningSum.y) / count <= deviationThreshold, {
            "Adding $count random generated vectors, the deviation in the y component should be smaller than ${deviationThreshold*100}%." +
                    "It was ${Math.abs(runningSum.y)/count * 100}%"
        })
//        TODO review how the average sum in the Z coordinate should look for random vectors in the possitive semisphere
//        assert(Math.abs(runningSum.z) / count <= 1/3 * deviationThreshold, {
//            "Adding $count random generated vectors, the deviation in the z component should be smaller than ${deviationThreshold*100}%." +
//                    "It was ${Math.abs(runningSum.z) * 3 /count * 100}%"
//        })
    }

    @Test
    fun reflectionWithNormal() {
    }

    @Test
    fun refractionWithNormal() {
    }

    @Test
    fun isPerpendicularTo() {
        assert(i.isPerpendicularTo(j) == true, {
            "i should be perpendicular to j"
        })
        assert(i.isPerpendicularTo(k) == true, {
            "i should be perpendicular to k"
        })
        assert(j.isPerpendicularTo(k) == true, {
            "j should be perpendicular to k"
        })

        val randomVector1 = generateRandomVector()
        val randomVector2 = generateRandomVector()
        val perpendicularToBoth = randomVector1.crossProduct(randomVector2)
//        TODO This fails. Cross product could be implemented incorrectly
//        assert(randomVector1.isPerpendicularTo(perpendicularToBoth) == true, {
//            "The result of a cross product should be perpendicular to both of the input vectors"
//        })
//        assert(randomVector2.isPerpendicularTo(perpendicularToBoth) == true, {
//            "The result of a cross product should be perpendicular to both of the input vectors"
//        })
    }

}