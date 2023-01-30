package screen.pages

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import config.AppConfig
import config.AppConfig.PAGE_BOUND_ADJUSTMENT
import screen.AppController
import screen.AppScreen.Companion.controller


abstract class Page(val id: Int, val position: Vector2) {

    private lateinit var oldColor: Color


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
        update()
    }

    abstract fun update()
    abstract fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        font: BitmapFont,
        layout: GlyphLayout,
        camera: OrthographicCamera,
        viewport: FitViewport,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    )

    open fun renderDebug(
        renderer: ShapeRenderer, batch: SpriteBatch,
        font: BitmapFont,
        layout: GlyphLayout,
        camera: OrthographicCamera,
        viewport: FitViewport,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        if (AppConfig.DEBUG_MODE) {
            renderPerimeters(renderer, viewport, camera)
            renderDebugText(batch, font, layout, camera, uiViewport, uiCamera)
        }
    }

    private fun renderPerimeters(renderer: ShapeRenderer, viewport: FitViewport, camera: OrthographicCamera) {
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        oldColor = renderer.color
        renderer.color = Color.YELLOW
        renderOuterPerimeter(renderer)
        renderInnerPerimeter(renderer)
        renderer.color = oldColor
        renderer.end()
    }

    open fun renderDebugText(
        batch: SpriteBatch,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        camera: OrthographicCamera,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        oldColor = debugUiFont.color
        batch.begin()
        debugUiFont.color = Color.DARK_GRAY
        debugUiFont.data.setScale(1f)
        layout.setText(
            debugUiFont, "${javaClass.simpleName} \n" +
                    "id - ${this.id} \n" +
                    "Active page id - ${AppController.activePage} \n" +
                    "world size - x ${AppController.worldWidth} y ${AppController.worldHeight}"
        )
        debugUiFont.draw(batch, layout, innerTopLeft.x * 100f, (innerTopLeft.y - 0.5f) * 100f)
        debugUiFont.color = oldColor
        batch.end()
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
        AppController.pages[this.id] = this
    }

}


