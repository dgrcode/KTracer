
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

/**
 * Created by daniel on 20/07/17.
 */

object params {
    val iterPerPixel = 300
    val recursionLimit = 25
    val xRes = 600
    val yRes = 400
    val debRadius = 0.5
    val bias = 0.001

    val optimizeIters = true
    val colorTolerance = 5.0
    val rechecks = 0.2 // probability of rechecking a supposedly finished pixel

    object debug {
        val global = false
        val hitStack =false
        val recursionLimit = false

        // refraction
        val refractionGlobal = false
        val refractionAngle = false

        // field of view
        val fieldOfView = false

        val refraction = refractionGlobal or refractionAngle
        val any = global or hitStack or recursionLimit or refraction
    }
}

val lens = Lens(Vector(61.5, -316.5, 50.0), GeometryCircle(0.0))
val focalDist: Double = 166.5
val sensor = Sensor(Vector(.0, 1.0, .0).normalize(), 316.1, 523.2, params.xRes, params.yRes)

fun main(args: Array<String>) {
    Scene.add(Sphere(Vector(61.5, -150.0, 30.0), 30.0, Material.GOLD))
    Scene.add(Sphere(Vector(150.0, -100.0, 70.0), 30.0, Material.GOLD))
    Scene.add(Sphere(Vector(-10.0, -100.0, 80.0), 30.0, Material.GOLD))
    Scene.add(Sphere(Vector(0.0, 100.0, 100.0), 100.0, Material.GOLD))
    Scene.add(Sphere(Vector(173.0, .0, 120.0), 50.0, Material.GOLD))
    Scene.add(Sky(Color(1.5f, 1.5f, 1.5f)))
    Scene.add(Plane(Vector(0.0, 0.0, 0.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), .0f)))

    val cpos = Vector(.0, 1.0, 1.0) * 1200.0

    val colorMatrix = sensor.render()

    val image = BufferedImage(sensor.xRes, sensor.yRes, BufferedImage.TYPE_INT_RGB)

    for (i in 0..sensor.yRes - 1) {
        for (j in 0..sensor.xRes - 1) {
            image.setRGB(j, i, colorMatrix[i][j].toRGB().toIntColor())
        }
    }

    val outputFile = File("./output.bmp")
    ImageIO.write(image, "bmp", outputFile)
    println("\nFinished!")

}
