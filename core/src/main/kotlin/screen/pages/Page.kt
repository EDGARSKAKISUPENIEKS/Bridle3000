package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import screen.AppController


abstract class Page(val id: Int, val positionX: Float, val positionY: Float) {

    var isActive: Boolean = false
        private set
        get() {
            return AppController.activePage == this.id
        }

    open fun activate() {
        AppController.activePage = this.id
    }

    abstract fun render(render: ShapeRenderer)


}


