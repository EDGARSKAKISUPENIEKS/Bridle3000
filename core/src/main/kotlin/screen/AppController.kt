package screen

import config.AppConfig

object AppController {
    var worldWidth: Float = AppConfig.DEFAULT_WORLD_WIDTH
        private set
    var worldHeight: Float = AppConfig.DEFAULT_WORLD_HEIGHT
        private set
    var activePage: Int = 0

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