package screen.bridle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport

class LeftLeg {
    private lateinit var oldColor: Color

    private var beamEnd: Vector2 = Vector2()
    private var pointEnd: Vector2 = Vector2()

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
        camera: OrthographicCamera
    ) {
        oldColor = renderer.color
        renderer.color = Color.CYAN
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

        renderer.rectLine(beamEnd, pointEnd, 0.05f)


        renderer.color = oldColor
        renderer.end()
    }
}