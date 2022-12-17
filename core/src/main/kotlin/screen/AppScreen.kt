package screen

import Bridle3000Main
import assets.AssetDescriptors
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import utils.logger

class AppScreen(val application: Bridle3000Main) : Screen, InputMultiplexer() {

    private val assetManager = Bridle3000Main.assetManager

    companion object {
        var controller: AppController = AppController
        var renderer: AppRenderer = AppRenderer()
        private val log = logger(AppRenderer::class.java)
    }


    override fun show() {
        assetManager.load(AssetDescriptors.UI_FONT)


        assetManager.finishLoading()
        log.debug("assetManager diagnostics = ${assetManager.diagnostics}")
    }

    override fun render(delta: Float) {
        controller.update()
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