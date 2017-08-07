
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

/**
 * Created by daniel on 20/07/17.
 */

object params {
    val iterPerPixel = 1
    val recursionLimit = 25
    val xRes = 400
    val yRes = 400
    val debRadius = 0.5
    val bias = 0.001

    val debug = false
}

object camera {
    var position = Vector(0.0, 0.0, 30000.0)

    fun moveTo(newPosition: Vector) {
        position = newPosition
    }
}

val lens = Lens(camera.position, Vector(0.0, 0.0, -1.0).normalize(), 1000.0, 100.0, params.xRes, params.yRes)

fun main(args: Array<String>) {
    Scene.add(Sphere(Vector(-10.0, 40.0, 10.0), 10.0, Material(Albedo(.1f, .2f, .9f), .5f)))
    Scene.add(Sphere(Vector(0.0, 0.0, 20.0), 20.0, Material(Albedo(.9f, .4f, .2f), .4f)))
    Scene.add(Sphere(Vector(15.0, -25.0, 30.0), 15.0, Material(Albedo(.1f, .9f, .2f), .1f)))
    Scene.add(Sphere(Vector(-30.0, 50.0, 15.0), 10.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    //Scene.add(Sphere(Vector(50.0, 1300.0, 20.0), 50.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    Scene.add(Sky(Color(1f, 1f, 1f)))
    Scene.add(Plane(Vector(0.0, 0.0, 0.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), .3f)))
    //Scene.add(Sphere(Vector(0.0, -1500.0, 200.0), 50.0, Material(Albedo(1f, 0f, 0f), 0f)))

    debugs()

    val colorMatrix = lens.render()

    val image = BufferedImage(lens.xRes, lens.yRes, BufferedImage.TYPE_INT_RGB)

    for (i in 0..lens.yRes - 1) {
        for (j in 0..lens.xRes - 1) {
            image.setRGB(j, i, colorMatrix[i][j].toRGB().toIntColor())
        }
    }

    val outputFile = File("./output.bmp")
    ImageIO.write(image, "bmp", outputFile)
    println("\nFinished!")

}
