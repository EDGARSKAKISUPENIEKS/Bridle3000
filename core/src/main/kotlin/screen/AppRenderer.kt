package screen


import Bridle3000Main.Companion.appInputHandler
import Bridle3000Main.Companion.assetManager
import assets.AssetDescriptors
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import config.AppConfig
import screen.navCircles.*
import screen.pages.FourthPage
import screen.pages.MainPage
import screen.pages.SecondPage
import screen.pages.ThirdPage
import utils.clearScreen
import utils.drawDebugGrid
import utils.logger

//iznests pirms klases, jo klasei inicializējoties vēl nav gatavs, vai kas tāds un met FATAL ERROR
private val  uiFont: BitmapFont = assetManager[AssetDescriptors.UI_FONT]


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

    private val viewport: Viewport =
        FitViewport(AppController.worldWidth, AppController.worldHeight, camera)
    private val renderer: ShapeRenderer = ShapeRenderer()
    private val batch: SpriteBatch = SpriteBatch()
    private val layout: GlyphLayout = GlyphLayout()


    init {
        Gdx.app.logLevel = Application.LOG_DEBUG
        camera.zoom = 1f
//        pagaidu. Vajag globālu pasaules platumu un ekrānu pozīcijas izritēs no tā
        appInputHandler.setCameraToStartPosition()

    }

    fun render() {
        appInputHandler.updateCameraPosition(camera)
        renderer.projectionMatrix = camera.combined
        clearScreen(Color.GRAY)

//  renderDebug() ir augšā jo savādāk zīmē pa virsu lapu perimetriem
        renderDebug()
        mainPage.render(renderer, batch, uiFont, layout, camera)
        secondPage.render(renderer, batch, uiFont, layout, camera)
        thirdPage.render(renderer, batch, uiFont, layout, camera)
        fourthPage.render(renderer, batch, uiFont, layout, camera)
        horizontalNavCircleMain.render(renderer)
        horizontalNavCircleSecond.render(renderer)
        horizontalNavCircleThird.render(renderer)
        verticalNavCircleMain.render(renderer)
        verticalNavCirclePlus1.render(renderer)
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
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