package screen.bridle

import com.badlogic.gdx.math.Vector2

class Beam(var width: Float, var height: Float) {
    var position: Vector2 = Vector2()


    fun updatePosition(x: Float, y: Float) {
        this.position.set(x, y)
    }
    fun updatePosition(v: Vector2) {
        this.position.set(v)
    }
}