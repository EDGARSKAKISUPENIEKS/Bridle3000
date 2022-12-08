package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import config.AppConfig.PAGE_BOUND_ADJUSTMENT
import screen.AppScreen.Companion.controller
import utils.AppInputHandler


// pa labi no galvenās, tanī pašā augstumā
private var vectorX: Float = (controller.worldWidth / 2) + controller.worldWidth
private var vectorY: Float = controller.worldHeight / 2

class SecondPage : Page(2, Vector2(vectorX, vectorY)) {

    override var innerTopLeft: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth + PAGE_BOUND_ADJUSTMENT
            field.y = controller.worldHeight - PAGE_BOUND_ADJUSTMENT
            return field
        }
    override var outerTopLeft: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth
            field.y = controller.worldHeight
            return field
        }
    override var innerTopRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth * 2 - PAGE_BOUND_ADJUSTMENT
            field.y = controller.worldHeight - PAGE_BOUND_ADJUSTMENT
            return field
        }
    override var outerTopRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth * 2
            field.y = controller.worldHeight
            return field
        }
    override var innerBottomLeft: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth + PAGE_BOUND_ADJUSTMENT
            field.y = 0f + PAGE_BOUND_ADJUSTMENT
            return field
        }
    override var outerBottomLeft: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth
            field.y = 0f
            return field
        }
    override var innerBottomRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth * 2 + PAGE_BOUND_ADJUSTMENT
            field.y = 0f + PAGE_BOUND_ADJUSTMENT
            return field
        }
    override var outerBottomRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth * 2
            field.y = 0f
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
        this.position.x = (controller.worldWidth / 2) + controller.worldWidth
        this.position.y = controller.worldHeight / 2
        AppInputHandler.pages[this.id] = this.position
    }
}