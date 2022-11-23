package utils
// Izmaina vērtības saglabātas šīs klases laukos un tad nepieciešamos laukus ar applyTo funkciju nodod izvēlētajai kamerai

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

//  CONSTANTS
private const val CAMERA_ZOOM_SPEED = 0.5f
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

//  DEBUG CAMERA CONTROLS AND SETTINGS
private const val MOVE_CAMERA_LEFT_KEY = Input.Keys.A
private const val MOVE_CAMERA_RIGHT_KEY = Input.Keys.D
private const val MOVE_CAMERA_UP_KEY = Input.Keys.W
private const val MOVE_CAMERA_DOWN_KEY = Input.Keys.S
private const val ZOOM_CAMERA_IN_KEY = Input.Keys.Q
private const val ZOOM_CAMERA_OUT_KEY = Input.Keys.E
private const val CAMERA_POS_MAIN_SCREEN = Input.Keys.NUMPAD_1
private const val CAMERA_POS_SECOND_SCREEN = Input.Keys.NUMPAD_2
private const val CAMERA_POS_THIRD_SCREEN = Input.Keys.NUMPAD_3


class CameraController : InputAdapter(), GestureDetector.GestureListener {

    companion object {
        private val log = logger(CameraController::class.java)
    }


//  PUBLIC FUNCTIONS

    fun updateCameraPosition(camera: OrthographicCamera) {
        camera.position.set(position, 0f)
//        camera ir zoom lauks un tam tiek iestatīta vērtība no DebugCameraController zoom lauka
        camera.zoom = zoom
        camera.update()
    }

    override fun keyDown(keycode: Int): Boolean {
        val delta = Gdx.graphics.deltaTime

        when (keycode) {
            MOVE_CAMERA_LEFT_KEY -> moveCameraLeft(delta)
            MOVE_CAMERA_RIGHT_KEY -> moveCameraRight(delta)
            MOVE_CAMERA_UP_KEY -> moveCameraUp(delta)
            MOVE_CAMERA_DOWN_KEY -> moveCameraDown(delta)
            ZOOM_CAMERA_IN_KEY -> zoomCameraIn(delta)
            ZOOM_CAMERA_OUT_KEY -> zoomCameraOut(delta)

        }
        log.debug("mans debug CameraController $keycode ${Input.Keys.toString(keycode)}")
        return false
    }


    // GESTURE LISTENER FUNCTIONS


    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        log.debug("mans debug touchDown x - $x 7 $y pointer - $pointer button $button")
        return true
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        log.debug("mans debug tap x - $x 7 $y pointer - $count button $button")
        return true
    }

    override fun longPress(x: Float, y: Float): Boolean {
        log.debug("mans debug longPress x - $x 7 $y")

        return true
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        log.debug("fling velocityX $velocityX, velocityY $velocityY , button $button")
        return true
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        log.debug("pan x $x y $y deltaX $deltaX deltaY $deltaY")
        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        log.debug("$this")
        return false
    }

    override fun pinch(
        initialPointer1: Vector2?,
        initialPointer2: Vector2?,
        pointer1: Vector2?,
        pointer2: Vector2?
    ): Boolean {
        return false
    }

    override fun pinchStop() {
    }

//    PRIVATE FUNCTIONS

    fun setStartPosition(x: Float, y: Float) {
        startPosition.set(x, y)
        position.set(x, y)
    }

    fun setPosition(x: Float, y: Float) {
        position.set(x, y)
    }

    fun moveCameraLeft(delta: Float) {
        setPosition(position.x - (CAMERA_MOVE_SPEED * delta), position.y)
    }

    fun moveCameraRight(delta: Float) {
        setPosition(position.x + (CAMERA_MOVE_SPEED * delta), position.y)
    }

    fun moveCameraUp(delta: Float) {
        setPosition(position.x, position.y + (CAMERA_MOVE_SPEED * delta))
    }

    fun moveCameraDown(delta: Float) {
        setPosition(position.x, position.y - (CAMERA_MOVE_SPEED * delta))
    }

    fun zoomCameraIn(delta: Float) {
        zoom -= CAMERA_ZOOM_SPEED * delta
    }

    fun zoomCameraOut(delta: Float) {
        zoom += CAMERA_ZOOM_SPEED * delta
    }
}
