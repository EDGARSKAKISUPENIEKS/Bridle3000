package screen.bridle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import java.util.*
import kotlin.math.abs

class Load(var position: Vector2) {
    private lateinit var oldColor: Color
    private lateinit var weightNumber: String
    private var weightNumberPos: Vector2 = Vector2()


    var xSize: Float = 0.5f
    var ySize: Float = 0.5f
    var weight: Float = 500.00f
        get() {
            return field
        }
        set(value) {
            field = abs(value)
        }


    fun updatePosition(x: Float, y: Float) {
        this.position.set(x, y)
    }

    fun updatePosition(v: Vector2) {
        this.position.set(v)
    }

    fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        renderWeightNumber(uiViewport, batch, uiCamera, debugUiFont, layout)
    }

    private fun renderWeightNumber(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        oldColor = debugUiFont.color

        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        weightNumber =
            "%.2f".format(
                Locale.ENGLISH, this.weight
            )
        layout.setText(debugUiFont, weightNumber)
        weightNumberPos.set(
            ((this.position.x + (this.xSize / 2f)) * 100f) - (layout.width / 2f),
            ((this.position.y + (this.ySize / 2f)) * 100f) + (layout.height / 2f)
        )
        debugUiFont.draw(batch, layout, weightNumberPos.x, weightNumberPos.y)



        debugUiFont.color = oldColor
        batch.end()
    }

    fun renderDebug(
        renderer: ShapeRenderer,
        viewport: FitViewport,
        camera: OrthographicCamera
    ) {
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        oldColor = renderer.color
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.CYAN

        renderer.rect(this.position.x, this.position.y, this.xSize, this.ySize)

        renderer.color = oldColor
        renderer.end()
    }

}