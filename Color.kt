/**
 * Created by daniel on 20/07/17.
 */

class Color(val r: Float, val g: Float, val b: Float) {
    constructor() : this(0f, 0f, 0f)

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

    fun toRGB() : ColorRGB {
        return ColorRGB(Math.round(r * 255), Math.round(g * 255), Math.round(b * 255))
    }
}

class ColorRGB(val r: Int, val g: Int, val b: Int) {
    fun toIntColor() : Int {
        var rgb = r
        rgb = (rgb shl 8) + g
        rgb = (rgb shl 8) + b
        return rgb
    }
}