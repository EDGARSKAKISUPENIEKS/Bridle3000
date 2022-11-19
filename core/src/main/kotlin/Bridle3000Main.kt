import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import screen.AppRenderer
import screen.AppScreen
import utils.CameraController


/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Bridle3000Main : Game() {

    private val cameraController = CameraController()

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        setScreen(AppScreen(this))
        Gdx.input.inputProcessor = InputMultiplexer(cameraController)


    }

    override fun dispose() {
        super.dispose()
    }
}