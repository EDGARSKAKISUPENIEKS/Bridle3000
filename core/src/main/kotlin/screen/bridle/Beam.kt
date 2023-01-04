package screen.bridle

import com.badlogic.gdx.math.Vector2

class Beam(var xSize: Float, var ySize: Float) {
    var position: Vector2 = Vector2()

    var height: Float = this.position.y
        get() {
            return this.position.y
        }


    fun updatePosition(x: Float, y: Float) {
        this.position.set(x, y)
    }

    fun updatePosition(v: Vector2) {
        this.position.set(v)
    }
}