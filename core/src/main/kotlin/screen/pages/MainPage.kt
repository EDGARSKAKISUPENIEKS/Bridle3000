package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import screen.AppController


class MainPage :
    Page(1, Vector2(AppController.worldWith / 2, AppController.worldHeight / 2)) {


    override fun render(renderer: ShapeRenderer) {
        if (this.isActive) {
            renderer.begin()

            renderer.end()
        }
    }


}


