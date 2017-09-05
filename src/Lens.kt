/**
 * Created by daniel on 20/07/17.
 */

class Lens(var cameraPos: Vector, var normalFromCamera: Vector, val dist: Double, val width: Double, val xRes: Int, val yRes: Int){
//    constructor(dist: Double, width: Double, xRes: Int, yRes: Int) :
//            this(Vector(0.0, 0.0, 0.0), Vector(0.0, 1.0, 0.0), dist, width, xRes, yRes)

    val pixelSize = width / xRes
    val height = yRes * pixelSize
    var rotationMatrix = getRotationMatirx(normalFromCamera)

//    init {
//        println(rotationMatrix)
//        var lx = rotationMatrix * Vector(1.0, 0.0, 0.0)
//        var ly = rotationMatrix * Vector(0.0, 1.0, 0.0)
//        var lz = rotationMatrix * Vector(0.0, 0.0, 1.0)
//        println("" + lx + " " + ly + " " + lz)
//    }

    var pixels = generatePixels()

    private fun generatePixels() : Array<Array<Pixel>> {
        return Array<Array<Pixel>>(yRes) { i ->
            Array<Pixel>(xRes) { j ->
                Pixel(
                        cameraPos + normalFromCamera * dist +
                                rotationMatrix * Vector(-width / 2 + j * pixelSize, 0.0, height / 2 - i * pixelSize),
                        rotationMatrix,
                        pixelSize
                )
            }
        }
    }

    fun render() : ArrayList<ArrayList<Color>> {

        val round = 1
        var p = 0
        val colorMatrix: ArrayList<ArrayList<Color>> = ArrayList(pixels.map { arrayPix ->
            ArrayList(arrayPix.map( fun(pixel: Pixel): Color{
                print("\rround: " + round + " -> pixel: " + Math.floor(p / params.xRes * 1.0).toInt() + "," + p % params.xRes + "  ")
                val ray = pixel.getRandomPoint() - cameraPos
                val normRay = ray.normalize()
                val hitRay = Scene.trace(cameraPos, normRay, 0)
                p++
                return hitRay.material.shade(hitRay)
            }))
        })

        for (round in 1..params.iterPerPixel - 1) {
            for (i in colorMatrix.indices) {
                for (j in colorMatrix[0].indices) {
                    print("\rround: " + (round + 1) + " -> i: " + i + ", j: " + j)
                    val pixel = pixels[i][j]
                    val ray = pixel.getRandomPoint() - cameraPos
                    val normRay = ray.normalize()
                    val hitRay = Scene.trace(cameraPos, normRay, 0)
                    val foundColor = hitRay.material.shade(hitRay)
                    //colorMatrix[i][j] = (colorMatrix[i][j] * round + foundColor) / (round + 1)
                    val newColor = (colorMatrix[i][j] * round + foundColor) / (round + 1)
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

    fun updateCameraPosition (newCameraPos: Vector) {
        cameraPos = newCameraPos
    }

    fun regeneratePixels() {
        rotationMatrix = getRotationMatirx(normalFromCamera)
        pixels = generatePixels()
    }
}