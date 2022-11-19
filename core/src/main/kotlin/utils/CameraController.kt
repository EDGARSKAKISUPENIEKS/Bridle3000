package utils


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import utils.logger

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

//  DEBUG CAMERA CONTROLS AND SETTINGS
const val MOVE_CAMERA_LEFT_KEY = Input.Keys.LEFT
private const val MOVE_CAMERA_RIGHT_KEY = Input.Keys.RIGHT
private const val MOVE_CAMERA_UP_KEY = Input.Keys.UP
private const val MOVE_CAMERA_DOWN_KEY = Input.Keys.DOWN
private const val ZOOM_CAMERA_IN_KEY = Input.Keys.NUMPAD_ADD
private const val ZOOM_CAMERA_OUT_KEY = Input.Keys.NUMPAD_SUBTRACT


//  extension function (method)
//  paplašina (extends) Int klasi ar sekojošo metodi
fun Int.isKeyPressed() = Gdx.input.isKeyPressed(this)
fun Int.isKeyJustPressed() = Gdx.input.isKeyJustPressed(this)

class CameraController : InputAdapter() {

    companion object {
        private val log = logger(CameraController::class.java)
    }

//  PUBLIC FUNCTIONS


    //  KEY DOWN FUNCTIONS
    override fun keyDown(keycode: Int): Boolean {
        val delta = Gdx.graphics.deltaTime

        when (keycode) {
            MOVE_CAMERA_LEFT_KEY -> moveCameraLeft(delta)
        }
        log.debug("CameraController $keycode ${Input.Keys.toString(keycode)}")
        return false
    }

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


    //    KEY UP FUNCTIONS
    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    //      KEY TYPED FUNCTIONS
    override fun keyTyped(character: Char): Boolean {
        return false
    }

    //      TOUCH DOWN FUNCTIONS
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    // TOUCH UP FUNCTIONS
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    // TOUCH DRAGGED FUNCTIONS
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    //  MOUSE MOVED FUNCTIONS
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    //  SCROLLED FUNCTIONS
    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}