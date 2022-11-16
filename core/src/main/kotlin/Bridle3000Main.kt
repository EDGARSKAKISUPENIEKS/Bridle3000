import com.badlogic.gdx.Game
import screen.AppScreen

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Bridle3000Main : Game() {

    override fun create() {

        setScreen(AppScreen(this))
    }

    override fun dispose() {
        super.dispose()
    }
}