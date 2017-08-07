import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    makeMatrix()
}

fun makeMatrix() {
    camera.moveTo(Vector(0.0, -1500.0, 200.0))
    lens.updateCameraPosition(camera.position)
    lens.updateNormalVector(Vector(0.0, 1500.0, -200.0).normalize())
    lens.regeneratePixels()

    Scene.add(Sphere(Vector(-10.0, 40.0, 10.0), 10.0, Material(Albedo(.1f, .2f, .9f), .5f)))
    Scene.add(Sphere(Vector(0.0, 0.0, 20.0), 20.0, Material(Albedo(.9f, .4f, .2f), .4f)))
    Scene.add(Sphere(Vector(15.0, -25.0, 30.0), 15.0, Material(Albedo(.1f, .9f, .2f), .1f)))
    Scene.add(Sphere(Vector(-30.0, 50.0, 15.0), 10.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    Scene.add(Sky(Color(1f, 1f, 1f)))
    Scene.add(Plane(Vector(0.0, 0.0, 0.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), .3f)))

    for (frame in 0..59) {
        println("Frame " + frame)
        val alpha = frame * 6 * Math.PI / 180
        val x = 1500 * sin(alpha)
        val y = -1500 * cos(alpha)
        camera.moveTo(Vector(x, y, 200.0))
        lens.updateNormalVector(Vector(-x, -y, -200.0).normalize())
        lens.updateCameraPosition(camera.position)
        lens.regeneratePixels()

        //println(" " + camera.position)

        val colorMatrix = lens.render()

        val image = BufferedImage(lens.xRes, lens.yRes, BufferedImage.TYPE_INT_RGB)

        for (i in 0..lens.yRes - 1) {
            for (j in 0..lens.xRes - 1) {
                image.setRGB(j, i, colorMatrix[i][j].toRGB().toIntColor())
            }
        }

        var frameString = frame.toString()
        if (frameString.length == 1) {
            frameString = "0" + frameString
        }
        val outputFile = File("./movieFrames/output_0" + frameString + ".bmp")
        ImageIO.write(image, "bmp", outputFile)
    }


    println("\nFinished!")
}