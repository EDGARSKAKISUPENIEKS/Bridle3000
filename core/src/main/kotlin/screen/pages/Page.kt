package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController
import utils.AppInputHandler
import screen.AppScreen.Companion.controller


abstract class Page(val id: Int, val position: Vector2) {

    abstract var innerTopLeft: Vector2
    abstract var outerTopLeft: Vector2
    abstract var innerTopRight: Vector2
    abstract var outerTopRight: Vector2
    abstract var innerBottomLeft: Vector2
    abstract var outerBottomLeft: Vector2
    abstract var innerBottomRight: Vector2
    abstract var outerBottomRight: Vector2
    val horizontalInnerSideAdjustment: Float
        get() {
            return controller.worldWidth / 3
        }
    val verticalInnerSideAdjustment: Float
        get() {
            return controller.worldHeight / 3
        }

    init {
        AppInputHandler.pages[this.id] = this.position
    }

    var isActive: Boolean = false
        private set
        get() {
            return AppController.activePage == this.id
        }

    open fun activate() {
        AppController.activePage = this.id
        updateSize()
    }

    abstract fun render(renderer: ShapeRenderer)
    abstract fun updateSize()


}


