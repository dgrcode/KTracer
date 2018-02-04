/*
    Defined as the base with possitive orientation (counter-clockwise) and the top vertex
 */
class Pyramid(v0: Vector, v1: Vector, v2: Vector, v3: Vector, material: Material) {
    val face1 = Triangle(v0, v1, v3, material)
    val face2 = Triangle(v1, v2, v3, material)
    val face3 = Triangle(v2, v0, v3, material)
    val base = Triangle(v0, v2, v1, material)

    fun addToScene() {
        Scene.add(face1)
        Scene.add(face2)
        Scene.add(face3)
        Scene.add(base)
    }
}