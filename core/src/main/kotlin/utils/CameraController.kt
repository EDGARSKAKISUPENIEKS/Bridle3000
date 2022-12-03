package utils
// Izmaina vērtības saglabātas šīs klases laukos un tad nepieciešamos laukus ar applyTo funkciju nodod izvēlētajai kamerai

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import config.AppConfig
import screen.AppRenderer
import utils.CameraController.Companion.pages
import kotlin.math.abs

//  CONSTANTS
private const val CAMERA_ZOOM_SPEED = 0.5f
private const val CAMERA_MOVE_SPEED = 5f
private const val CAMERA_MAX_ZOOM_OUT = 5f
private const val CAMERA_MAX_ZOOM_IN = 0.25f

//  PRIVATE PROPERTIES

private val position = Vector2()
private val startPosition = Vector2()

private var delta = Gdx.graphics.deltaTime
private var panPositionStart = Vector3()
private var panPositionEnd = Vector3()
private var zoom = 1f
    set(value) {
        field = MathUtils.clamp(value, CAMERA_MAX_ZOOM_IN, CAMERA_MAX_ZOOM_OUT)
    }
private var dummyCamera = OrthographicCamera()
    get() {
        field = AppRenderer.camera
        return field
    }


//  DEBUG CAMERA CONTROLS AND SETTINGS
private const val MOVE_CAMERA_LEFT_KEY: Int = Input.Keys.A
private const val MOVE_CAMERA_RIGHT_KEY: Int = Input.Keys.D
private const val MOVE_CAMERA_UP_KEY: Int = Input.Keys.W
private const val MOVE_CAMERA_DOWN_KEY: Int = Input.Keys.S
private const val ZOOM_CAMERA_IN_KEY: Int = Input.Keys.Q
private const val ZOOM_CAMERA_OUT_KEY: Int = Input.Keys.E
private const val DEBUG_MODE_KEY: Int = Input.Keys.M
private const val CAMERA_POS_MAIN_SCREEN: Int = Input.Keys.NUMPAD_1
private const val CAMERA_POS_SECOND_SCREEN: Int = Input.Keys.NUMPAD_2
private const val CAMERA_POS_THIRD_SCREEN: Int = Input.Keys.NUMPAD_3


class CameraController : InputAdapter(), GestureDetector.GestureListener {

    companion object {
        private val log = logger(CameraController::class.java)
        var pages: MutableMap<Int, Vector2> = mutableMapOf()
    }

    //  PUBLIC FUNCTIONS

    fun setCameraToStartPosition() {
        startPosition.set(pages[AppRenderer.mainPage.id])
        position.set(startPosition)
    }

    fun updateCameraPosition(camera: OrthographicCamera) {
        camera.position.set(position, 0f)
//        camera ir zoom lauks un tam tiek iestatīta vērtība no DebugCameraController zoom lauka
        camera.zoom = zoom
        camera.update()
    }

    override fun keyDown(keycode: Int): Boolean {
        delta = Gdx.graphics.deltaTime
//          GENERAL CONTROLS
        when (keycode) {
            DEBUG_MODE_KEY -> AppConfig.DEBUG_MODE = !AppConfig.DEBUG_MODE
        }


//        DEBUG CONTROLS/
        if (AppConfig.DEBUG_MODE) {
            when (keycode) {
                MOVE_CAMERA_LEFT_KEY -> moveCameraLeft(delta)
                MOVE_CAMERA_RIGHT_KEY -> moveCameraRight(delta)
                MOVE_CAMERA_UP_KEY -> moveCameraUp(delta)
                MOVE_CAMERA_DOWN_KEY -> moveCameraDown(delta)
                ZOOM_CAMERA_IN_KEY -> zoomCameraIn(delta)
                ZOOM_CAMERA_OUT_KEY -> zoomCameraOut(delta)
            }
            log.debug("mans debug CameraController $keycode ${Input.Keys.toString(keycode)}")
        }
        return false
    }


