package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController
import utils.CameraController

// pa labi no galvenās, tanī pašā augstumā
private var vectorX: Float = (AppController.worldWith / 2) + AppController.worldWith
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
        this.position.x = (AppController.worldWith / 2) + AppController.worldWith
        this.position.y = AppController.worldHeight / 2
        CameraController.pages[this.id] = this.position
    }
}