package screen

import com.badlogic.gdx.InputProcessor



import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

//  DEBUG CAMERA CONTROLS AND SETTINGS
private const val MOVE_CAMERA_LEFT_KEY = Input.Keys.LEFT
private const val MOVE_CAMERA_RIGHT_KEY = Input.Keys.RIGHT
private const val MOVE_CAMERA_UP_KEY = Input.Keys.UP
private const val MOVE_CAMERA_DOWN_KEY = Input.Keys.DOWN
private const val ZOOM_CAMERA_IN_KEY = Input.Keys.NUMPAD_ADD
private const val ZOOM_CAMERA_OUT_KEY = Input.Keys.NUMPAD_SUBTRACT
const val CAMERA_ZOOM_SPEED = 2f
const val CAMERA_MOVE_SPEED = 5f
const val CAMERA_MAX_ZOOM_OUT = 5f
const val CAMERA_MAX_ZOOM_IN = 0.25f


//  extension function (method)
//  paplašina (extends) Int klasi ar sekojošo metodi
fun Int.isKeyPressed() = Gdx.input.isKeyPressed(this)
fun Int.isKeyJustPressed() = Gdx.input.isKeyJustPressed(this)

class AppInputProcessor: InputProcessor {



    override fun keyDown(keycode: Int): Boolean {

        when (keycode){
//            MOVE_CAMERA_LEFT_KEY ->
        }

        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }
    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return true
    }
}