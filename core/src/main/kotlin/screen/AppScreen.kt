package screen

import Bridle3000Main
import com.badlogic.gdx.Screen

class AppScreen(val application: Bridle3000Main) : Screen {

    private lateinit var renderer: AppRenderer

    override fun show() {

        renderer = AppRenderer()
    }

    override fun render(delta: Float) {
        renderer.render()
    }

    override fun resize(width: Int, height: Int) {
        renderer.resize(width, height)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun hide() {
        dispose()
    }
}