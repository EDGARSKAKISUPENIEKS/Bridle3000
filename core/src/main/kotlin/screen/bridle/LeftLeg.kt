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
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class LeftLeg {
    private lateinit var oldColor: Color

    private var beamEnd: Vector2 = Vector2()
    private var pointEnd: Vector2 = Vector2()
    private var legLengthNumberPos: Vector2 = Vector2()
    private lateinit var legLengthString: String

    var legL1: Float = 0f
    var legV1: Float = 0f
    var legH1: Float = 0f


    fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        viewport: FitViewport,
        uiViewport: FitViewport,
        camera: OrthographicCamera,
        uiCamera: OrthographicCamera,
        load: Load,
        leftBeam: Beam
    ) {
        updatePositions(leftBeam, load)
        calculateLegLength(load, leftBeam)
        renderLegLengthNumber(
            uiViewport,
            batch,
            uiCamera,
            leftBeam,
            load,
            debugUiFont,
            layout
        )
    }

    private fun updatePositions(leftBeam: Beam, load: Load) {
        beamEnd.set(
            leftBeam.position.x + leftBeam.xSize,
            leftBeam.position.y
        )
        pointEnd.set(
            load.position.x + (load.xSize / 2f),
            load.position.y + load.ySize
        )
    }

    fun renderLegDebug(
        renderer: ShapeRenderer,
        viewport: FitViewport,
        camera: OrthographicCamera,
    ) {
        oldColor = renderer.color
        renderer.color = Color.CYAN
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

        renderer.rectLine(beamEnd, pointEnd, 0.05f)
////        šo kasti jāliek apkart tekstam, kad tas ir gatavs, lai uz šo kasti varētu tēmēt augšējo un apakšējo kājas daļu
//        renderer.rect(
//            (load.position.x + (load.xSize / 2f)) - ((load.position.x + (load.xSize / 2f) - (leftBeam.position.x + leftBeam.xSize)) / 2f),
//            leftBeam.position.y - ((leftBeam.position.y - (load.position.y + load.xSize))  / 2f),
//            0.5f, 0.5f
//        )
        renderer.color = oldColor
        renderer.end()
    }

    private fun renderLegLengthNumber(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        leftBeam: Beam,
        load: Load,
        debugUiFont: BitmapFont,
        layout: GlyphLayout
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        oldColor = debugUiFont.color
        batch.begin()
        legLengthString =
            "%.2f".format(
                Locale.ENGLISH,
                legL1
            )
        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        layout.setText(debugUiFont, legLengthString)
        legLengthNumberPos.set(
            ((load.position.x + (load.xSize / 2f)) - ((load.position.x + (load.xSize / 2f) - (leftBeam.position.x + leftBeam.xSize)) / 2f)) * 100f,
            (leftBeam.position.y - ((leftBeam.position.y - (load.position.y + load.xSize)) / 2f)) * 100f
        )

        debugUiFont.draw(batch, layout, legLengthNumberPos.x, legLengthNumberPos.y)

        debugUiFont.color = oldColor
        batch.end()
    }


    private fun calculateLegLength(
        load: Load,
        leftBeam: Beam
    ) {
        calculateV1(load, leftBeam)
        calculateH1(load, leftBeam)
        legL1 = sqrt(legH1.pow(2) + legV1.pow(2))
    }

    private fun calculateV1(load: Load, leftBeam: Beam) {
        legV1 = abs(
            leftBeam.position.y - (load.position.y + load.ySize)
        )
    }

    private fun calculateH1(load: Load, leftBeam: Beam) {
        legH1 = load.position.x - (leftBeam.position.x + leftBeam.xSize)
    }


}