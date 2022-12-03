package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController
import utils.CameraController


abstract class Page(val id: Int, val position: Vector2) {

    init {
        CameraController.pages[this.id] = this.position
    }

    var isActive: Boolean = false
        private set
        get() {
            return AppController.activePage == this.id
        }

    open fun activate() {
        AppController.activePage = this.id
        updateSize()
    }

    abstract fun render(renderer: ShapeRenderer)
    abstract fun updateSize()


}


