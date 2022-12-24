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
import screen.pages.Page
import java.util.*
import kotlin.math.abs

class BeamDistance {
    private lateinit var oldColor: Color
    private lateinit var beamDistanceText: String
    private var textPosition: Vector2 = Vector2()
    private var leftArrowOutside: Vector2 = Vector2()
    private var leftArrowInside: Vector2 = Vector2()
    private var leftArrowOutsideUpperWingEnd: Vector2 = Vector2()
    private var leftArrowOutsideLowerWingEnd: Vector2 = Vector2()
    private var leftArrowInsideUpperWingEnd: Vector2 = Vector2()
    private var leftArrowInsideLowerWingEnd: Vector2 = Vector2()
    private var rightArrowOutside: Vector2 = Vector2()
    private var rightArrowInside: Vector2 = Vector2()
    private var rightArrowOutsideUpperWing: Vector2 = Vector2()
    private var rightArrowOutsideLowerWing: Vector2 = Vector2()
    private var rightArrowInsideUpperWing: Vector2 = Vector2()
    private var rightArrowInsideLowerWing: Vector2 = Vector2()

    private var arrowAdjustment: Float = 0f


    fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        font: BitmapFont,
        layout: GlyphLayout,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera,
        leftBeam: Beam,
        rightBeam: Beam,
    ) {
        renderDistanceNumber(uiViewport, batch, uiCamera, leftBeam, rightBeam, font, layout)
        renderLeftArrow(renderer, uiViewport, uiCamera)
        renderRightArrow(renderer, uiViewport, uiCamera)
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
//        iekšējās bultas augša
        renderer.line(leftArrowInside, leftArrowInsideUpperWingEnd)
//        iekšējās bultas apakša
        renderer.line(leftArrowInside, leftArrowInsideLowerWingEnd)

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
//        iekšējā augšējā bulta
        renderer.line(rightArrowInside, rightArrowInsideUpperWing)
//        iekšējā apakšējā bulta
        renderer.line(rightArrowInside, rightArrowInsideLowerWing)

        renderer.end()
    }

    private fun renderDistanceNumber(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        leftBeam: Beam,
        rightBeam: Beam,
        font: BitmapFont,
        layout: GlyphLayout
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        beamDistanceText =
            "%.2f".format(
                Locale.ENGLISH,
                abs(leftBeam.position.x + AppController.beamWidth - rightBeam.position.x)
            )
        font.color = Color.BLACK
        font.data.setScale(1f)
        layout.setText(font, beamDistanceText)
        textPosition.set(
            AppRenderer.mainPage.position.x * 100f - (layout.width / 2),
            AppRenderer.mainPage.innerTop * 100f + layout.height
        )
        font.draw(
            batch,
            layout,
            textPosition.x,
            textPosition.y
        )
        updatePositions(leftBeam, rightBeam, layout)
        batch.end()
    }

    private fun updatePositions(leftBeam: Beam, rightBeam: Beam, layout: GlyphLayout) {
        arrowAdjustment = layout.height / 3
//        beam pozīcijas no galvenā viewport, tāpēc vajag reizināt
        leftArrowOutside.set(
            (leftBeam.position.x + AppController.beamWidth) * 100f + arrowAdjustment,
            textPosition.y - layout.height / 2
        )
        leftArrowInside.set(
            textPosition.x - arrowAdjustment,
            textPosition.y - layout.height / 2
        )
        leftArrowOutsideUpperWingEnd.set(
            leftArrowOutside.x + arrowAdjustment,
            leftArrowOutside.y + arrowAdjustment
        )
        leftArrowOutsideLowerWingEnd.set(
            leftArrowOutside.x + arrowAdjustment,
            leftArrowOutside.y - arrowAdjustment
        )
        leftArrowInsideUpperWingEnd.set(
            leftArrowInside.x - arrowAdjustment,
            leftArrowInside.y + arrowAdjustment
        )
        leftArrowInsideLowerWingEnd.set(
            leftArrowInside.x - arrowAdjustment,
            leftArrowInside.y - arrowAdjustment
        )
        rightArrowInside.set(
            textPosition.x + layout.width + arrowAdjustment,
            textPosition.y - layout.height / 2
        )
        rightArrowOutside.set(
            (rightBeam.position.x * 100f) - arrowAdjustment,
            textPosition.y - layout.height / 2
        )
        rightArrowOutsideUpperWing.set(
            rightArrowOutside.x - arrowAdjustment,
            rightArrowOutside.y + arrowAdjustment
        )
        rightArrowOutsideLowerWing.set(
            rightArrowOutside.x - arrowAdjustment,
            rightArrowOutside.y - arrowAdjustment
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