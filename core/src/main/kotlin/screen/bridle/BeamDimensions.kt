package screen.bridle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import java.util.*

class BeamDimensions {
    private lateinit var leftBeamXSize: String
    private lateinit var leftBeamYSize: String
    private lateinit var rightBeamXSize: String
    private lateinit var rightBeamYSize: String
    private var leftBeamXSizePos: Vector2 = Vector2()
    private var leftBeamYSizePos: Vector2 = Vector2()
    private var rightBeamXSizePos: Vector2 = Vector2()
    private var rightBeamYSizePos: Vector2 = Vector2()

    private lateinit var oldColor: Color


    fun render(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        leftBeam: Beam,
        rightBeam: Beam,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        renderBeamDimensions(uiViewport, batch, uiCamera, leftBeam, rightBeam, debugUiFont, layout)
    }


    private fun renderBeamDimensions(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        leftBeam: Beam,
        rightBeam: Beam,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        oldColor = debugUiFont.color
        batch.begin()

        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)

        leftBeamXSize = "%.2f".format(Locale.ENGLISH, leftBeam.xSize)
        layout.setText(debugUiFont, leftBeamXSize)
        leftBeamXSizePos.set(
            (leftBeam.position.x + (leftBeam.xSize / 2)) * 100f - (layout.width / 2),
            (leftBeam.position.y + leftBeam.ySize) * 100f
        )
        debugUiFont.draw(batch, layout, leftBeamXSizePos.x, leftBeamXSizePos.y)

        leftBeamYSize = "%.2f".format(Locale.ENGLISH, leftBeam.ySize)
        layout.setText(debugUiFont, leftBeamYSize)
        leftBeamYSizePos.set(
            leftBeam.position.x * 100f,
            ((leftBeam.position.y + leftBeam.xSize / 2) * 100f) + layout.height / 2
        )
        debugUiFont.draw(batch, layout, leftBeamYSizePos.x, leftBeamYSizePos.y)

        rightBeamXSize = "%.2f".format(Locale.ENGLISH, rightBeam.xSize)
        layout.setText(debugUiFont, rightBeamXSize)
        rightBeamXSizePos.set(
            (rightBeam.position.x + (rightBeam.xSize / 2)) * 100f - (layout.width / 2),
            (rightBeam.position.y + rightBeam.ySize) * 100f
        )
        debugUiFont.draw(batch, layout, rightBeamXSizePos.x, rightBeamXSizePos.y)

        rightBeamYSize = "%.2f".format(Locale.ENGLISH, rightBeam.ySize)
        layout.setText(debugUiFont, rightBeamYSize)
        rightBeamYSizePos.set(
            ((rightBeam.position.x + rightBeam.xSize) * 100f) - layout.width,
            ((rightBeam.position.y + rightBeam.ySize / 2) * 100f) + layout.height / 2
        )
        debugUiFont.draw(batch, layout, rightBeamYSizePos.x, rightBeamYSizePos.y)

        debugUiFont.color = oldColor
        batch.end()
    }
}