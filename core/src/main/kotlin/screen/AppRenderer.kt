package screen


import Bridle3000Main
import Bridle3000Main.Companion.appInputHandler
import Bridle3000Main.Companion.assetManager
import Bridle3000Main.Companion.inputPlexer
import assets.AssetDescriptors
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import config.AppConfig
import screen.bridle.Beam
import screen.navCircles.*
import screen.pages.FourthPage
import screen.pages.MainPage
import screen.pages.SecondPage
import screen.pages.ThirdPage
import utils.clearScreen
import utils.drawDebugGrid
import utils.logger


class AppRenderer() : Disposable {

    companion object {
        private val log = logger(AppRenderer::class.java)
        val camera: OrthographicCamera = OrthographicCamera()
        val mainPage: MainPage = MainPage()
        val secondPage: SecondPage = SecondPage()
        val thirdPage: ThirdPage = ThirdPage()
        val fourthPage: FourthPage = FourthPage()
        val horizontalNavCircleMain: HorizontalNavCircleMain = HorizontalNavCircleMain()
        val horizontalNavCircleSecond: HorizontalNavCircleSecond = HorizontalNavCircleSecond()
        val horizontalNavCircleThird: HorizontalNavCircleThird = HorizontalNavCircleThird()
        val verticalNavCircleMain: VerticalNavCircleMain = VerticalNavCircleMain()
        val verticalNavCirclePlus1: VerticalNavCirclePlus1 = VerticalNavCirclePlus1()
    }

    //    PRIVATE PROPERTIES

    private val uiFont: BitmapFont by lazy { //vajadzīgs lazy, jo savādāk jāness ārpus klases, jo kautkas piekļūst pirms inicializācija ir pabeigta
        assetManager[AssetDescriptors.UI_FONT]
    }

    private val uiCamera: OrthographicCamera = OrthographicCamera()
    private val viewport: FitViewport =
        FitViewport(AppController.worldWidth, AppController.worldHeight, camera)
    private val uiViewport: FitViewport =
        FitViewport(viewport.worldWidth * 100f, viewport.worldHeight * 100f, uiCamera)
    private val renderer: ShapeRenderer = ShapeRenderer()
    private val batch: SpriteBatch = SpriteBatch()
    private val layout: GlyphLayout = GlyphLayout()
    private val stage: Stage = Stage(uiViewport, batch)


    init {
        Gdx.app.logLevel = Application.LOG_DEBUG
        camera.zoom = 1f
//        pagaidu. Vajag globālu pasaules platumu un ekrānu pozīcijas izritēs no tā
        appInputHandler.setCameraToStartPosition()
        inputPlexer.addProcessor(stage)
    }

    fun render() {
        renderer.projectionMatrix = camera.combined
        appInputHandler.updateCameraPosition(camera, uiCamera)
        clearScreen(Color.GRAY)

//  renderDebug() ir augšā jo savādāk zīmē pa virsu lapu perimetriem
        renderDebug()
        mainPage.render(renderer, batch, uiFont, layout, camera, viewport, uiViewport, uiCamera)
        secondPage.render(renderer, batch, uiFont, layout, camera, viewport, uiViewport, uiCamera)
        thirdPage.render(renderer, batch, uiFont, layout, camera, viewport, uiViewport, uiCamera)
        fourthPage.render(renderer, batch, uiFont, layout, camera, viewport, uiViewport, uiCamera)
        horizontalNavCircleMain.render(renderer, camera, viewport)
        horizontalNavCircleSecond.render(renderer, camera, viewport)
        horizontalNavCircleThird.render(renderer, camera, viewport)
        verticalNavCircleMain.render(renderer, camera, viewport)
        verticalNavCirclePlus1.render(renderer, camera, viewport)


//        vajadzīgs beigās, lai reaģējot uz žestiem tiktu izmantotas pareizie pasaules izmēri camera.unproject()
//        viewport.apply()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }


    override fun dispose() {
        renderer.dispose()
        batch.dispose()
    }

    private fun renderDebug() {
        if (AppConfig.DEBUG_MODE) {
            renderer.projectionMatrix = camera.combined
            viewport.drawDebugGrid(renderer)
        }
    }

}