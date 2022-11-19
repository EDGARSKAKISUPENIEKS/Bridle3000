package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import java.awt.image.ImageObserver.PROPERTIES

//  CONSTANTS
private const val CAMERA_ZOOM_SPEED = 2f
private const val CAMERA_MOVE_SPEED = 5f
private const val CAMERA_MAX_ZOOM_OUT = 5f
private const val CAMERA_MAX_ZOOM_IN = 0.25f

//  PROPERTIES
private val position = Vector2()
private val startPosition = Vector2()
private var zoom = 1f
    set(value) {
        field = MathUtils.clamp(value, CAMERA_MAX_ZOOM_IN, CAMERA_MAX_ZOOM_OUT)
    }

//  PUBLIC FUNCTIONS

fun setStartPosition(x: Float, y: Float) {
    startPosition.set(x, y)
    position.set(x, y)
}

fun setPosition(x: Float, y: Float) {
    position.set(x, y)
}

fun moveCameraLeft(delta: Float) {
//    setPosition(camera.position.x - (CAMERA_MOVE_SPEED * delta), camera.position.y)
}