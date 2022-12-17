package screen.navCircles

import com.badlogic.gdx.math.Vector2
import screen.AppController
import screen.AppRenderer
import utils.AppInputHandler

private val vectorX: Float
    get() {
        return AppInputHandler.pages[AppController.activePage]!!.position.x
    }
private val vectorY: Float
    get() {
        return AppInputHandler.pages[AppController.activePage]!!.outerTop - (AppController.worldHeight / 20f)
    }

class HorizontalNavCircleSecond() : NavCircle(
    AppRenderer.secondPage.id,
    Vector2(vectorX, vectorY)
) {

    override fun isActive(): Boolean {
        return activePagePosX() == circlePosX()
    }

    override fun updatePosition() {
        this.position.x = vectorX
        this.position.y = vectorY
    }


}