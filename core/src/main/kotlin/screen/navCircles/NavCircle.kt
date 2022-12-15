package screen.navCircles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController
import utils.AppInputHandler


abstract class NavCircle(val id: Int, val position: Vector2) {

    private lateinit var oldColor: Color


    abstract fun updatePosition()
    open fun render(renderer: ShapeRenderer) {
        updatePosition()
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

    fun circlePosY() = AppInputHandler.pages[this.id]!!.position.y

    fun activePagePosY() = AppInputHandler.pages[AppController.activePage]!!.position.y

    fun circlePosX() = AppInputHandler.pages[this.id]!!.position.x

    fun activePagePosX() = AppInputHandler.pages[AppController.activePage]!!.position.x
}