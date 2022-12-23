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

class BeamDistanceText {
    private lateinit var oldColor: Color
    private lateinit var beamDistanceText: String
    private var textPosition: Vector2 = Vector2()
    private var leftArrowOutside: Vector2 = Vector2()
    private var leftArrowInside: Vector2 = Vector2()
    private var leftArrowUpperWing: Vector2 = Vector2()
    private var leftArrowLowerWing: Vector2 = Vector2()
    private var rightArrowOutside: Vector2 = Vector2()
    private var rightArrowInside: Vector2 = Vector2()
    private var rightArrowUpperWing: Vector2 = Vector2()
    private var rightArrowLowerWing: Vector2 = Vector2()


    fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        font: BitmapFont,
        layout: GlyphLayout,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera,
        leftBeam: Beam,
        rightBeam: Beam,
        page: Page
    ) {
        renderDistanceNumber(uiViewport, batch, uiCamera, leftBeam, rightBeam, font, layout)
        renderLeftArrow(renderer, uiViewport, uiCamera)
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

        renderer.line(leftArrowOutside, leftArrowInside)

        renderer.color = oldColor
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
        updatePositions(leftBeam, layout)
        batch.end()
    }

    private fun updatePositions(leftBeam: Beam, layout: GlyphLayout) {
//        beam pozīcijas no galvenā viewport, tāpēc vajag reizināt
        leftArrowOutside.set(
            (leftBeam.position.x + AppController.beamWidth + 0.25f) * 100f,
            textPosition.y - layout.height / 2
        )
        leftArrowInside.set(
            textPosition.x - layout.width * 0.25f,
            textPosition.y - layout.height / 2
        )
    }
}