package screen

import com.badlogic.gdx.InputProcessor


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import utils.moveCameraLeft

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

class DebugCameraInputProcessor : InputAdapter() {


    override fun keyDown(keycode: Int): Boolean {
//        val delta = Gdx.graphics.deltaTime
//
//        when (keycode) {
//            MOVE_CAMERA_LEFT_KEY -> moveCameraLeft(delta)
//        }

        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}