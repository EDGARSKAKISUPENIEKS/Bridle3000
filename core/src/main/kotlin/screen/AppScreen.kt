package screen

import Bridle3000Main
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen

class AppScreen(val application: Bridle3000Main) : Screen, InputMultiplexer() {

    private lateinit var renderer: AppRenderer

    override fun show() {

        renderer = AppRenderer()

        Gdx.input.inputProcessor = this
        val inputPlexer = InputMultiplexer()
        inputPlexer.addProcessor(renderer)

    }

    override fun render(delta: Float) {
//        controler.uptade()

//        Appscreen izsauc renderer un controler, kur katrs ir InputProcessor un screen ir multiplexer
//        jāizdomā tikai kā implementēt procesoru neaizdirošot renderer ujn controler klases


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