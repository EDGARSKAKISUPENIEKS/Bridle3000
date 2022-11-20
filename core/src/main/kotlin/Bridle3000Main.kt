import com.badlogic.gdx.*
import com.badlogic.gdx.input.GestureDetector
import screen.AppScreen
import utils.CameraController


/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Bridle3000Main : Game() {


    private val cameraController = CameraController()
    private val inputPlexer = InputMultiplexer()
    private val gestureDetector = GestureDetector(cameraController)


    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        setScreen(AppScreen(this))


        inputPlexer.addProcessor(cameraController)
        inputPlexer.addProcessor(gestureDetector)
        Gdx.input.inputProcessor = inputPlexer
    }

    override fun dispose() {
        super.dispose()
    }
}