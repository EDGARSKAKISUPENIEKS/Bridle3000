import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import screen.AppScreen
import screen.DebugCameraInputProcessor

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Bridle3000Main : Game() {

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        setScreen(AppScreen(this))

    }

    override fun dispose() {
        super.dispose()
    }
}