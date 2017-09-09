/**
 * Created by daniel on 20/07/17.
 */

class Sensor(var normalFromCamera: Vector, val dist: Double, val width: Double, val xRes: Int, val yRes: Int){

    //TODO: HOW TO GET A RANDOM POINT FROM THE LENSE AND THE RAY
    // Get the direction and the origin?
    // Get the origin and the focal point?

    val pixelSize = width / xRes
    val height = yRes * pixelSize
    var rotationMatrix = getRotationMatirx(normalFromCamera)

    var pixels = generatePixels()

    private fun generatePixels() : Array<Array<Pixel>> {
        return Array<Array<Pixel>>(yRes) { i ->
            Array<Pixel>(xRes) { j ->
                Pixel(
                        lens.center + normalFromCamera * dist +
                                rotationMatrix * Vector(-width / 2 + j * pixelSize, 0.0, height / 2 - i * pixelSize),
                        rotationMatrix,
                        pixelSize
                )
            }
        }
    }

    /*
    fun render() : ArrayList<ArrayList<Color>> {

        val round = 1
        var p = 0
        val colorMatrix: ArrayList<ArrayList<Color>> = ArrayList(pixels.map { arrayPix ->
            ArrayList(arrayPix.map( fun(pixel: Pixel): Color{
                print("\rround: " + round + " -> pixel: " + Math.floor(p / params.xRes * 1.0).toInt() + "," + p % params.xRes + "  ")
                val ray = pixel.getRandomPoint() - lensCenter
                val normRay = ray.normalize()
                val hitRay = Scene.trace(lensCenter, normRay, 0)
                p++
                return hitRay.material.shade(hitRay)
            }))
        })

        val roundsWithoutChange: Array<Array<Int>> = Array(yRes) {Array(xRes){0}}
        val pixelHasFinalColor: Array<Array<Boolean>> = Array(yRes) { Array(xRes){false}}
        var pixelFinishedCount = 0
        var rechecking: Boolean

        for (round in 1..params.iterPerPixel - 1) {
            if (params.optimizeIters)
                print("\rround: ${round + 1}; remaininPixels: ${yRes * xRes - pixelFinishedCount} \t pixelFinished: $pixelFinishedCount")

            for (i in colorMatrix.indices) {
                for (j in colorMatrix[0].indices) {
                    rechecking = false
                    if (pixelHasFinalColor[i][j]) {
                        if (Math.random() > params.rechecks) continue
                        rechecking = true
                    }

                    if (!params.optimizeIters)
                        print("\rround: ${round + 1} -> i: $i, j: $j")

                    val pixel = pixels[i][j]
                    val ray = pixel.getRandomPoint() - lensCenter
                    val normRay = ray.normalize()
                    val hitRay = Scene.trace(lensCenter, normRay, 0)
                    val foundColor = hitRay.material.shade(hitRay)
                    //colorMatrix[i][j] = (colorMatrix[i][j] * round + foundColor) / (round + 1)
                    val newColor = (colorMatrix[i][j] * round + foundColor) / (round + 1)
                    val colorDistance = foundColor - newColor
                    if (rechecking and (colorDistance > params.colorTolerance)) {
                        roundsWithoutChange[i][j] = 0
                        pixelHasFinalColor[i][j] = false
                        pixelFinishedCount -= 1
                    }
                    if (params.optimizeIters and (colorDistance < params.colorTolerance )) {
                        roundsWithoutChange[i][j] += 1
                        if (roundsWithoutChange[i][j] == 10) {
                            pixelHasFinalColor[i][j] = true
                            pixelFinishedCount += 1
                        }
                    } /*else {
                        roundsWithoutChange[i][j] = 0
                    }*/
                    colorMatrix[i][j] = newColor
                }
            }
        }

        return colorMatrix

    }
    */

    fun render() : Array<Array<Color>> {
        val colorMatrix: Array<Array<Color>> = Array(yRes) {Array<Color>(xRes){Color()}}
        val roundsWithoutChange: Array<Array<Int>> = Array(yRes) {Array(xRes){0}}
        val pixelHasFinalColor: Array<Array<Boolean>> = Array(yRes) { Array(xRes){false}}
        var pixelFinishedCount = 0
        var rechecking: Boolean

        for (round in 0..params.iterPerPixel - 1) {
            if (params.optimizeIters)
                print("\rround: ${round + 1}; remaininPixels: ${yRes * xRes - pixelFinishedCount} \t pixelFinished: $pixelFinishedCount")

            for (i in 0..yRes - 1) {
                for (j in 0..xRes - 1) {
                    rechecking = false
                    if (pixelHasFinalColor[i][j]) {
                        if (Math.random() > params.rechecks) continue
                        rechecking = true
                    }

                    if (!params.optimizeIters)
                        print("\rround: ${round + 1} -> i: $i, j: $j")

                    val pixel = pixels[i][j]
                    val (lensOrigin, lensRayDirection) = lens.generateRay(pixel)
                    if (params.debug.fieldOfView) println("distance from origin to lens center: ${(lensOrigin - lens.center).modulo()}")
                    val hitRay = Scene.trace(lensOrigin, lensRayDirection, 0)
                    val foundColor = hitRay.material.shade(hitRay)
                    val newColor: Color
                    if (round != 0) {
                        newColor = (colorMatrix[i][j] * round + foundColor) / (round + 1)
                        val colorDistance = foundColor - newColor
                        if (rechecking and (colorDistance > params.colorTolerance)) {
                            roundsWithoutChange[i][j] = 0
                            pixelHasFinalColor[i][j] = false
                            pixelFinishedCount -= 1
                        }
                        if (params.optimizeIters and (colorDistance < params.colorTolerance )) {
                            roundsWithoutChange[i][j] += 1
                            if (roundsWithoutChange[i][j] == 10) {
                                pixelHasFinalColor[i][j] = true
                                pixelFinishedCount += 1
                            }
                        } /*else {
                            roundsWithoutChange[i][j] = 0
                        }*/
                    } else newColor = foundColor

                    colorMatrix[i][j] = newColor
                }
            }
        }

        return colorMatrix
    }

    fun getRandomPixel() : Pixel {
        val y = Math.random() * (yRes) // I had yRes+1 here...
        val x = Math.random() * (xRes) // I had xRes+1 here...
        return pixels[y.toInt()][x.toInt()]
    }

    fun updateNormalVector (newNormal: Vector) {
        normalFromCamera = newNormal
    }

    fun regeneratePixels() {
        rotationMatrix = getRotationMatirx(normalFromCamera)
        pixels = generatePixels()
    }
}