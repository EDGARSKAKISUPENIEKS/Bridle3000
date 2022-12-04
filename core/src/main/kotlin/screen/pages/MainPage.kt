package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController
import screen.AppScreen.Companion.controller
import utils.CameraController


private var vectorX: Float = AppController.worldWidth / 2
private var vectorY: Float = AppController.worldHeight / 2

class MainPage : Page(1, Vector2(vectorX, vectorY)) {



    override fun render(renderer: ShapeRenderer) {
        if (this.isActive) {
            updateSize()
            renderer.begin()

            renderer.end()
        }
    }

    override fun updateSize() {
        this.position.x = controller.worldWidth / 2
        this.position.y = controller.worldHeight / 2
        CameraController.pages[this.id] = this.position
    }
}


