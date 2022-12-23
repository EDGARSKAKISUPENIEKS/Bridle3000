package screen

import config.AppConfig
import screen.pages.Page

object AppController {

    var pages: MutableMap<Int, Page> = mutableMapOf()

    var worldWidth: Float = 0f
        private set
    var worldHeight: Float = 0f
        private set
    var activePage: Int = 1
        set(value) {
            field = value.coerceIn(1, pages.size)
        }

    var beamWidth: Float = 0f
    var beamHeight: Float = 0f

    init {
        worldWidth = AppConfig.DEFAULT_WORLD_WIDTH
        worldHeight = AppConfig.DEFAULT_WORLD_HEIGHT
        beamWidth = AppConfig.DEFAULT_BEAM_WIDTH
        beamHeight = AppConfig.DEFAULT_BEAM_HEIGHT
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