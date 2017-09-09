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
    //val debugImagePlane = Sensor(cpos, (-cpos).normalize(), 500.0, 1000.0, 10, 10)

    for (frame in 0..59) {
        println("\nFrame " + frame)

        /*
        val (center, direction) = movementFunction(frame)
        println("center: %s; direction: %s".format(center, direction))
        //println(Scene.objects.size)
        sensor.updateCameraPosition(center)
        sensor.updateNormalVector(direction)
        sensor.regeneratePixels()
        */

        Scene.clear()
        val glassSphere = Sphere(Vector(0.0, 0.0, 250.0), 200.0, Material(Albedo(.9f, .9f, .9f), .9f, 0.8 + frame * 0.05))
        Scene.add(glassSphere)
        Scene.add(Sky(Color(1f, 1f, 1f)))
        Scene.add(Plane(Vector(0.0, 0.0, -10.0), Vector(0.0, 0.0, 1.0), Material(Albedo(.8f, .8f, .8f), .2f)))

        val colorMatrix = sensor.render()

        val image = BufferedImage(sensor.xRes, sensor.yRes, BufferedImage.TYPE_INT_RGB)

        for (i in 0..sensor.yRes - 1) {
            for (j in 0..sensor.xRes - 1) {
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
//    lens.moveTo(Vector(0.0, -15000.0, 1000.0))
//    sensor.updateCameraPosition(lens.center)
//    sensor.updateNormalVector(Vector(0.0, 15000.0, -800.0).normalize())
//    sensor.regeneratePixels()

    //debugs()
    println("ROTATING HORIZONTALLY")

    for (frame in 0..59) {
        println("Frame " + frame)
        val alpha = frame * 6 * Math.PI / 180
        val x = 3000 * cos(alpha)
        val y = 3000 * sin(alpha)
        sensor.updateNormalVector(Vector(-x, -y, 0.0).normalize())
        sensor.regeneratePixels()

        //println(" " + lens.center)

        val colorMatrix = sensor.render()

        val image = BufferedImage(sensor.xRes, sensor.yRes, BufferedImage.TYPE_INT_RGB)

        for (i in 0..sensor.yRes - 1) {
            for (j in 0..sensor.xRes - 1) {
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
//    lens.moveTo(Vector(0.0, -15000.0, 1000.0))
//    sensor.updateCameraPosition(lens.center)
//    sensor.updateNormalVector(Vector(0.0, 15000.0, -800.0).normalize())
//    sensor.regeneratePixels()

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
        lens.moveTo(Vector(0.0, y, z))
        sensor.updateNormalVector(Vector(0.0, -y, -z).normalize())
        sensor.regeneratePixels()

        //println(" " + lens.center)

        val colorMatrix = sensor.render()

        val image = BufferedImage(sensor.xRes, sensor.yRes, BufferedImage.TYPE_INT_RGB)

        for (i in 0..sensor.yRes - 1) {
            for (j in 0..sensor.xRes - 1) {
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

fun customMovement(imagePlane: Sensor, movementFunction: (Int) -> Pair<Vector, Vector>) {
    for (frame in 0..59) {
        println("\nFrame " + frame)
        val (position, direction) = movementFunction(frame)
        println("center: %s; direction: %s".format(position, direction))
        lens.moveTo(position)
        imagePlane.updateNormalVector(direction)
        imagePlane.regeneratePixels()

        //println(" " + lens.center)

        val colorMatrix = imagePlane.render()

        val image = BufferedImage(sensor.xRes, sensor.yRes, BufferedImage.TYPE_INT_RGB)

        for (i in 0..sensor.yRes - 1) {
            for (j in 0..sensor.xRes - 1) {
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
