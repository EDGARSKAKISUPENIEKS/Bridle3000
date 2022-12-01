package screen.pages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import screen.AppController


class MainPage :
    Page(1, AppController.worldWith / 2, AppController.worldHeight / 2) {


    override fun render(renderer: ShapeRenderer) {
        if (this.isActive) {
            renderer.begin()

            renderer.end()
        }
    }



}


