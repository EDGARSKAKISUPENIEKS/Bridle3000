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

class LoadDistances {
    private lateinit var oldColor: Color

    private lateinit var distanceNumber: String
    private var distanceNumberPos: Vector2 = Vector2()
    private lateinit var heightNumber: String
    private var heightNumberPos: Vector2 = Vector2()
    private var distanceText: String = "distance"
    private var distanceTextPos: Vector2 = Vector2()
    private var heightText: String = "height"
    private var heightTextPos: Vector2 = Vector2()

    private var distanceArrowOutside: Vector2 = Vector2()
    private var distanceArrowOutsideMid: Vector2 = Vector2()
    private var distanceArrowOutsideUpperWing: Vector2 = Vector2()
    private var distanceArrowOutsideLowerWing: Vector2 = Vector2()
    private var distanceArrowInsideMid: Vector2 = Vector2()
    private var distanceArrowInside: Vector2 = Vector2()
    private var distanceArrowInsideUpperWing: Vector2 = Vector2()
    private var distanceArrowInsideLowerWing: Vector2 = Vector2()
    private var heightArrowOutside: Vector2 = Vector2()
    private var heightArrowInside: Vector2 = Vector2()
    private var heightArrowLeftWing: Vector2 = Vector2()
    private var heightArrowRightWing: Vector2 = Vector2()

    private var arrowAdjustment = 0f

    fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera,
        load: Load,
        leftBeam: Beam
    ) {
        renderDistanceNumber(uiViewport, batch, uiCamera, debugUiFont, layout, load, leftBeam)
        renderDistanceArrows(renderer, uiViewport, uiCamera)
    }


    private fun renderDistanceNumber(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        load: Load,
        leftBeam: Beam
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        oldColor = debugUiFont.color

        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        distanceNumber =
            "%.2f".format(
                Locale.ENGLISH, abs(load.position.x - leftBeam.position.x)
            )
        layout.setText(debugUiFont, distanceNumber)
        distanceNumberPos.set(
            loadCenter(load)
                    - halfDistanceBetweenLoadCenterAndLeftBeamInside(leftBeam, load)
                    - (layout.width / 2f),
            (load.position.y * 100f) - ((load.ySize * 100f) / 2f)
        )
        debugUiFont.draw(batch, layout, distanceNumberPos.x, distanceNumberPos.y)


        debugUiFont.color = oldColor
        batch.end()
        updateDistanceArrowPositions(leftBeam, layout, load)
    }

    private fun updateDistanceArrowPositions(
        leftBeam: Beam,
        layout: GlyphLayout,
        load: Load
    ) {
        arrowAdjustment = layout.height / 3

        distanceArrowOutside.set(
            (leftBeam.position.x * 100f) + (leftBeam.xSize * 100f) + arrowAdjustment,
            distanceNumberPos.y - (layout.height / 2f)
        )
        distanceArrowOutsideMid.set(
            distanceNumberPos.x - arrowAdjustment,
            distanceNumberPos.y - (layout.height / 2f)
        )
        distanceArrowOutsideUpperWing.set(
            distanceArrowOutside.x + arrowAdjustment,
            distanceArrowOutside.y + arrowAdjustment
        )
        distanceArrowOutsideLowerWing.set(
            distanceArrowOutside.x + arrowAdjustment,
            distanceArrowOutside.y - arrowAdjustment
        )
        distanceArrowInsideMid.set(
            distanceNumberPos.x + layout.width + arrowAdjustment,
            distanceNumberPos.y - (layout.height / 2f)
        )
        distanceArrowInside.set(
            (load.position.x * 100f) + ((load.xSize * 100f) / 2f) - arrowAdjustment,
            distanceNumberPos.y - (layout.height / 2f)
        )
        distanceArrowInsideUpperWing.set(
            distanceArrowInside.x - arrowAdjustment,
            distanceArrowInside.y + arrowAdjustment
        )
        distanceArrowInsideLowerWing.set(
            distanceArrowInside.x - arrowAdjustment,
            distanceArrowInside.y - arrowAdjustment
        )
    }

    private fun renderDistanceArrows(
        renderer: ShapeRenderer,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        oldColor = renderer.color
        renderer.color = Color.BLACK
        uiViewport.apply()
        renderer.projectionMatrix = uiCamera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

//        ārējā horizontālā līnija
        renderer.line(distanceArrowOutside, distanceArrowOutsideMid)
//        ārējā augšējā bulta
        renderer.line(distanceArrowOutside, distanceArrowOutsideUpperWing)
//        ārējā apakšējā bulta
        renderer.line(distanceArrowOutside, distanceArrowOutsideLowerWing)
//        iekšējā horizontālā līnija
        renderer.line(distanceArrowInsideMid, distanceArrowInside)
//        iekšējā augšējā bulta
        renderer.line(distanceArrowInside, distanceArrowInsideUpperWing)
//        iekšejā apakšējā bulta
        renderer.line(distanceArrowInside, distanceArrowInsideLowerWing)

        renderer.end()
    }

    private fun renderOutsideArrow(renderer: ShapeRenderer) {


    }

    private fun halfDistanceBetweenLoadCenterAndLeftBeamInside(
        leftBeam: Beam,
        load: Load
    ): Float {
        return abs(
            (((load.position.x + (load.xSize / 2f)) * 100f)
                    - (leftBeam.position.x + leftBeam.xSize) * 100f) / 2f
        )
    }

    private fun loadCenter(
        load: Load
    ) = ((load.position.x + (load.xSize / 2f)) * 100f)

}