package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController
import utils.CameraController
import screen.AppScreen.Companion.controller


// pa labi no galvenās, tanī pašā augstumā
private var vectorX: Float = (AppController.worldWidth / 2) + AppController.worldWidth
private var vectorY: Float = AppController.worldHeight / 2

class SecondPage : Page(2, Vector2(vectorX, vectorY)) {


    override fun render(renderer: ShapeRenderer) {
        if (this.isActive) {
            updateSize()
            renderer.begin()

            renderer.end()
        }
    }

    override fun updateSize() {
        this.position.x = (controller.worldWidth / 2) + controller.worldWidth
        this.position.y = controller.worldHeight / 2
        CameraController.pages[this.id] = this.position
    }
}