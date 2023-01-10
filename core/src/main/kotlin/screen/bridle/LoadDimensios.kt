package screen.bridle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import screen.AppRenderer
import utils.logger
import java.util.*

class LoadDimensions {

    private lateinit var loadXSize: String
    private lateinit var loadYSize: String
    private var loadXSizePos: Vector2 = Vector2()
    private var loadYSizePos: Vector2 = Vector2()

    private lateinit var oldColor: Color



    fun render(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        load: Load,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        renderLoadDimensional(uiViewport, batch, uiCamera, load, debugUiFont, layout)
    }

    private fun renderLoadDimensional(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        load: Load,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        oldColor = debugUiFont.color
        batch.begin()

        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)

        loadXSize = "%.2f".format(Locale.ENGLISH, load.xSize)
        layout.setText(debugUiFont, loadXSize)
        loadXSizePos.set(
            load.position.x * 100f,
            load.position.y * 100f
        )
        debugUiFont.draw(batch, layout, loadXSizePos.x, loadXSizePos.y)

        debugUiFont.color = oldColor
        batch.end()
    }
}