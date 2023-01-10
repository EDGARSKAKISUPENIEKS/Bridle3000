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

    private var beamLoadText: String = "load"
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
        renderLeftBeamLoadText(uiViewport, batch, leftBeam, uiCamera, debugUiFont, layout)
        renderLeftLoadArrows(renderer, uiViewport, uiCamera)
        renderRightBeamLoadNumber(uiViewport, batch, uiCamera, rightBeam, debugUiFont, layout)
        renderRightBeamLoadText(uiViewport, batch, rightBeam, uiCamera, debugUiFont, layout)
        renderRightLoadArrows(renderer, uiViewport, uiCamera)
    }

    private fun renderRightLoadArrows(
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
        renderer.line(rightTopArrowOutside, rightTopArrowInside)
//        augšējās līnijas kreisā bulta
        renderer.line(rightTopArrowInside, rightTopArrowLeftWing)
//        augšējās līnijas labā bulta
        renderer.line(rightTopArrowInside, rightTopArrowRightWing)
//        apakšējās bultas vertikālā līnija
        renderer.line(rightBottomArrowInside, rightBottomArrowOutside)
//        apakšējās līnijas kreisā bulta
        renderer.line(rightBottomArrowOutside, rightBottomArrowLeftWing)
//        apakšējās līnijas labā bulta
        renderer.line(rightBottomArrowOutside, rightBottomArrowRightWing)

        renderer.end()
        renderer.color = oldColor
    }

    private fun renderRightBeamLoadText(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        rightBeam: Beam,
        uiCamera: OrthographicCamera,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        oldColor = debugUiFont.color
        batch.begin()

        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        layout.setText(debugUiFont, beamLoadText)
        rightBeamLoadTextPosition.set(
            (rightBeam.position.x * 100f) + (rightBeam.xSize * 100f) - (layout.width / 2f),
            ((rightBeam.height * 100f) / 2f) + layout.height
        )
        debugUiFont.draw(batch, layout, rightBeamLoadTextPosition.x, rightBeamLoadTextPosition.y)

        debugUiFont.color = oldColor
        batch.end()
        updateRightTopArrowPositions(rightBeam, layout)
    }

    private fun updateRightTopArrowPositions(rightBeam: Beam, layout: GlyphLayout) {
        arrowAdjustment = layout.height / 2f

        rightTopArrowOutside.set(
            (rightBeam.position.x * 100f) + (rightBeam.xSize * 100f),
            (rightBeam.height * 100f) - arrowAdjustment
        )
        rightTopArrowInside.set(
            rightBeamLoadTextPosition.x + (layout.width / 2f),
            rightBeamLoadTextPosition.y + arrowAdjustment
        )
        rightTopArrowLeftWing.set(
            rightTopArrowInside.x - arrowAdjustment,
            rightTopArrowInside.y + arrowAdjustment
        )
        rightTopArrowRightWing.set(
            rightTopArrowInside.x + arrowAdjustment,
            rightTopArrowInside.y + arrowAdjustment
        )
    }

    private fun renderRightBeamLoadNumber(
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

        rightBeamLoadNumber = "%.2f".format(
            Locale.ENGLISH,
            1000.00
        )
        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        layout.setText(debugUiFont, rightBeamLoadNumber)
        rightBeamLoadNumberPosition.set(
            (rightBeam.position.x * 100f) + (rightBeam.xSize * 100f) - (layout.width / 2f),
            ((rightBeam.height * 100f) / 2f) - (layout.height / 2f)
        )
        debugUiFont.draw(
            batch,
            layout,
            rightBeamLoadNumberPosition.x,
            rightBeamLoadNumberPosition.y
        )

        batch.end()
        debugUiFont.color = oldColor
        updateRightBottomArrowPositions(layout)
    }

    private fun updateRightBottomArrowPositions(
        layout: GlyphLayout
    ) {
        arrowAdjustment = layout.height / 2f

        rightBottomArrowInside.set(
            rightBeamLoadNumberPosition.x + (layout.width / 2f),
            rightBeamLoadNumberPosition.y - layout.height - arrowAdjustment
        )
        rightBottomArrowOutside.set(
            rightBeamLoadNumberPosition.x + (layout.width / 2f),
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

    private fun renderLeftLoadArrows(
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
        renderer.line(leftTopArrowInside, leftTopArrowOutside)
//        augšējās līnijas kreisā bulta
        renderer.line(leftTopArrowInside, leftTopArrowLeftWing)
//        augšējās līnijas labā bulta
        renderer.line(leftTopArrowInside, leftTopArrowRightWing)
//        apagšējās bultas vertikālā līnija
        renderer.line(leftBottomArrowInside, leftBottomArrowOutside)
//        apakšējās līnijas kreisā bulta
        renderer.line(leftBottomArrowOutside, leftBottomArrowLeftWing)
//        apakšējās līnijas labā bulta
        renderer.line(leftBottomArrowOutside, leftBottomArrowRightWing)

        renderer.end()
    }

    private fun renderLeftBeamLoadText(
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
        layout.setText(debugUiFont, beamLoadText)
        leftBeamLoadTextPosition.set(
            (leftBeam.position.x * 100f) - (layout.width / 2f),
            ((leftBeam.height * 100f) / 2f) + layout.height
        )
        debugUiFont.draw(batch, layout, leftBeamLoadTextPosition.x, leftBeamLoadTextPosition.y)

        debugUiFont.color = oldColor
        batch.end()
        updateLeftTopArrowPositions(leftBeam, layout)
    }

    private fun updateLeftTopArrowPositions(leftBeam: Beam, layout: GlyphLayout) {
        arrowAdjustment = layout.height / 2f

        leftTopArrowOutside.set(
            (leftBeam.position.x * 100f),
            (leftBeam.position.y * 100f) - arrowAdjustment
        )
        leftTopArrowInside.set(
            leftBeamLoadTextPosition.x + (layout.width / 2f),
            leftBeamLoadTextPosition.y + arrowAdjustment
        )
        leftTopArrowLeftWing.set(
            leftTopArrowInside.x - arrowAdjustment,
            leftTopArrowInside.y + arrowAdjustment
        )
        leftTopArrowRightWing.set(
            leftTopArrowInside.x + arrowAdjustment,
            leftTopArrowInside.y + arrowAdjustment
        )
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

        leftBottomArrowInside.set(
            leftBeamLoadNumberPosition.x + (layout.width / 2f),
            leftBeamLoadNumberPosition.y - layout.height - arrowAdjustment
        )
        leftBottomArrowOutside.set(
            leftBeamLoadNumberPosition.x + (layout.width / 2f),
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
}