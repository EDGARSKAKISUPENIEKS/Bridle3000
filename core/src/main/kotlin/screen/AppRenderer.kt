package screen


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
import utils.clearScreen
import utils.drawDebugGrid
import utils.logger

class AppRenderer : Disposable {

    companion object {
        private val log = logger(AppRenderer::class.java)
    }

    //    PROPERTIES
    private val camera: OrthographicCamera = OrthographicCamera()
    private val viewport: Viewport =
        FitViewport(AppConfig.DEFAULT_WORLD_WIDTH, AppConfig.DEFAULT_WORLD_HEIGHT, camera)
    private val renderer: ShapeRenderer = ShapeRenderer()

    private val batch: SpriteBatch = SpriteBatch()
    private val image: Texture = Texture("libgdx.png")


    init {
        camera.zoom = 1f
        Gdx.app.logLevel = Application.LOG_DEBUG
    }

    fun render() {

        clearScreen(Color.GRAY)

        renderDebug()
    }

    private fun renderDebug() {
        renderer.projectionMatrix = camera.combined

        batch.begin()
        batch.draw(image, 140f, 210f)
        batch.end()
//        render current viewport world lines
        viewport.drawDebugGrid(renderer)
    }


    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        renderer.dispose()
        batch.dispose()
        image.dispose()
    }


}