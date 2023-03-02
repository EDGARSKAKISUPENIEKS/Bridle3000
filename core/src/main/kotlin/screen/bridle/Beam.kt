package screen.bridle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport

class Beam(var xSize: Float, var ySize: Float) {
    private lateinit var oldColor: Color
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

    fun renderDebug(
        renderer: ShapeRenderer,
        viewport: FitViewport,
        camera: OrthographicCamera
    ){
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        oldColor = renderer.color
        renderer.color = Color.CYAN

        renderer.rect(this.position.x, this.position.y, this.xSize, this.ySize)

        renderer.color = oldColor
        renderer.end()
    }
}