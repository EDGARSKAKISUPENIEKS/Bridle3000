package screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import config.Config
import utils.clearScreen

class AppRenderer : Disposable {

    //    PROPERTIES
    private val camera: OrthographicCamera = OrthographicCamera()
    private val viewport: Viewport =
        FitViewport(Config.DEFAULT_WORLD_WIDTH, Config.DEFAULT_WORLD_HEIGHT, camera)
    private val renderer: ShapeRenderer = ShapeRenderer()

    private val batch: SpriteBatch = SpriteBatch()
    private val image: Texture = Texture("libgdx.png")

    fun render() {

        clearScreen(Color.GRAY)

        renderDebug()
    }

    private fun renderDebug() {
        batch.begin()
        batch.draw(image, 140f, 210f)
        batch.end()

        renderer.begin()
        TODO("Uzzīmēt debug līnijas X un Y asīm")
        renderer.end()
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