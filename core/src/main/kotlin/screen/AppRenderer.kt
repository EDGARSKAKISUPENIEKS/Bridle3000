package screen


import Bridle3000Main.Companion.appInputHandler
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
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

class AppRenderer : Disposable {

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
    private val image: Texture = Texture("libgdx.png")


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
        mainPage.render(renderer)
        secondPage.render(renderer)
        thirdPage.render(renderer)
        fourthPage.render(renderer)
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
        image.dispose()
    }

    private fun renderDebug() {
        if (AppConfig.DEBUG_MODE) {
            renderer.projectionMatrix = camera.combined

            batch.begin()
            batch.draw(image, 140f, 210f)
            batch.end()


//        render current viewport world lines
            viewport.drawDebugGrid(renderer)
        }
    }

}