package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import config.AppConfig
import config.AppConfig.PAGE_BOUND_ADJUSTMENT
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
            return controller.worldWidth / 6
        }
    val verticalInnerSideAdjustment: Float
        get() {
            return controller.worldHeight / 6
        }
    val innerLeft: Float
        get() {
            return innerTopLeft.x
        }
    val innerRight: Float
        get() {
            return innerTopRight.x
        }
    val innerTop: Float
        get() {
            return innerTopLeft.y
        }
    val innerBottom: Float
        get() {
            return innerBottomLeft.y
        }
    val outerLeft: Float
        get() {
            return outerTopLeft.x
        }
    val outerRight: Float
        get() {
            return outerTopRight.x
        }
    val outerTop: Float
        get() {
            return outerTopLeft.y
        }
    val outerBottom: Float
        get() {
            return outerBottomLeft.y
        }


    init {
//        šādi darot multithread gadījumā var sanākt sūdi, privātā metodē esot ok
//        AppInputHandler.pages[this.id] = this
        selfRegister()
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

    abstract fun updateSize()
    abstract fun render(renderer: ShapeRenderer)
    fun renderDebug(renderer: ShapeRenderer) {
        if (AppConfig.DEBUG_MODE) {
            renderer.begin(ShapeRenderer.ShapeType.Line)
            renderOuterPerimeter(renderer)
            renderInnerPerimeter(renderer)
            renderer.end()
        }
    }

    private fun renderOuterPerimeter(renderer: ShapeRenderer) {
        //    augšmala
        renderer.line(
            outerTopLeft.x + PAGE_BOUND_ADJUSTMENT,
            outerTopLeft.y - PAGE_BOUND_ADJUSTMENT,
            outerTopRight.x - PAGE_BOUND_ADJUSTMENT,
            outerTopRight.y - PAGE_BOUND_ADJUSTMENT
        )
        //    labā mala
        renderer.line(
            outerTopRight.x - PAGE_BOUND_ADJUSTMENT,
            outerTopRight.y - PAGE_BOUND_ADJUSTMENT,
            outerBottomRight.x - PAGE_BOUND_ADJUSTMENT,
            outerBottomRight.y + PAGE_BOUND_ADJUSTMENT
        )
        //    apakšmala
        renderer.line(
            outerBottomRight.x - PAGE_BOUND_ADJUSTMENT,
            outerBottomRight.y + PAGE_BOUND_ADJUSTMENT,
            outerBottomLeft.x + PAGE_BOUND_ADJUSTMENT,
            outerBottomLeft.y + PAGE_BOUND_ADJUSTMENT
        )
        //    kreisā mala
        renderer.line(
            outerBottomLeft.x + PAGE_BOUND_ADJUSTMENT,
            outerBottomLeft.y + PAGE_BOUND_ADJUSTMENT,
            outerTopLeft.x + PAGE_BOUND_ADJUSTMENT,
            outerTopLeft.y - PAGE_BOUND_ADJUSTMENT
        )
    }

    private fun renderInnerPerimeter(renderer: ShapeRenderer) {
//        augšmala
        renderer.line(innerTopLeft, innerTopRight)
//        labā mala
        renderer.line(innerTopRight, innerBottomRight)
//        apakšmala
        renderer.line(innerBottomRight, innerBottomLeft)
//        labā mala
        renderer.line(innerBottomLeft, innerTopLeft)
    }

    private fun selfRegister() {
        AppInputHandler.pages[this.id] = this
    }

}


