package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import config.AppConfig.PAGE_BOUND_ADJUSTMENT
import screen.AppScreen.Companion.controller
import utils.AppInputHandler

// virs MainPage
private var vectorX: Float = controller.worldWidth / 2
private var vectorY: Float = (controller.worldHeight / 2) + controller.worldHeight

class FourthPage : Page(4, Vector2(vectorX, vectorY)) {

    override var innerTopLeft: Vector2 = Vector2()
        get() {
            field.x = 0f + PAGE_BOUND_ADJUSTMENT
            field.y = controller.worldHeight * 2 - PAGE_BOUND_ADJUSTMENT
            return field
        }
    override var outerTopLeft: Vector2 = Vector2()
        get() {
            field.x = 0f
            field.y = controller.worldWidth * 2
            return field
        }
    override var innerTopRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth - PAGE_BOUND_ADJUSTMENT
            field.y = controller.worldHeight + PAGE_BOUND_ADJUSTMENT
            return field
        }
    override var outerTopRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth
            field.y = controller.worldHeight * 2
            return field
        }
    override var innerBottomRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth - PAGE_BOUND_ADJUSTMENT
            field.y = controller.worldHeight + PAGE_BOUND_ADJUSTMENT
            return field
        }
    override var outerBottomRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth
            field.y = controller.worldHeight
            return field
        }
    override var innerBottomLeft: Vector2 = Vector2()
        get() {
            field.x = 0f + PAGE_BOUND_ADJUSTMENT
            field.y = controller.worldHeight + PAGE_BOUND_ADJUSTMENT
            return field
        }
    override var outerBottomLeft: Vector2 = Vector2()
        get() {
            field.x = 0f
            field.y = controller.worldHeight
            return field
        }


    init {
        this.innerTopLeft = this.innerTopLeft
        this.innerTopRight = this.innerTopRight
        this.innerBottomLeft = this.innerBottomLeft
        this.innerBottomRight = this.innerBottomRight
    }

    override fun render(renderer: ShapeRenderer) {
        if (this.isActive) {
            updateSize()


        }
    }

    override fun updateSize() {
        this.position.x = controller.worldWidth / 2
        this.position.y = (controller.worldHeight / 2) + (controller.worldHeight)
        AppInputHandler.pages[this.id] = this.position
    }
}