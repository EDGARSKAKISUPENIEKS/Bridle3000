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

class BeamHeight {
    private lateinit var oldColor: Color

    private var leftBeamHeightNumberPosition: Vector2 = Vector2()
    private lateinit var leftBeamHeightNumber: String
    private var leftBeamHeightTextPosition: Vector2 = Vector2()
    private lateinit var rightBeamHeightNumber: String
    private var rightBeamHeightNumberPosition: Vector2 = Vector2()
    private var rightBeamHeightTextPosition: Vector2 = Vector2()
    private var leftTopArrowOutside: Vector2 = Vector2()
    private var leftTopArrowInside: Vector2 = Vector2()
    private var leftTopArrowLeftWing: Vector2 = Vector2()
    private var leftTopArrowRightWing: Vector2 = Vector2()
    private var leftBottomArrowInside: Vector2 = Vector2()
    private var leftBottomArrowOutside: Vector2 = Vector2()
    private var leftBottomArrowLeftWing: Vector2 = Vector2()
    private var leftBottomArrowRightWing: Vector2 = Vector2()
    private var rightTopArrowOutside: Vector2 = Vector2()
    private var rightTopArrowInside: Vector2 = Vector2()
    private var rightTopArrowLeftWing: Vector2 = Vector2()
    private var rightTopArrowRightWing: Vector2 = Vector2()
    private var rightBottomArrowInside: Vector2 = Vector2()
    private var rightBottomArrowOutside: Vector2 = Vector2()
    private var rightBottomArrowLeftWing: Vector2 = Vector2()
    private var rightBottomArrowRightWing: Vector2 = Vector2()

    private var arrowAdjustment: Float = 0f

