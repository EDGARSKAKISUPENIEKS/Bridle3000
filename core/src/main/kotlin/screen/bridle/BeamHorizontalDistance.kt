package screen.bridle

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
import java.util.*
import kotlin.math.abs

class BeamHorizontalDistance {
    private lateinit var oldColor: Color
    private lateinit var beamDistanceNumber: String


    private var beamDistanceNumberPosition: Vector2 = Vector2()
    private var beamDistanceTextPosition: Vector2 = Vector2()
    private var leftArrowOutside: Vector2 = Vector2()
    private var leftArrowInside: Vector2 = Vector2()
    private var leftArrowOutsideUpperWingEnd: Vector2 = Vector2()
    private var leftArrowOutsideLowerWingEnd: Vector2 = Vector2()

    private var rightArrowOutside: Vector2 = Vector2()
    private var rightArrowInside: Vector2 = Vector2()
    private var rightArrowOutsideUpperWing: Vector2 = Vector2()
    private var rightArrowOutsideLowerWing: Vector2 = Vector2()


    private var arrowAdjustment: Float = 0f

    private var beamDistanceText: String = "beam distance"


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
        renderDistanceNumber(uiViewport, batch, uiCamera, leftBeam, rightBeam, debugUiFont, layout)
        renderLeftArrow(renderer, uiViewport, uiCamera)
        renderRightArrow(renderer, uiViewport, uiCamera)
        renderDistanceText(uiViewport, batch, uiCamera, debugUiFont, layout)
    }

    private fun renderDistanceText(
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
//        ja teksta platums pārsniedz attālumu starp bultām
        if (layout.width > (abs((leftArrowInside.x - arrowAdjustment) - (leftArrowOutside.x + arrowAdjustment)))) {
            layout.setText(debugUiFont, beamDistanceText.replace(" ", "\n"))
        } else {
            layout.setText(debugUiFont, beamDistanceText)
        }
        debugUiFont.data.setLineHeight(layout.height * 2f)
        beamDistanceTextPosition.set(
            leftArrowInside.x - (abs(leftArrowInside.x - leftArrowOutside.x) / 2) - (layout.width / 2),
            leftArrowInside.y + layout.height
        )

        debugUiFont.draw(batch, layout, beamDistanceTextPosition.x, beamDistanceTextPosition.y)
        beamDistanceTextPosition.set(
            rightArrowOutside.x - (abs(rightArrowInside.x - rightArrowOutside.x) / 2) - (layout.width / 2),
            rightArrowInside.y + layout.height
        )
        debugUiFont.draw(batch, layout, beamDistanceTextPosition.x, beamDistanceTextPosition.y)
        debugUiFont.color = oldColor

        batch.end()
    }

    private fun renderLeftArrow(
        renderer: ShapeRenderer,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera,
    ) {
        oldColor = renderer.color
        renderer.color = Color.BLACK
        uiViewport.apply()
        renderer.projectionMatrix = uiCamera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

//        horizontālā līnija
        renderer.line(leftArrowOutside, leftArrowInside)
//        ārējās bultas augša
        renderer.line(leftArrowOutside, leftArrowOutsideUpperWingEnd)
//        ārējās bultas apakaša
        renderer.line(leftArrowOutside, leftArrowOutsideLowerWingEnd)

        renderer.color = oldColor
        renderer.end()
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

//        horizontālā līnija
        renderer.line(rightArrowInside, rightArrowOutside)
//        ārējā augšējā bulta
        renderer.line(rightArrowOutside, rightArrowOutsideUpperWing)
//        ārējā apakšējā bulta
        renderer.line(rightArrowOutside, rightArrowOutsideLowerWing)

        renderer.color = oldColor
        renderer.end()
    }


    private fun renderDistanceNumber(
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
        beamDistanceNumber =
            "%.2f".format(
                Locale.ENGLISH,
                abs(leftBeam.position.x + AppController.beamWidth - rightBeam.position.x)
            )
        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.75f)
        layout.setText(debugUiFont, beamDistanceNumber)
        beamDistanceNumberPosition.set(
            AppRenderer.mainPage.position.x * 100f - (layout.width / 2),
            AppRenderer.mainPage.innerTop * 100f + layout.height
        )
        debugUiFont.draw(batch, layout, beamDistanceNumberPosition.x, beamDistanceNumberPosition.y)
        updatePositions(leftBeam, rightBeam, layout)
        batch.end()
    }

    private fun updatePositions(leftBeam: Beam, rightBeam: Beam, layout: GlyphLayout) {
        arrowAdjustment = layout.height / 3
//        beam pozīcijas no galvenā viewport, tāpēc vajag reizināt
        leftArrowOutside.set(
            (leftBeam.position.x + AppController.beamWidth) * 100f + arrowAdjustment,
            beamDistanceNumberPosition.y - layout.height / 2
        )
        leftArrowInside.set(
            beamDistanceNumberPosition.x - arrowAdjustment,
            beamDistanceNumberPosition.y - layout.height / 2
        )
        leftArrowOutsideUpperWingEnd.set(
            leftArrowOutside.x + arrowAdjustment,
            leftArrowOutside.y + arrowAdjustment
        )
        leftArrowOutsideLowerWingEnd.set(
            leftArrowOutside.x + arrowAdjustment,
            leftArrowOutside.y - arrowAdjustment
        )
        rightArrowInside.set(
            beamDistanceNumberPosition.x + layout.width + arrowAdjustment,
            beamDistanceNumberPosition.y - layout.height / 2
        )
        rightArrowOutside.set(
            (rightBeam.position.x * 100f) - arrowAdjustment,
            beamDistanceNumberPosition.y - layout.height / 2
        )
        rightArrowOutsideUpperWing.set(
            rightArrowOutside.x - arrowAdjustment,
            rightArrowOutside.y + arrowAdjustment
        )
        rightArrowOutsideLowerWing.set(
            rightArrowOutside.x - arrowAdjustment,
            rightArrowOutside.y - arrowAdjustment
        )
    }
}