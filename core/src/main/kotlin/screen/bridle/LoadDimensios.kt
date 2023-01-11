package screen.bridle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
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
        renderLoadDimensions(uiViewport, batch, uiCamera, load, debugUiFont, layout)
    }

    private fun renderLoadDimensions(
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
            (load.position.x * 100f) + ((load.xSize * 100f) / 2f) - (layout.width / 2f),
            (load.position.y * 100f) + (load.ySize * 100f)
        )
        debugUiFont.draw(batch, layout, loadXSizePos.x, loadXSizePos.y)

        loadYSize = "%.2f".format(Locale.ENGLISH, load.ySize)
        layout.setText(debugUiFont, loadYSize)
        loadYSizePos.set(
            (load.position.x * 100f) + (load.xSize * 100f) - layout.width,
            (load.position.y * 100f) + ((load.ySize * 100f) / 2) + (layout.height / 2f)
        )
        debugUiFont.draw(batch, layout, loadYSizePos.x, loadYSizePos.y)

        debugUiFont.color = oldColor
        batch.end()
    }
}