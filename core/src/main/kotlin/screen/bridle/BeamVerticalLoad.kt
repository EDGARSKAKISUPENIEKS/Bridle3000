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

class BeamVerticalLoad {
    private lateinit var oldColor: Color

    private var beamLoadText: String = "vertical load"
    private lateinit var leftBeamLoadNumber: String
    private var leftBeamLoadNumberPosition: Vector2 = Vector2()
    private var leftBeamLoadTextPosition: Vector2 = Vector2()
    private lateinit var rightBeamLoadNumber: String
    private var rightBeamLoadNumberPosition: Vector2 = Vector2()
    private var rightBeamLoadTextPosition: Vector2 = Vector2()
    private var leftTopArrowOutside: Vector2 = Vector2()
    private var leftTopArrowInside: Vector2 = Vector2()
    private var leftTopArrowLeftWing: Vector2 = Vector2()
    private var leftTopArrowRightWing: Vector2 = Vector2()
    private var leftBottomArrowOutside: Vector2 = Vector2()
    private var leftBottomArrowInside: Vector2 = Vector2()
    private var leftBottomArrowLeftWing: Vector2 = Vector2()
    private var leftBottomArrowRightWing: Vector2 = Vector2()
    private var rightTopArrowOutside: Vector2 = Vector2()
    private var rightTopArrowInside: Vector2 = Vector2()
    private var rightTopArrowLeftWing: Vector2 = Vector2()
    private var rightTopArrowRightWing: Vector2 = Vector2()
    private var rightBottomArrowOutside: Vector2 = Vector2()
    private var rightBottomArrowInside: Vector2 = Vector2()
    private var rightBottomArrowLeftWing: Vector2 = Vector2()
    private var rightBottomArrowRightWing: Vector2 = Vector2()

    private var arrowAdjustment: Float = 0f

    fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera,
        leftBeam: Beam,
        rightBeam: Beam
    ) {
        renderLeftBeamLoadNumber(uiViewport, batch, uiCamera, leftBeam, debugUiFont, layout)
    }

    private fun renderLeftBeamLoadNumber(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        leftBeam: Beam,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        oldColor = debugUiFont.color

        leftBeamLoadNumber = "%.2f".format(Locale.ENGLISH, 1000.00)
        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        layout.setText(debugUiFont, leftBeamLoadNumber)
        leftBeamLoadNumberPosition.set(
            (leftBeam.position.x * 100f) - (layout.width / 2f),
            ((leftBeam.height * 100f) / 2f) - (layout.height / 2)
        )
        debugUiFont.draw(batch, layout, leftBeamLoadNumberPosition.x, leftBeamLoadNumberPosition.y)


        debugUiFont.color = oldColor
        batch.end()
        updateLeftBottomArrowPositions(layout)
    }

    private fun updateLeftBottomArrowPositions(layout: GlyphLayout) {
        arrowAdjustment = layout.height / 2f


    }
}