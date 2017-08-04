
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

/**
 * Created by daniel on 20/07/17.
 */

object params {
    val iterPerPixel = 500
    val recursionLimit = 10
    val xRes = 500
    val yRes = 300
    val debRadius = 0.5
    val bias = 0.001

    val debug = false
}

object camera {
    val position = Vector(0.0, 0.0, 0.0)
}

val lens = Lens(500.0, 200.0, params.xRes, params.yRes)

fun main(args: Array<String>) {
    //Scene.add(Sphere(Vector(-270.0, 460.0, 90.0), 30.0, Material(Albedo(.1f, .2f, .9f), .5f)))
    Scene.add(Sphere(Vector(0.0, 1300.0, 40.0), 110.0, Material(Albedo(.9f, .4f, .2f), .7f)))
    Scene.add(Sphere(Vector(80.0, 900.0, 60.0), 50.0, Material(Albedo(.1f, .9f, .2f), .1f)))
    //Scene.add(Sphere(Vector(-50.0, 1300.0, 20.0), 50.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    //Scene.add(Sphere(Vector(50.0, 1300.0, 20.0), 50.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    Scene.add(Sky(Color(1f, 1f, 1f)))
    Scene.add(Plane(Vector(0.0, 0.0, -70.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), 0f)))

    debugs(lens.getRandomPixel().center())

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
