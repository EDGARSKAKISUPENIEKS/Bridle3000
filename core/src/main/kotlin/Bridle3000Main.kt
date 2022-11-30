import com.badlogic.gdx.*
import com.badlogic.gdx.input.GestureDetector
import screen.AppScreen
import utils.CameraController


/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Bridle3000Main : Game() {


    private val cameraController = CameraController()
    private val inputPlexer = InputMultiplexer()


    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        setScreen(AppScreen(this))
//        man neizportamu iemeslu dēļ šo var inicializēt tikai metodē
        val gestureDetector = GestureDetector(cameraController)
        gestureDetector.setMaxFlingDelay((0.2 * 1000000000).toLong()) //nav ne mazākās jausmas kāpēc nanosekundēs


        inputPlexer.addProcessor(cameraController)
        inputPlexer.addProcessor(gestureDetector)
        Gdx.input.inputProcessor = inputPlexer
    }

    override fun dispose() {
        super.dispose()
    }
}
