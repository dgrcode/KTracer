/**
 * Created by daniel on 20/07/17.
 */

class Color(val r: Float, val g: Float, val b: Float) {
    constructor() : this(0f, 0f, 0f)
    constructor(n: Float) : this(n, n, n)

    operator fun plus(other: Color) : Color {
        return Color(r + other.r, g + other.g, b + other.b)
    }

    operator fun times(n: Int) : Color {
        return Color(r * n, g * n, b * n)
    }

    operator fun times(n: Float) : Color {
        return Color(r * n, g * n, b * n)
    }

    operator fun div(n: Int) : Color {
        return Color(r / n, g / n, b / n)
    }

    operator fun div(n: Float) : Color {
        return Color(r / n, g / n, b / n)
    }

    operator fun minus(other: Color): Double {
        val c1Lab = this.toLab()
        val c2Lab = other.toLab()

        return c1Lab - c2Lab
    }

    fun max() : Float {
        return Math.max(Math.max(r, g) , b)
    }

    fun toRGB() : ColorRGB {
        // TODO r, g, b, cannot be more than 1
        val rCorrection = Math.min(r, 1f)
        val gCorrection = Math.min(g, 1f)
        val bCorrection = Math.min(b, 1f)
        return ColorRGB(Math.round(rCorrection * 255), Math.round(gCorrection * 255), Math.round(bCorrection * 255))
    }

    fun toXYZ() : ColorXYZ {

        var rc = if ( r > 0.04045 )
            Math.pow((r + 0.055) / 1.055, 2.4)
        else
            r / 12.92

        var gc = if ( g > 0.04045 )
            Math.pow((g + 0.055) / 1.055, 2.4)
        else
            g / 12.92

        var bc = if ( b > 0.04045 )
            Math.pow((b + 0.055) / 1.055, 2.4)
        else
            b / 12.92

        rc *= 100
        gc *= 100
        bc *= 100

        return ColorXYZ(
                rc * 0.4124 + gc * 0.3576 + bc * 0.1805,
                rc * 0.2126 + gc * 0.7152 + bc * 0.0722,
                rc * 0.0193 + gc * 0.1192 + bc * 0.9505
        )
    }

    fun toLab() : ColorLab {
        return this.toXYZ().toLab()
    }
}

class ColorRGB(val r: Int, val g: Int, val b: Int) {
    operator fun minus(other: ColorRGB) : Double {
        return this.toColor() - other.toColor()
    }

    fun toIntColor() : Int {
        var rgb = r
        rgb = (rgb shl 8) + g
        rgb = (rgb shl 8) + b
        return rgb
    }

    fun toColor() : Color {
        return Color(r / 255f, g / 255f, b / 255f)
    }
}

class ColorXYZ(val X: Double, val Y: Double, val Z: Double) {
    fun toLab() : ColorLab {
        val xRef2 = 95.047
        val yRef2 = 100.000
        val zRef2 = 108.883
        val xRef10 = 94.811
        val yRef10 = 100.000
        val zRef10 = 107.304

        var xc = X / xRef2
        var yc = Y / yRef2
        var zc = Z / zRef2

        xc = if ( xc > 0.008856 )
            Math.pow(xc, 1.0/3)
        else
            7.787 * xc + 16.0 / 116

        yc = if ( yc > 0.008856 )
            Math.pow(yc, 1.0/3)
        else
            7.787 * yc + 16.0 / 116

        zc = if ( zc > 0.008856 )
            Math.pow(zc, 1.0/3)
        else
            7.787 * zc + 16.0 / 116

        return ColorLab(
                116 * yc - 16,
                500 * (xc - yc),
                200 * (yc - zc)
        )
    }
}

class ColorLab(val L: Double, val a: Double, val b: Double) {
    operator fun minus(other: ColorLab) : Double {
        return distanceCie76(this, other)
    }

    fun distanceCie76 (c1: ColorLab, c2: ColorLab) : Double {
        val LDist = c1.L - c2.L
        val aDist = c1.a - c2.a
        val bDist = c1.b - c2.b
        return Math.sqrt(LDist * LDist + aDist * aDist + bDist * bDist)
    }
}