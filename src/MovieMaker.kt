import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    // Add Scene
    val glassSphere = Sphere(Vector(0.0, 0.0, 250.0), 200.0, Material(Albedo(.9f, .4f, .2f), .4f, GLASS_KR))
    Scene.add(glassSphere)
    Scene.add(Sky(Color(1f, 1f, 1f)))
    Scene.add(Plane(Vector(0.0, 0.0, -10.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), .2f)))


    //val movementFunction : (Int) -> Pair<Vector, Vector> = RotationHorizontal(10200.0, 2000.0).cameraUpdater()
    //val movementFunction : (Int) -> Pair<Vector, Vector> = RotationVertical(10200.0).cameraUpdater()
    //val movementFunction : (Int) -> Pair<Vector, Vector> = TraslationLinear(Vector(0.0, -10000.0, 500.0), Vector(0.0, -1000.0, 500.0)).cameraUpdater()
    //val cpos = Vector(.0, -10000.0, 2000.0)
    //val debugImagePlane = Lens(cpos, (-cpos).normalize(), 500.0, 1000.0, 10, 10)

    for (frame in 0..59) {
        println("\nFrame " + frame)

        /*
        val (position, direction) = movementFunction(frame)
        println("position: %s; direction: %s".format(position, direction))
        //println(Scene.objects.size)
        lens.updateCameraPosition(position)
        lens.updateNormalVector(direction)
        lens.regeneratePixels()
        */

        Scene.clear()
        val glassSphere = Sphere(Vector(0.0, 0.0, 250.0), 200.0, Material(Albedo(.9f, .9f, .9f), .9f, 0.8 + frame * 0.05))
        Scene.add(glassSphere)
        Scene.add(Sky(Color(1f, 1f, 1f)))
        Scene.add(Plane(Vector(0.0, 0.0, -10.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), .2f)))

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

fun rotateHorizontal() {
//    camera.moveTo(Vector(0.0, -15000.0, 1000.0))
//    lens.updateCameraPosition(camera.position)
//    lens.updateNormalVector(Vector(0.0, 15000.0, -800.0).normalize())
//    lens.regeneratePixels()

    //debugs()
    println("ROTATING HORIZONTALLY")

    for (frame in 0..59) {
        println("Frame " + frame)
        val alpha = frame * 6 * Math.PI / 180
        val x = 3000 * cos(alpha)
        val y = 3000 * sin(alpha)
        lens.updateNormalVector(Vector(-x, -y, 0.0).normalize())
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

fun rotateVertical() {
//    camera.moveTo(Vector(0.0, -15000.0, 1000.0))
//    lens.updateCameraPosition(camera.position)
//    lens.updateNormalVector(Vector(0.0, 15000.0, -800.0).normalize())
//    lens.regeneratePixels()

    Scene.add(Sphere(Vector(0.0, 0.0, 250.0), 200.0, Material(Albedo(.9f, .4f, .2f), .4f)))
    //Scene.add(Sphere(Vector(15.0, -25.0, 30.0), 15.0, Material(Albedo(.1f, .9f, .2f), .1f)))
    //Scene.add(Sphere(Vector(-30.0, 50.0, 15.0), 10.0, Material(Albedo(.1f, .9f, .2f), .9f)))
    //Scene.add(Sphere(Vector(0.0, -700.0, 350.0), 350.0, Material(Albedo(.3f, .3f, 1f), .3f, Medium.GLASS)))
    Scene.add(Sky(Color(1f, 1f, 1f)))
    Scene.add(Plane(Vector(0.0, 0.0, 0.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), .2f)))

    //debugs()
    println("ROTATING VERTICALLY")

    for (frame in 0..18) {
        println("\nFrame " + frame)
        val alpha = frame * 3.5 * Math.PI / 180
        val z = 1200 * sin(alpha)
        val y = 1200 * cos(alpha)
        camera.moveTo(Vector(0.0, y, z))
        lens.updateNormalVector(Vector(0.0, -y, -z).normalize())
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

fun customMovement(imagePlane: Lens, movementFunction: (Int) -> Pair<Vector, Vector>) {
    for (frame in 0..59) {
        println("\nFrame " + frame)
        val (position, direction) = movementFunction(frame)
        println("position: %s; direction: %s".format(position, direction))
        imagePlane.updateCameraPosition(position)
        imagePlane.updateNormalVector(direction)
        imagePlane.regeneratePixels()

        //println(" " + camera.position)

        val colorMatrix = imagePlane.render()

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
