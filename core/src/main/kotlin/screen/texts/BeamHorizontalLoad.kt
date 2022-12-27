package screen.texts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import screen.AppController
import screen.AppRenderer
import screen.bridle.Beam
import java.util.*
import kotlin.math.abs

class BeamHorizontalLoad {

    private lateinit var oldColor: Color
    private lateinit var beamHorizontalLoadNumber: String

    private var beamHorizontalLoadNumberPosition: Vector2 = Vector2()
    private var beamHorizontalLoadTextPosition: Vector2 = Vector2()
    private var leftArrowInside: Vector2 = Vector2()
    private var leftArrowOutside: Vector2 = Vector2()
    private var leftArrowInsideUpperWing: Vector2 = Vector2()
    private var leftArrowInsideLowerWing: Vector2 = Vector2()
    private var rightArrowInside: Vector2 = Vector2()
    private var rightArrowOutside: Vector2 = Vector2()
    private var rightArrowInsideUpperWing: Vector2 = Vector2()
    private var rightArrowInsideLowerWing: Vector2 = Vector2()

    private var arrowAdjustment: Float = 0f

    private var beamHorizontalLoadText: String = "horizontal load"

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
        renderHorizontalLoadNumber(
            uiViewport,
            batch,
            uiCamera,
            leftBeam,
            rightBeam,
            debugUiFont,
            layout
        )
        renderLeftArrow(renderer, uiViewport, uiCamera)
        renderRightArrow(renderer, uiViewport, uiCamera)
        renderHorizontalLoadText(uiViewport, batch, uiCamera, debugUiFont, layout)
    }

    private fun renderHorizontalLoadText(
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

        batch.end()
    }

    private fun renderRightArrow(
        renderer: ShapeRenderer,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        oldColor = renderer.color
        renderer.color = Color.BLACK
        uiViewport.apply()
        renderer.projectionMatrix = uiCamera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

//        horizntālā līnija
        renderer.line(rightArrowInside, rightArrowOutside)
//        augšējā bulta
        renderer.line(rightArrowInside, rightArrowInsideUpperWing)
//        apakšējā bulta
        renderer.line(rightArrowInside, rightArrowInsideLowerWing)

        renderer.color = oldColor
        renderer.end()
    }


    private fun renderLeftArrow(
        renderer: ShapeRenderer,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        oldColor = renderer.color
        renderer.color = Color.BLACK
        uiViewport.apply()
        renderer.projectionMatrix = uiCamera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

//        horizontālā līnija
        renderer.line(leftArrowOutside, leftArrowInside)
//        augšējā bulta
        renderer.line(leftArrowInside, leftArrowInsideUpperWing)
//        apakšējā bulta
        renderer.line(leftArrowInside, leftArrowInsideLowerWing)

        renderer.color = oldColor
        renderer.end()
    }

    private fun renderHorizontalLoadNumber(
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
        batch.begin()
        beamHorizontalLoadNumber =
            "%.2f".format(
                Locale.ENGLISH,
                123456789.00
            )
        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.75f)
        layout.setText(debugUiFont, beamHorizontalLoadNumber)
        beamHorizontalLoadNumberPosition.set(
            AppRenderer.mainPage.position.x * 100f - (layout.width / 2),
            AppRenderer.mainPage.innerTop * 100f + (layout.height * 2.2f)
        )
        debugUiFont.draw(
            batch,
            layout,
            beamHorizontalLoadNumberPosition.x,
            beamHorizontalLoadNumberPosition.y
        )
        updatePositions(leftBeam, rightBeam, layout)
        batch.end()
    }


    private fun updatePositions(leftBeam: Beam, rightBeam: Beam, layout: GlyphLayout) {
        arrowAdjustment = layout.height / 3

//        beam pozīcijas iziet no galvenā viewport, tāpēc vajag reizināt
        leftArrowOutside.set(
            (leftBeam.position.x + AppController.beamWidth) * 100f + arrowAdjustment,
            beamHorizontalLoadNumberPosition.y - layout.height / 2
        )
        leftArrowInside.set(
            beamHorizontalLoadNumberPosition.x - arrowAdjustment,
            beamHorizontalLoadNumberPosition.y - layout.height / 2
        )
        leftArrowInsideUpperWing.set(
            leftArrowInside.x - arrowAdjustment,
            leftArrowInside.y + arrowAdjustment
        )
        leftArrowInsideLowerWing.set(
            leftArrowInside.x - arrowAdjustment,
            leftArrowInside.y - arrowAdjustment
        )
        rightArrowInside.set(
            beamHorizontalLoadNumberPosition.x + layout.width + arrowAdjustment,
            beamHorizontalLoadNumberPosition.y - layout.height / 2
        )
        rightArrowOutside.set(
            (rightBeam.position.x * 100f) - arrowAdjustment,
            beamHorizontalLoadNumberPosition.y - layout.height / 2
        )
        rightArrowInsideUpperWing.set(
            rightArrowInside.x + arrowAdjustment,
            rightArrowInside.y + arrowAdjustment
        )
        rightArrowInsideLowerWing.set(
            rightArrowInside.x + arrowAdjustment,
            rightArrowInside.y - arrowAdjustment
        )
    }
}