package config

object Config {

    const val DEFAULT_WORLD_WIDTH = 8f
    const val DEFAULT_WORLD_HEIGHT = 20f
    const val DEFAULT_BEAM_WIDTH = 0.5f
    const val DEFAULT_BEAM_HEIGHT = 0.5f

    // jāņem vērā arī pašas sijas platumu, jo zīmē taisnstūrus no apakšējā kreisā stūra
    const val DEFAULT_LEFT_BEAM_POS_X = -1.5f - DEFAULT_BEAM_WIDTH
    const val DEFAULT_RIGHT_BEAM_POS_X = 1.5F
    const val DEFAULT_BEAM_POS_Y = 18f // pietiek ar vienu vērtību abiem

    const val DEFAULT_LOAD_POS_X = 0f
    const val DEFAULT_LOAD_POS_Y = 12f

}