
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

/**
 * Created by daniel on 20/07/17.
 */

object params {
    val iterPerPixel = 30
}

object camera {
    val position = Vector(0.0, 0.0, 0.0)
}

val lens = Lens(100.0, 200.0, 1200, 600)

fun main(args: Array<String>) {
    Scene.add(Sphere(Vector(-270.0, 460.0, 90.0), 30.0, Material(Albedo(.1f, .2f, .9f), .5f)))
    Scene.add(Sphere(Vector(-60.0, 400.0, 30.0), 100.0, Material(Albedo(.9f, .2f, .1f), .9f)))
    Scene.add(Sphere(Vector(60.0, 280.0, 20.0), 50.0, Material(Albedo(.1f, .9f, .2f), .25f)))
    //Scene.add(Sphere(Vector(-50.0, 280.0, 20.0), 50.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    //Scene.add(Sphere(Vector(50.0, 280.0, 20.0), 50.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    Scene.add(Sky(Color(1f, 1f, 1f)))
    Scene.add(Plane(Vector(0.0, 0.0, -70.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.65f, 0.7f, 0.7f), 0f)))

    val colorMatrix = lens.render()

    val image = BufferedImage(lens.xRes, lens.yRes, BufferedImage.TYPE_INT_RGB)

    for (i in 0..lens.yRes - 1) {
        for (j in 0..lens.xRes - 1) {
            image.setRGB(j, i, colorMatrix[i][j].toRGB().toIntColor())
        }
    }

    val outputFile = File("./output.bmp")
    ImageIO.write(image, "bmp", outputFile)

}
