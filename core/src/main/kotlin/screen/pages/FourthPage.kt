package screen.pages

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import screen.AppController
import screen.AppScreen.Companion.controller

// virs MainPage
private val vectorX: Float
    get() {
        return controller.worldWidth / 2
    }
private val vectorY: Float
    get() {
        return (controller.worldHeight / 2) + controller.worldHeight
    }

class FourthPage : Page(4, Vector2(vectorX, vectorY)) {

    override var innerTopLeft: Vector2 = Vector2()
        get() {
            field.x = 0f + horizontalInnerSideAdjustment
            field.y = controller.worldHeight * 2 - verticalInnerSideAdjustment
            return field
        }
    override var outerTopLeft: Vector2 = Vector2()
        get() {
            field.x = 0f
            field.y = controller.worldHeight * 2
            return field
        }
    override var innerTopRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth - horizontalInnerSideAdjustment
            field.y = controller.worldHeight * 2 - verticalInnerSideAdjustment
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
            field.x = controller.worldWidth - horizontalInnerSideAdjustment
            field.y = controller.worldHeight + verticalInnerSideAdjustment
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
            field.x = 0f + horizontalInnerSideAdjustment
            field.y = controller.worldHeight + verticalInnerSideAdjustment
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

    override fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        camera: OrthographicCamera,
        viewport: FitViewport,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        update()
        if (this.isActive) {


        }
        renderDebug(renderer, batch, debugUiFont, layout, camera, viewport, uiViewport, uiCamera)
    }

    override fun update() {
        this.position.x = vectorX
        this.position.y = vectorY
        AppController.pages[this.id] = this
    }
}