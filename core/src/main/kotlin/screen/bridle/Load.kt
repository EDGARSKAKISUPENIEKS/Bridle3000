package screen.bridle

import com.badlogic.gdx.math.Vector2

class Load(var position: Vector2) {



    var xSize: Float = 0.5f
    var ySize: Float = 0.5f

    fun updatePosition(x: Float, y: Float) {
        this.position.set(x, y)
    }

    fun updatePosition(v: Vector2) {
        this.position.set(v)
    }
}