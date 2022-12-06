package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppScreen.Companion.controller
import utils.CameraController

// pa labi no SecondPage, tanī pašā augstumā
private var vectorX: Float = (controller.worldWidth / 2) + (controller.worldWidth * 2)
private var vectorY: Float = controller.worldHeight / 2

class ThirdPage : Page(3, Vector2(vectorX, vectorY)) {

    override fun render(renderer: ShapeRenderer) {
        if (this.isActive) {
            updateSize()

        }
    }

    override fun updateSize() {
        this.position.x = (controller.worldWidth / 2) + (controller.worldWidth * 2)
        this.position.y = controller.worldHeight / 2
        CameraController.pages[this.id] = this.position
    }
}