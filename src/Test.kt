import java.util.*

/**
 * Created by daniel on 21/07/17.
 */

fun main(args: Array<String>) {
    val v = Vector(0.0, 0.0, 1.0)
    println(v.randomCentered().toString())
}

fun showVectorAt(v: Vector, p: Vector, albedo: Albedo) {
    showVectorAt(v, p, albedo, 10.0)
}

fun showVectorAt(v: Vector, p: Vector, albedo: Albedo, length: Double, radius: Double = params.debRadius) {
    //for (j in 0..(length / (radius * 2)).toInt() step Math.max((length / 10).toInt(), 1)) {
    for (j in 0..10) {
        val pCenter = p + v * j * (length / 10)
        Scene.add(Sphere(pCenter, radius, Material(albedo, 0f)))
    }
}

fun debugs(pixelVector: Vector = Vector()) {
    //printRandomNormals()

    //printRandomReflections()

    //printVectorTracing(pixelVector)

    //printRotationMatrix()

    //printCameraPosition()
}

fun printRandomNormals() {
        println("Generating random normals")
    for (i in 1..10) {
        var hitObject = "none"
        var hitRay = HitRay(0)
        while (hitObject != "sphere") {
            val pixel = lens.getRandomPixel();
            val rayDir = pixel.getRandomPoint() - camera.position
            hitRay = Scene.trace(camera.position, rayDir, 0)
            hitObject = hitRay.name
        }
        val hitPoint = hitRay.dir * hitRay.dist
        val normalAtHit = hitRay.normal
        showVectorAt(normalAtHit, hitPoint, Albedo(0f,0f,1f))
        for (j in 1..5) {
            val randomVector = normalAtHit.randomCentered()
//            showVectorAt(randomVector, hitPoint, Albedo(0f, 1f, 0f))
            if (randomVector.y < 0) {
                showVectorAt(randomVector, hitPoint, Albedo(0f, 1f, 0f))
            } else {
                showVectorAt(randomVector, hitPoint, Albedo(1f, 0f, 0f))
            }
        }

    }
    println("Finished with the random normals")
}

fun printRandomReflections() {
    println("Generating random reflections")
    for (i in 1..5) {
        var hitObject = "none"
        var hitRay = HitRay(0)
        while (hitObject != "sphere") {
            val pixel = lens.getRandomPixel();
            val rayDir = pixel.getRandomPoint() - camera.position
            hitRay = Scene.trace(camera.position, rayDir, 0)
            hitObject = hitRay.name
        }
        val hitPoint = hitRay.dir * hitRay.dist
        val normalAtHit = hitRay.normal
        showVectorAt(normalAtHit, hitPoint, Albedo(0f,0f,1f), 5.0)
        val reflectionVector = hitRay.dir.reflectionWithNormal(hitRay.normal)
        if (reflectionVector.y < 0) {
            showVectorAt(reflectionVector, hitPoint, Albedo(0f, 1f, 0f))
        } else {
            showVectorAt(reflectionVector, hitPoint, Albedo(1f, 0f, 0f))
        }

    }
    println("Finished with the random reflections")
}

fun printVectorTracing(pixelVector: Vector) {
    println("Generating vector tracing")

    var dir = pixelVector - camera.position
    var hitRay = Scene.trace(camera.position, dir, 0)
    while (hitRay.name != "sphere") {
        dir = lens.getRandomPixel().center() - camera.position
        hitRay = Scene.trace(camera.position, dir, 0)
    }
    //showVectorAt(dir, camera.position, Albedo(1f), hitRay.dist, 1.0)
    val stack = Stack<Sphere>()
    for (i in 1..10) {
        println(hitRay.name)
        if (hitRay.name == "light") {
            break
        }
        val nextOrig = hitRay.orig + hitRay.dir * hitRay.dist
        stack.add(Sphere(nextOrig, 2.0, Material(Albedo((1f / i)), 0f)))
        val reflection = hitRay.dir.reflectionWithNormal(hitRay.normal)
        hitRay = Scene.trace(nextOrig, reflection, i)

        //showVectorAt(reflection, nextOrig, Albedo((1f - i / 10)), hitRay.dist, 1.0)
    }
    for (sphere in stack) {
        Scene.add(sphere)
    }
    println("Finished with the tracing")
}

fun printRotationMatrix() {
    val v = Vector(0.0, 1.0, 0.0)
    println("Con vector")
    println(getRotationMatirxV2(v))

    println("Con angulos")
    println(getRotationMatrixV2(0.0, Math.PI / 2))

    println("Elegante")
    println(getRotationMatirx(v))
}

fun printCameraPosition() {
    for (frame in 0..59) {
        val alpha = frame * 6 * Math.PI / 180
        val x = 1500 * sin(alpha)
        val y = -1500 * cos(alpha)

        val position = Vector(-x, -y, 0.0)
        val direction = Vector(x, y, 0.0).normalize()
        val rotMatrix = getRotationMatirx(direction)
        val leftLens = position + direction * 500 + rotMatrix * Vector(-50.0, 0.0, 0.0)
        val rightLensDirection = rotMatrix * Vector(1.0, 0.0, 0.0)

        Scene.add(Sphere(leftLens, 10.0, Material(Albedo(1f, 0f, 0f), 0f)))
        Scene.add(Sphere(leftLens + rightLensDirection * 20, 10.0, Material(Albedo(1f, 0f, 0f), 0f)))
        Scene.add(Sphere(leftLens + rightLensDirection * 40, 10.0, Material(Albedo(1f, 0f, 0f), 0f)))
        Scene.add(Sphere(leftLens + rightLensDirection * 60, 10.0, Material(Albedo(1f, 0f, 0f), 0f)))
        Scene.add(Sphere(leftLens + rightLensDirection * 80, 10.0, Material(Albedo(1f, 0f, 0f), 0f)))
        Scene.add(Sphere(leftLens + rightLensDirection * 100, 10.0, Material(Albedo(1f, 0f, 0f), 0f)))

        //println("" + position + "; " + direction  + "; " + leftLens)
        //showVectorAt(leftLens, rightLensDirection, Albedo(1f, 0f, 1f), 100.0, 40.0)
        //showVectorAt(position, direction, Albedo(0f, 0f, 1f), 500.0, 40.0)
    }

    //Scene.add(Sphere(Vector(.0, .0, .0), 00.0, Material(Albedo(1f, 0f, .5f), 0f)))
}