package screen

import config.AppConfig
import utils.CameraController.Companion.pages

object AppController {
    var worldWidth: Float = AppConfig.DEFAULT_WORLD_WIDTH
        private set
    var worldHeight: Float = AppConfig.DEFAULT_WORLD_HEIGHT
        private set
    var activePage: Int = 1
        set(value) {
            field = value.coerceIn(1, pages.size)  //  variants, ja galos apstājas
        }

    fun incrementWorldWidth(string: String) {
        when (string) {
            "up" -> this.worldWidth++
            "down" -> this.worldWidth--
        }
    }

    fun incrementWorldHeight(string: String) {
        when (string) {
            "up" -> this.worldHeight++
            "down" -> this.worldHeight--
        }
    }


}