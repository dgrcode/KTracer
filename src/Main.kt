
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

/**
 * Created by daniel on 20/07/17.
 */

object params {
    val iterPerPixel = 10
    val recursionLimit = 25
    val xRes = 600
    val yRes = 600
    val debRadius = 0.5
    val bias = 0.001

    val debug = false
}

object camera {
    val position = Vector(0.0, -1500.0, 200.0)
}

val lens = Lens(camera.position, Vector(0.0, 1500.0, -150.0).normalize(), 500.0, 200.0, params.xRes, params.yRes)

fun main(args: Array<String>) {
    Scene.add(Sphere(Vector(-50.0, -450.0, 30.0), 30.0, Material(Albedo(.1f, .2f, .9f), .5f)))
    Scene.add(Sphere(Vector(60.0, 0.0, 120.0), 120.0, Material(Albedo(.9f, .4f, .2f), .4f)))
    Scene.add(Sphere(Vector(140.0, -250.0, 70.0), 50.0, Material(Albedo(.1f, .9f, .2f), .1f)))
    Scene.add(Sphere(Vector(-300.0, 2700.0, 500.0), 500.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    //Scene.add(Sphere(Vector(50.0, 1300.0, 20.0), 50.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    Scene.add(Sky(Color(1f, 1f, 1f)))
    Scene.add(Plane(Vector(0.0, 0.0, 0.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), .3f)))

    //debugs()

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
