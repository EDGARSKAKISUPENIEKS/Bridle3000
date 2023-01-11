package screen.bridle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
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
    private var distanceArrowInside: Vector2 = Vector2()
    private var distanceArrowUpperWing: Vector2 = Vector2()
    private var distanceArrowLowerWing: Vector2 = Vector2()
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
        renderDistanceText(uiViewport, batch, uiCamera, debugUiFont, layout, load, leftBeam)
    }


    private fun renderDistanceText(
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
        layout.setText(debugUiFont, distanceText)
        distanceTextPos.set(
            loadCenter(load)
                    - halfDistanceBetweenLoadCenterAndLeftBeamInside(leftBeam, load)
                    - (layout.width / 2f),
            (load.position.y * 100f) - ((load.ySize * 100f) / 2f)
        )
        debugUiFont.draw(batch, layout, distanceTextPos.x, distanceTextPos.y)


        debugUiFont.color = oldColor
        batch.end()
        updateDistanceArrowPositions(leftBeam, load, layout)
    }

    private fun updateDistanceArrowPositions(
        leftBeam: Beam,
        load: Load,
        layout: GlyphLayout
    ) {

    }

    private fun halfDistanceBetweenLoadCenterAndLeftBeamInside(
        leftBeam: Beam,
        load: Load
    ) = abs(
        (((leftBeam.position.x * 100f) + (leftBeam.xSize * 100f)) - (load.position.x * 100f) + (load.xSize * 100f)) / 2f
    )

    private fun loadCenter(
        load: Load
    ) = (load.position.x * 100f) + (load.xSize / 2f)

}