    private var beamHeightText: String = "height"


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
        renderLeftBeamHeightNumber(uiViewport, batch, uiCamera, leftBeam, debugUiFont, layout)
        renderLeftBeamHeightText(uiViewport, batch, leftBeam, uiCamera, debugUiFont, layout)
        renderLeftHeightArrows(renderer, uiViewport, uiCamera)
        renderRightBeamHeightNumber(uiViewport, batch, uiCamera, rightBeam, debugUiFont, layout)
        renderRightBeamHeightText(uiViewport, batch, rightBeam, uiCamera, debugUiFont, layout)
        renderRightHeightArrows(renderer, uiViewport, uiCamera)
    }

    private fun renderRightBeamHeightText(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        rightBeam: Beam,
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
        layout.setText(debugUiFont, beamHeightText)
        rightBeamHeightTextPosition.set(
            (rightBeam.position.x * 100f) - (layout.width / 2f),
            ((rightBeam.height / 2) * 100f) + layout.height
        )
        debugUiFont.draw(
            batch,
            layout,
            rightBeamHeightTextPosition.x,
            rightBeamHeightTextPosition.y
        )

        debugUiFont.color = oldColor
        batch.end()
        updateRightTopArrowPositions(rightBeam, layout)
    }


    private fun renderLeftBeamHeightText(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        leftBeam: Beam,
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
        layout.setText(debugUiFont, beamHeightText)
        leftBeamHeightTextPosition.set(
            (leftBeam.position.x * 100f) + (leftBeam.xSize * 100f) - (layout.width / 2f),
            ((leftBeam.height * 100f) / 2f) + layout.height
        )
        debugUiFont.draw(batch, layout, leftBeamHeightTextPosition.x, leftBeamHeightTextPosition.y)

        debugUiFont.color = oldColor
        batch.end()
        updateLeftTopArrowPositions(leftBeam, layout)
    }

    private fun renderRightBeamHeightNumber(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        rightBeam: Beam,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        oldColor = debugUiFont.color

        rightBeamHeightNumber = "%.2f".format(
            Locale.ENGLISH,
            rightBeam.height
        )
        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        layout.setText(debugUiFont, rightBeamHeightNumber)
        rightBeamHeightNumberPosition.set(
            (rightBeam.position.x * 100f) - (layout.width / 2f),
            ((rightBeam.height / 2) * 100f) - (layout.height / 2f)
        )
        debugUiFont.draw(
            batch,
            layout,
            rightBeamHeightNumberPosition.x,
            rightBeamHeightNumberPosition.y
        )

        batch.end()
        debugUiFont.color = oldColor
        updateRightBottomArrowPositions(layout)
    }

    private fun renderLeftBeamHeightNumber(
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

        leftBeamHeightNumber = "%.2f".format(Locale.ENGLISH, leftBeam.height)
        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        layout.setText(debugUiFont, leftBeamHeightNumber)
        leftBeamHeightNumberPosition.set(
            (leftBeam.position.x * 100f) + (leftBeam.xSize * 100f) - (layout.width / 2f),
            ((leftBeam.height / 2) * 100f) - (layout.height / 2f)
        )
        debugUiFont.draw(
            batch,
            layout,
            leftBeamHeightNumberPosition.x,
            leftBeamHeightNumberPosition.y
        )

        debugUiFont.color = oldColor
        batch.end()
        updateLeftBottomArrowPositions(layout)
    }


    private fun renderLeftHeightArrows(
        renderer: ShapeRenderer,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera,
    ) {
        oldColor = renderer.color
        renderer.color = Color.BLACK
        uiViewport.apply()
        renderer.projectionMatrix = uiCamera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

//        augšējās bultas vertikālā līnija
        renderer.line(leftTopArrowInside, leftTopArrowOutside)
//        augšējās līnijas kreisā bulta
        renderer.line(leftTopArrowOutside, leftTopArrowLeftWing)
//        augšējās līnijas labā bulta
        renderer.line(leftTopArrowOutside, leftTopArrowRightWing)
//        apakšējās bultas vertikālā līnija
        renderer.line(leftBottomArrowInside, leftBottomArrowOutside)
//        apakšējās līnijas kreisā bulta
        renderer.line(leftBottomArrowOutside, leftBottomArrowLeftWing)
//        apakšējās līnijas labā bulta
        renderer.line(leftBottomArrowOutside, leftBottomArrowRightWing)

        renderer.end()
    }

    private fun updateLeftBottomArrowPositions(layout: GlyphLayout) {
        arrowAdjustment = layout.height / 2f

        leftBottomArrowInside.set(
            leftBeamHeightNumberPosition.x + (layout.width / 2f),
            leftBeamHeightNumberPosition.y - layout.height - arrowAdjustment
        )
        leftBottomArrowOutside.set(
            leftBeamHeightNumberPosition.x + (layout.width / 2f),
            0f + arrowAdjustment
        )
        leftBottomArrowLeftWing.set(
            leftBottomArrowOutside.x - arrowAdjustment,
            leftBottomArrowOutside.y + arrowAdjustment
        )
        leftBottomArrowRightWing.set(
            leftBottomArrowOutside.x + arrowAdjustment,
            leftBottomArrowOutside.y + arrowAdjustment
        )

    }

    private fun updateLeftTopArrowPositions(leftBeam: Beam, layout: GlyphLayout) {
        arrowAdjustment = layout.height / 2

        leftTopArrowOutside.set(
            (leftBeam.position.x * 100f) + (leftBeam.xSize * 100f),
            (leftBeam.height * 100f) - arrowAdjustment
        )
        leftTopArrowInside.set(
            leftBeamHeightTextPosition.x + (layout.width / 2f),
            leftBeamHeightTextPosition.y + arrowAdjustment
        )
        leftTopArrowLeftWing.set(
            leftTopArrowOutside.x - arrowAdjustment,
            leftTopArrowOutside.y - arrowAdjustment
        )
        leftTopArrowRightWing.set(
            leftTopArrowOutside.x + arrowAdjustment,
            leftTopArrowOutside.y - arrowAdjustment
        )
    }

    private fun renderRightHeightArrows(
        renderer: ShapeRenderer,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        oldColor = renderer.color
        renderer.color = Color.BLACK
        uiViewport.apply()
        renderer.projectionMatrix = uiCamera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

//        augšējās bultas vertikālā līnija
        renderer.line(rightTopArrowInside, rightTopArrowOutside)
//        augšējās līnijas kreisā bulta
        renderer.line(rightTopArrowOutside, rightTopArrowLeftWing)
//        augšējās līnijas labā bulta
        renderer.line(rightTopArrowOutside, rightTopArrowRightWing)
//        apakšējās bultas vertikālā līnija
        renderer.line(rightBottomArrowInside, rightBottomArrowOutside)
//        apakšējās līnijas kreisā bulta
        renderer.line(rightBottomArrowOutside, rightBottomArrowLeftWing)
//        apakšējās līnijas labā bulta
        renderer.line(rightBottomArrowOutside, rightBottomArrowRightWing)

        renderer.end()
        renderer.color = oldColor
    }

    private fun updateRightTopArrowPositions(rightBeam: Beam, layout: GlyphLayout) {
        arrowAdjustment = layout.height / 2

        rightTopArrowOutside.set(
            (rightBeam.position.x * 100f),
            (rightBeam.height * 100f) - arrowAdjustment
        )
        rightTopArrowInside.set(
            rightBeamHeightTextPosition.x + (layout.width / 2),
            rightBeamHeightTextPosition.y + arrowAdjustment
        )
        rightTopArrowLeftWing.set(
            rightTopArrowOutside.x - arrowAdjustment,
            rightTopArrowOutside.y - arrowAdjustment
        )
        rightTopArrowRightWing.set(
            rightTopArrowOutside.x + arrowAdjustment,
            rightTopArrowOutside.y - arrowAdjustment
        )
    }

    private fun updateRightBottomArrowPositions(layout: GlyphLayout) {
        arrowAdjustment = layout.height / 2

        rightBottomArrowInside.set(
            rightBeamHeightNumberPosition.x + (layout.width / 2f),
            rightBeamHeightNumberPosition.y - layout.height - arrowAdjustment
        )
        rightBottomArrowOutside.set(
            rightBeamHeightNumberPosition.x + (layout.width / 2f),
            0f + arrowAdjustment
        )
        rightBottomArrowLeftWing.set(
            rightBottomArrowOutside.x - arrowAdjustment,
            rightBottomArrowOutside.y + arrowAdjustment
        )
        rightBottomArrowRightWing.set(
            rightBottomArrowOutside.x + arrowAdjustment,
            rightBottomArrowOutside.y + arrowAdjustment
        )
    }


}