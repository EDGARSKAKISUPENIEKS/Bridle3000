package utils

import com.badlogic.gdx.utils.Logger

fun <T : Any> logger(victimClass: Class<T>): Logger {

    return Logger(victimClass::class.java.simpleName, Logger.DEBUG)

}