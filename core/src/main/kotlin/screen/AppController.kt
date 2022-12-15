package screen

import config.AppConfig
import utils.AppInputHandler.Companion.pages

object AppController {
    var worldWidth: Float = 0f
        private set
    var worldHeight: Float = 0f
        private set
    var activePage: Int = 1
        set(value) {
            field = value.coerceIn(1, pages.size)
        }

    init {
        worldWidth = AppConfig.DEFAULT_WORLD_WIDTH
        worldHeight = AppConfig.DEFAULT_WORLD_HEIGHT
    }

    fun update() {
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