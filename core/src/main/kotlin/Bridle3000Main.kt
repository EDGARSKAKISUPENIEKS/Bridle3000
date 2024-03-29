import com.badlogic.gdx.*
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.scenes.scene2d.Stage
import screen.AppScreen
import utils.AppInputHandler


/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Bridle3000Main : Game() {

    companion object {
        val appInputHandler = AppInputHandler()
        val assetManager = AssetManager()
        val inputPlexer = InputMultiplexer()
    }


    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        setScreen(AppScreen(this))
//        man neizportamu iemeslu dēļ šo var inicializēt tikai metodē
        val gestureDetector = GestureDetector(appInputHandler)
        gestureDetector.setMaxFlingDelay((0.2 * 1000000000).toLong()) //nav ne mazākās jausmas kāpēc nanosekundēs


        inputPlexer.addProcessor(appInputHandler)
        inputPlexer.addProcessor(gestureDetector)
        Gdx.input.inputProcessor = inputPlexer
    }

    override fun dispose() {
        super.dispose()
        assetManager.dispose()
    }
}