    // GESTURE LISTENER FUNCTIONS


    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
//        log.debug("mans debug touchDown x - $x y $y pointer - $pointer button $button")
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
//        touchPosition.set(x, y, 0f)
//        dummyCamera.unproject(touchPosition)
//        log.debug("mans debug tap $touchPosition")
        return true
    }

    override fun longPress(x: Float, y: Float): Boolean {
        AppConfig.DEBUG_MODE = !AppConfig.DEBUG_MODE

        if (AppConfig.DEBUG_MODE) {
            log.debug("mans debug longPress x - $x 7 $y")
        }
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        log.debug("mans debug fling velocityX $velocityX, velocityY $velocityY , button $button")
//          sistēmas appišana, neder
        when {
            movePageUpConditions(velocityX, velocityY) -> movePageUp()
            movePageDownConditions(velocityX, velocityY) -> movePageDown()
        }

        return false
    }


    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {

        if (AppConfig.DEBUG_MODE) {
//        paņem skāriena sākuma koordinātes un nobīdes lielumus
            panPositionStart.set(x, y, 0f)
            panPositionEnd.set(x + deltaX, y + deltaY, 0f)

//        projecē tos no pikseļiem uz pasaules vienībām
            AppRenderer.camera.unproject(panPositionStart)
            AppRenderer.camera.unproject(panPositionEnd)

//        paņem esošo kameras pozīciju un iestata to pagaidu kamerai
            position.set(AppRenderer.camera.position.x, AppRenderer.camera.position.y)
            dummyCamera.position.set(position, 0f)
//        aprēķina jauno pozīciju
            dummyCamera.translate(
                panPositionStart.x - panPositionEnd.x,
                panPositionStart.y - panPositionEnd.y
            )
//          iestata jauno pozīciju
            position.set(dummyCamera.position.x, dummyCamera.position.y)
        }

        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        if (AppConfig.DEBUG_MODE) {
//        teorētiski būtu labāk izmantot to pašu pieeju, kas pan metodē un izveidot tam funkciju
            delta = Gdx.graphics.deltaTime
            when {
                initialDistance > distance -> zoomCameraOut(
                    delta,
                    abs((initialDistance - distance) / 1000)
                )
                initialDistance < distance -> zoomCameraIn(
                    delta,
                    abs((initialDistance - distance) / 1000)
                )
            }
        }

        return false
    }


    override fun pinch(
        initialPointer1: Vector2,
        initialPointer2: Vector2,
        pointer1: Vector2,
        pointer2: Vector2
    ): Boolean {
// lai paliek parastais zoom, tas ir priekš debug šā vai tā
        return false
    }

    override fun pinchStop() {
    }

    //    PRIVATE FUNCTIONS

    private fun movePageUpConditions(velocityX: Float, velocityY: Float): Boolean {
        return (
                abs(velocityX) < abs(velocityY)
                        && velocityY > 0
                )
    }

    private fun movePageUp() {
        position.y = AppConfig.DEFAULT_WORLD_HEIGHT + (AppConfig.DEFAULT_WORLD_HEIGHT / 2)
        if (AppConfig.DEBUG_MODE) log.debug("mans debug moved up y=${position.y} ${AppRenderer.camera.position.y} x=${AppRenderer.camera.position.x}")
    }

    private fun movePageDownConditions(velocityX: Float, velocityY: Float): Boolean {
        return (
                abs(velocityX) < abs(velocityY)
                        && velocityY < 0
                )
    }

    private fun movePageDown() {
        position.y = startPosition.y
        if (AppConfig.DEBUG_MODE) log.debug("mans debug moved down y=${position.y} ${AppRenderer.camera.position.y} x=${AppRenderer.camera.position.x}")
    }

    private fun setPosition(x: Float, y: Float) {
        position.set(x, y)
    }

    private fun moveCameraLeft(delta: Float) {
        setPosition(position.x - (CAMERA_MOVE_SPEED * delta), position.y)
    }

    private fun moveCameraRight(delta: Float) {
        setPosition(position.x + (CAMERA_MOVE_SPEED * delta), position.y)
    }

    private fun moveCameraUp(delta: Float) {
        setPosition(position.x, position.y + (CAMERA_MOVE_SPEED * delta))
    }

    private fun moveCameraDown(delta: Float) {
        setPosition(position.x, position.y - (CAMERA_MOVE_SPEED * delta))
    }

    private fun zoomCameraIn(delta: Float) {
        zoom -= CAMERA_ZOOM_SPEED * delta
    }

    private fun zoomCameraIn(delta: Float, speed: Float) {
        zoom -= speed * delta
    }

    private fun zoomCameraOut(delta: Float) {
        zoom += CAMERA_ZOOM_SPEED * delta
    }

    private fun zoomCameraOut(delta: Float, speed: Float) {
        zoom += speed * delta
    }
}
