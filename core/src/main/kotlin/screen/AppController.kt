package screen

import config.AppConfig

object AppController {
    var worldWidth: Float = AppConfig.DEFAULT_WORLD_WIDTH
        private set
    var worldHeight: Float = AppConfig.DEFAULT_WORLD_HEIGHT
        private set
    var activePage: Int = 0
        set(value) {
//            field = value.coerceIn(1, 3)  //  variants, ja galos apstājas
            if (value < 1) {
                field = 3
            } else if (value > 3) {
                field = 1
            }
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