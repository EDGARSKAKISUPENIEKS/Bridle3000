package screen.navCircles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import screen.AppController


abstract class NavCircle(val id: Int, val position: Vector2) {

    private lateinit var oldColor: Color


    abstract fun updatePosition()
    open fun render(renderer: ShapeRenderer, camera: OrthographicCamera, viewport: FitViewport) {
        updatePosition()
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        if (isActive()) {
            renderer.begin(ShapeRenderer.ShapeType.Filled)
        } else {
            renderer.begin(ShapeRenderer.ShapeType.Line)
        }
        oldColor = renderer.color
        renderer.color = Color.LIGHT_GRAY
        renderer.circle(this.position.x, this.position.y, 0.2f, 30)

        renderer.color = oldColor
        renderer.end()
    }

    open fun isActive(): Boolean {
        return activePagePosX() == circlePosX() && activePagePosY() == circlePosY()
    }

    fun circlePosY() = AppController.pages[this.id]!!.position.y

    fun activePagePosY() = AppController.pages[AppController.activePage]!!.position.y

    fun circlePosX() = AppController.pages[this.id]!!.position.x

    fun activePagePosX() = AppController.pages[AppController.activePage]!!.position.x
}