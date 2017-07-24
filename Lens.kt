/**
 * Created by daniel on 20/07/17.
 */

class Lens(val dist: Double, val width: Double, val xRes: Int, val yRes: Int){
    val pixelSize = width / xRes
    val height = yRes * pixelSize

    val pixels = Array<Array<Pixel>>(yRes) { i ->
        Array<Pixel>(xRes) { j ->
            Pixel(
                    Vector(-width / 2 + j * pixelSize, dist, height / 2 - i * pixelSize),
                    pixelSize
            )
        }
    }

    fun render() : ArrayList<ArrayList<Color>> {

        val colorMatrix: ArrayList<ArrayList<Color>> = ArrayList(pixels.map { arrayPix ->
            ArrayList(arrayPix.map( fun(pixel: Pixel): Color{
                val ray = pixel.center() - camera.position
                val normRay = ray.normalize()
                val hitRay = Scene.trace(camera.position, normRay, 0)
                return hitRay.material.shade(hitRay)
            }))
        })

        var maxColor = 0f
        for (round in 1..params.iterPerPixel - 1) {
            for (i in colorMatrix.indices) {
                for (j in colorMatrix[0].indices) {
                    val pixel = pixels[i][j]
                    val ray = pixel.center() - camera.position
                    val normRay = ray.normalize()
                    val hitRay = Scene.trace(camera.position, normRay, 0)
                    val foundColor = hitRay.material.shade(hitRay)
                    //colorMatrix[i][j] = (colorMatrix[i][j] * round + foundColor) / (round + 1)
                    val newColor = colorMatrix[i][j] + foundColor
                    val nMaxColor = newColor.max()
                    if (nMaxColor > maxColor) {
                        maxColor = nMaxColor
                    }
                    colorMatrix[i][j] = newColor
                }
            }
        }

        if (maxColor > 1f) {
            for (i in colorMatrix.indices) {
                for (j in colorMatrix[i].indices) {
                    colorMatrix[i][j] = colorMatrix[i][j] / maxColor
                }
            }
        }

        return colorMatrix

    }
}