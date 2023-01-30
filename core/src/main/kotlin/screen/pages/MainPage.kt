package screen.pages

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import config.AppConfig
import screen.AppController
import screen.AppRenderer
import screen.AppScreen.Companion.controller
import screen.bridle.*
import utils.logger

private val vectorX: Float
    get() {
        return controller.worldWidth / 2
    }
private val vectorY: Float
    get() {
        return controller.worldHeight / 2
    }

class MainPage : Page(1, Vector2(vectorX, vectorY)) {

    override var innerTopLeft: Vector2 = Vector2()
        get() {
            field.x = 0f + horizontalInnerSideAdjustment
            field.y = controller.worldHeight - verticalInnerSideAdjustment
            return field
        }
    override var outerTopLeft: Vector2 = Vector2()
        get() {
            field.x = 0f
            field.y = controller.worldHeight
            return field
        }
    override var innerTopRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth - horizontalInnerSideAdjustment
            field.y = controller.worldHeight - verticalInnerSideAdjustment
            return field
        }
    override var outerTopRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth
            field.y = controller.worldHeight
            return field
        }
    override var innerBottomLeft: Vector2 = Vector2()
        get() {
            field.x = 0f + horizontalInnerSideAdjustment
            field.y = 0f + verticalInnerSideAdjustment
            return field
        }
    override var outerBottomLeft: Vector2 = Vector2()
        get() {
            field.x = 0f
            field.y = 0f
            return field
        }
    override var innerBottomRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth - horizontalInnerSideAdjustment
            field.y = 0f + verticalInnerSideAdjustment
            return field
        }
    override var outerBottomRight: Vector2 = Vector2()
        get() {
            field.x = controller.worldWidth
            field.y = 0f
            return field
        }

    val leftBeam: Beam = Beam(AppController.beamWidth, AppController.beamHeight)
    val rightBeam: Beam = Beam(AppController.beamWidth, AppController.beamHeight)
    val beamHorizontalDistance: BeamHorizontalDistance = BeamHorizontalDistance()
    val beamDimensions: BeamDimensions = BeamDimensions()
    val beamHorizontalLoad: BeamHorizontalLoad = BeamHorizontalLoad()
    val beamHeight: BeamHeight = BeamHeight()
    val beamVerticalLoad: BeamVerticalLoad = BeamVerticalLoad()

    val load: Load = Load(Vector2())
    val loadDistances: LoadDistances = LoadDistances()

    val leftLeg: LeftLeg = LeftLeg()


    private lateinit var oldColor: Color

    companion object {
        private val log = logger(AppRenderer::class.java)
    }

    init {
        this.innerTopLeft = this.innerTopLeft
        this.innerTopRight = this.innerTopRight
        this.innerBottomLeft = this.innerBottomLeft
        this.innerBottomRight = this.innerBottomRight
        leftBeam.updatePosition(innerTopLeft)
        rightBeam.updatePosition(innerTopRight.x - rightBeam.xSize, innerTopRight.y)
        load.updatePosition(
            (controller.worldWidth / 2f) - (load.xSize / 2f),
            (controller.worldHeight / 2f) - (load.ySize / 2f)
        )
        log.debug("mans debug ${load.position}")
    }

    override fun render(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        camera: OrthographicCamera,
        viewport: FitViewport,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        update()
        if (this.isActive) {
            beamHorizontalDistance.render(
                renderer, batch, debugUiFont, layout, uiViewport, uiCamera, leftBeam, rightBeam
            )
            beamDimensions.render(
                uiViewport, batch, uiCamera, leftBeam, rightBeam, debugUiFont, layout
            )
            beamHorizontalLoad.render(
                renderer, batch, debugUiFont, layout, uiViewport, uiCamera, leftBeam, rightBeam
            )
            beamHeight.render(
                renderer, batch, debugUiFont, layout, uiViewport, uiCamera, leftBeam, rightBeam
            )
            beamVerticalLoad.render(
                renderer, batch, debugUiFont, layout, uiViewport, uiCamera, leftBeam, rightBeam
            )
            load.render(renderer, batch, debugUiFont, layout, uiViewport, uiCamera)
            loadDistances.render(
                renderer, batch, debugUiFont, layout, uiViewport, uiCamera, load, leftBeam
            )
            leftLeg.render(
                renderer, batch, debugUiFont, layout, viewport, uiViewport, camera, uiCamera, load,
                leftBeam
            )
        }
        renderDebug(renderer, batch, debugUiFont, layout, camera, viewport, uiViewport, uiCamera)
    }

    override fun renderDebug(
        renderer: ShapeRenderer,
        batch: SpriteBatch,
        debugUiFont: BitmapFont,
        layout: GlyphLayout,
        camera: OrthographicCamera,
        viewport: FitViewport,
        uiViewport: FitViewport,
        uiCamera: OrthographicCamera
    ) {
        super.renderDebug(
            renderer,
            batch,
            debugUiFont,
            layout,
            camera,
            viewport,
            uiViewport,
            uiCamera
        )
        if (AppConfig.DEBUG_MODE) {
            drawDebugBeams(renderer, viewport, camera)
            drawDebugLoad(renderer, viewport, camera)
            leftLeg.renderLegDebug(renderer, viewport, camera)
        }
    }

    private fun drawDebugLoad(
        renderer: ShapeRenderer,
        viewport: FitViewport,
        camera: OrthographicCamera
    ) {
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        oldColor = renderer.color
        renderer.color = Color.CYAN

        renderer.rect(load.position.x, load.position.y, load.xSize, load.ySize)

        renderer.color = oldColor
        renderer.end()
    }


    private fun drawDebugBeams(
        renderer: ShapeRenderer,
        viewport: FitViewport,
        camera: OrthographicCamera
    ) {
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        oldColor = renderer.color
        renderer.color = Color.CYAN

//        leftBeam.updatePosition(innerTopLeft)
//        rightBeam.updatePosition(innerTopRight.x - rightBeam.xSize, innerTopRight.y)

        renderer.rect(leftBeam.position.x, leftBeam.position.y, leftBeam.xSize, leftBeam.ySize)
        renderer.rect(rightBeam.position.x, rightBeam.position.y, rightBeam.xSize, rightBeam.ySize)

        renderer.color = oldColor
        renderer.end()
    }

    override fun update() {
        this.position.x = vectorX
        this.position.y = vectorY
        AppController.pages[this.id] = this
        leftBeam.updatePosition(innerTopLeft)
        rightBeam.updatePosition(innerTopRight.x - rightBeam.xSize, innerTopRight.y)
        load.updatePosition(
            (controller.worldWidth / 2f) - (load.xSize / 2f),
            (controller.worldHeight / 2f) - (load.ySize / 2f)
        )
    }


}


