package screen

import config.AppConfig

object AppController {
    var worldWith: Float = AppConfig.DEFAULT_WORLD_WIDTH
    private set
    var worldHeight: Float = AppConfig.DEFAULT_WORLD_HEIGHT
    private set
    var activePage: Int = 0
}