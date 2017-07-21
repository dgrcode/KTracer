/**
 * Created by daniel on 20/07/17.
 */

class Albedo(val r: Float, val g: Float, val b: Float) {
    constructor() : this (0f, 0f, 0f)

    operator fun times(color: Color) : Color {
        return Color(r * color.r, g * color.g, b * color.b)
    }
}