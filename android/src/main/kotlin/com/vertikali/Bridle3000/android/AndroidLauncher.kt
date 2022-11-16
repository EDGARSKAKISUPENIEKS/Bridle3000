package com.vertikali.Bridle3000.android

import Bridle3000Main
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration


/** Launches the Android application.  */
class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration = AndroidApplicationConfiguration()
        configuration.apply {
            useAccelerometer = false
            useCompass = false
//            resolutionStrategy = caur šo var dabūt ekrāna izmēru
        }
        initialize(Bridle3000Main(), configuration)
    }
}