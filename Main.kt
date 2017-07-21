
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

/**
 * Created by daniel on 20/07/17.
 */

object params {
    val iterPerPixel = 10
}

object camera {
    val position = Vector(0.0, 0.0, 0.0)
}

val lens = Lens(100.0, 400.0, 300, 200)

fun main(args: Array<String>) {
    Scene.add(Sphere(Vector(50.0, 300.0, 60.0), 100.0, Material(Albedo(.15f, .15f, .15f), 0f)))
    Scene.add(Sky(Color(0.3f, 0.6f, 1f)))

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
