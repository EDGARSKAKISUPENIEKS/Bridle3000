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
    private var heightArrowLower: Vector2 = Vector2()
    private var heightArrowLowerInside: Vector2 = Vector2()
    private var heightArrowLowerRightWing: Vector2 = Vector2()
    private var heightArrowLowerLeftWing: Vector2 = Vector2()
    private var heightArrowUpper: Vector2 = Vector2()
    private var heightArrowUpperInside: Vector2 = Vector2()
    private var heightArrowUpperLeftWing: Vector2 = Vector2()
    private var heightArrowUpperRightWing: Vector2 = Vector2()

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
        renderDistanceTexts(uiViewport, batch, uiCamera, debugUiFont, layout)
        renderHeightText(uiViewport, batch, uiCamera, debugUiFont, layout, load)
        renderHeightNumber(uiViewport, batch, uiCamera, debugUiFont, layout, load)
        renderHeightArrows(renderer, uiViewport, uiCamera)
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

        renderer.color = oldColor
        renderer.end()
    }

    private fun renderDistanceTexts(
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
        layout.setText(debugUiFont, distanceText)
        distanceTextPos.set(
            distanceArrowOutside.x + ((distanceArrowOutsideMid.x - distanceArrowOutside.x) / 2f) - (layout.width / 2f),
            distanceArrowOutside.y + layout.height
        )
        debugUiFont.draw(batch, layout, distanceTextPos.x, distanceTextPos.y)
        distanceTextPos.set(
            distanceArrowInsideMid.x + ((distanceArrowInside.x - distanceArrowInsideMid.x) / 2f) - (layout.width / 2f),
            distanceArrowInside.y + layout.height
        )
        debugUiFont.draw(batch, layout, distanceTextPos.x, distanceTextPos.y)


        debugUiFont.color = oldColor
        batch.end()
    }

    private fun renderHeightText(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        load: Load
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        oldColor = debugUiFont.color

        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        layout.setText(debugUiFont, heightText)
        heightTextPos.set(
            ((load.position.x + load.xSize / 2f) * 100f) - (layout.width / 2f),
            ((load.position.y / 2f) * 100f) + layout.height
        )
        debugUiFont.draw(batch, layout, heightTextPos.x, heightTextPos.y)


        debugUiFont.color = oldColor
        batch.end()
        updateUpperArrowPositions(load, layout)
    }

    private fun updateUpperArrowPositions(
        load: Load,
        layout: GlyphLayout
    ) {
        arrowAdjustment = layout.height / 3

        heightArrowUpper.set(
            (load.position.x + (load.xSize / 2f)) * 100f,
            (load.position.y * 100f) - arrowAdjustment
        )
        heightArrowUpperInside.set(
            heightTextPos.x + (layout.width / 2f),
            heightTextPos.y + arrowAdjustment
        )
        heightArrowUpperLeftWing.set(
            heightArrowUpper.x - arrowAdjustment,
            heightArrowUpper.y - arrowAdjustment
        )
        heightArrowUpperRightWing.set(
            heightArrowUpper.x + arrowAdjustment,
            heightArrowUpper.y - arrowAdjustment
        )
    }

    private fun renderHeightNumber(
        uiViewport: FitViewport,
        batch: SpriteBatch,
        uiCamera: OrthographicCamera,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        load: Load
    ) {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        oldColor = debugUiFont.color

        debugUiFont.color = Color.BLACK
        debugUiFont.data.setScale(0.5f)
        heightNumber =
            "%.2f".format(
                Locale.ENGLISH,
                load.position.y
            )
        layout.setText(debugUiFont, heightNumber)
        heightNumberPos.set(
            ((load.position.x + load.xSize / 2f) * 100f) - (layout.width / 2f),
            ((load.position.y / 2f) * 100f) - (layout.height / 2f)
        )
        debugUiFont.draw(batch, layout, heightNumberPos.x, heightNumberPos.y)


        debugUiFont.color = oldColor
        batch.end()
        updateLowerArrowPositions(layout)
    }

    private fun updateLowerArrowPositions(
        layout: GlyphLayout
    ) {
        arrowAdjustment = layout.height / 3

        heightArrowLowerInside.set(
            heightNumberPos.x + (layout.width / 2f),
            heightNumberPos.y - layout.height - arrowAdjustment
        )
        heightArrowLower.set(
            heightArrowLowerInside.x,
            0f + arrowAdjustment
        )
        heightArrowLowerLeftWing.set(
            heightArrowLower.x - arrowAdjustment,
            heightArrowLower.y + arrowAdjustment
        )
        heightArrowLowerRightWing.set(
            heightArrowLower.x + arrowAdjustment,
            heightArrowLower.y + arrowAdjustment
        )
    }

    private fun renderHeightArrows(
        renderer: ShapeRenderer,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        oldColor = renderer.color
        renderer.color = Color.BLACK
        uiViewport.apply()
        renderer.projectionMatrix = uiCamera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

//        augšējā līnija
        renderer.line(heightArrowUpper, heightArrowUpperInside)
//        augšējā kreisā bulta
        renderer.line(heightArrowUpper, heightArrowUpperLeftWing)
//        augšējā labā bulta
        renderer.line(heightArrowUpper, heightArrowUpperRightWing)
//        apakšējā līnija
        renderer.line(heightArrowLowerInside, heightArrowLower)
//        apakšējā kreisā bulta
        renderer.line(heightArrowLower, heightArrowLowerLeftWing)
//        apakšējā labā bulta
        renderer.line(heightArrowLower, heightArrowLowerRightWing)

        renderer.end()
    }

}