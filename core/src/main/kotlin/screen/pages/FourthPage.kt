package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController
import utils.CameraController

// virs MainPage
private var vectorX: Float = AppController.worldWith / 2
private var vectorY: Float = (AppController.worldHeight / 2) + AppController.worldHeight

class FourthPage : Page(4, Vector2(vectorX, vectorY)) {
    override fun render(renderer: ShapeRenderer) {
        if (this.isActive) {
            updateSize()
            renderer.begin()

            renderer.end()
        }
    }

    override fun updateSize() {
        this.position.x = AppController.worldWith / 2
        this.position.y = (AppController.worldHeight / 2) + (AppController.worldHeight)
        CameraController.pages[this.id] = this.position
    }
}