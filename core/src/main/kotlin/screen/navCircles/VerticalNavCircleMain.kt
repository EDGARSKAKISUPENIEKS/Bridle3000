package screen.navCircles

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController
import screen.AppRenderer
import utils.AppInputHandler

private val vectorX: Float
    get() {
        return AppInputHandler.pages[AppController.activePage]!!.outerRight - (AppController.worldWidth / 20f)
    }
private val vectorY: Float
    get() {
        return AppInputHandler.pages[AppController.activePage]!!.position.y
    }

class VerticalNavCircleMain : NavCircle(
    AppRenderer.mainPage.id,
    Vector2(vectorX, vectorY)
) {

    override fun isActive(): Boolean {
        return activePagePosY() == circlePosY()
    }


    override fun updatePosition() {
        this.position.x = vectorX
        this.position.y = vectorY
    }
}