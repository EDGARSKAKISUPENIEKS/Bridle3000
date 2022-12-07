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
import screen.AppController.activePage
import screen.AppScreen.Companion.controller
import screen.AppRenderer
import screen.AppRenderer.Companion
import kotlin.math.abs

//  CONSTANTS
private const val CAMERA_ZOOM_SPEED = 0.5f
private const val CAMERA_MAX_ZOOM_OUT = 5f
private const val CAMERA_MAX_ZOOM_IN = 0.25f
private val cameraMoveSpeed = if (!AppConfig.DEBUG_MODE) 15f else 5f

//  PRIVATE PROPERTIES

private val position = Vector2()
private var touchPosition = Vector3()
private var targetPosition = Vector2()
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

private val innerUp: Float
    get() = (controller.worldHeight / 3) * 2
private val innerRight: Float
    get() = (controller.worldWidth / 3) * 2
private val innerDown: Float
    get() = controller.worldHeight / 3
private val innerLeft: Float
    get() = controller.worldWidth / 3


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
private const val CAMERA_POS_FOURTH_SCREEN: Int = Input.Keys.NUMPAD_4

private const val WORLD_HEIGHT_PLUS: Int = Input.Keys.UP
private const val WORLD_HEIGHT_MINUS: Int = Input.Keys.DOWN
private const val WORLD_WIDTH_PLUS: Int = Input.Keys.RIGHT
private const val WORLD_WIDTH_MINUS: Int = Input.Keys.LEFT


class CameraController : InputAdapter(), GestureDetector.GestureListener {

    companion object {
        private val log = logger(CameraController::class.java)
        var pages: MutableMap<Int, Vector2> = mutableMapOf()
    }

    //  PUBLIC FUNCTIONS

    fun setCameraToStartPosition() {
        targetPosition.set(AppRenderer.mainPage.position)
        position.set(targetPosition)
        if (AppConfig.DEBUG_MODE) log.debug("mans debug start $position x=$targetPosition ${controller.activePage}")
    }

    fun updateCameraPosition(camera: OrthographicCamera) {
        when {
            position.y > targetPosition.y -> {
                if (position.y - targetPosition.y < 0.1f) {
                    position.y = targetPosition.y
                } else if (position.y - targetPosition.y > 0.1f) {
                    moveCameraDown(getDelta())
                }
            }
        }
        when {
            position.y < targetPosition.y -> {
                if (targetPosition.y - position.y < 0.1f) {
                    position.y = targetPosition.y
                } else if (targetPosition.y - position.y > 0.1f) {
                    moveCameraUp(getDelta())
                }
            }
        }
        when {
            position.x > targetPosition.x -> {
                if (position.x - targetPosition.x < 0.1f) {
                    position.x = targetPosition.x
                } else if (position.x - targetPosition.x > 0.1f) {
                    moveCameraLeft(getDelta())
                }
            }
        }
        when {
            position.x < targetPosition.x -> {
                if (targetPosition.x - position.x < 0.1f) {
                    position.x = targetPosition.x
                } else if (targetPosition.x - position.x > 0.1f) {
                    moveCameraRight(getDelta())
                }
            }
        }

        camera.position.set(position, 0f)
//        camera ir zoom lauks un tam tiek iestatīta vērtība no DebugCameraController zoom lauka
        camera.zoom = zoom
        camera.update()
    }

    override fun keyDown(keycode: Int): Boolean {
//          GENERAL CONTROLS
        when (keycode) {
            DEBUG_MODE_KEY -> AppConfig.DEBUG_MODE = !AppConfig.DEBUG_MODE
        }


//        DEBUG KEYBOARD CONTROLS
        if (AppConfig.DEBUG_MODE) {
            when (keycode) {
                MOVE_CAMERA_LEFT_KEY -> moveCameraLeft(getDelta())
                MOVE_CAMERA_RIGHT_KEY -> moveCameraRight(getDelta())
                MOVE_CAMERA_UP_KEY -> moveCameraUp(getDelta())
                MOVE_CAMERA_DOWN_KEY -> moveCameraDown(getDelta())
                ZOOM_CAMERA_IN_KEY -> zoomCameraIn(getDelta())
                ZOOM_CAMERA_OUT_KEY -> zoomCameraOut(getDelta())
                CAMERA_POS_MAIN_SCREEN -> targetPosition.set(AppRenderer.mainPage.position)
                CAMERA_POS_SECOND_SCREEN -> targetPosition.set(AppRenderer.secondPage.position)

                WORLD_HEIGHT_PLUS -> controller.incrementWorldHeight("up")
                WORLD_HEIGHT_MINUS -> controller.incrementWorldHeight("down")
                WORLD_WIDTH_PLUS -> controller.incrementWorldWidth("up")
                WORLD_WIDTH_MINUS -> controller.incrementWorldWidth("down")
            }
            log.debug("mans debug CameraController $keycode ${Input.Keys.toString(keycode)}")
            log.debug("mans debug CameraController ${controller.worldHeight}")
        }
        return false
    }


    // GESTURE LISTENER FUNCTIONS


    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
//        log.debug("mans debug touchDown x - $x y $y pointer - $pointer button $button")
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        touchPosition.set(x, y, 0f)
        AppRenderer.camera.unproject(touchPosition)

        when {
            touchPosition.x in innerRight..controller.worldWidth -> controller.incrementWorldWidth("up")
            touchPosition.x in 0f..innerLeft -> controller.incrementWorldWidth("down")
            touchPosition.y in innerUp..controller.worldHeight -> controller.incrementWorldHeight("up")
            touchPosition.y in 0f..innerDown -> controller.incrementWorldHeight("down")
        }

        log.debug("mans debug tap $touchPosition")
        log.debug("mans debug ${controller.worldWidth} ${controller.worldHeight}")
        return false
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
            movePageRightConditions(velocityX, velocityY) -> movePageRight()
            movePageLeftConditions(velocityX, velocityY) -> movePageLeft()
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
            when {
                initialDistance > distance -> zoomCameraOut(
                    getDelta(),
                    abs((initialDistance - distance) / 1000)
                )
                initialDistance < distance -> zoomCameraIn(
                    getDelta(),
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
        return (abs(velocityX) < abs(velocityY) && velocityY > 0)
    }

    private fun movePageUp() {
        if (AppConfig.DEBUG_MODE) log.debug("mans debug before moved up  $position x=$targetPosition ${controller.activePage}")
        targetPosition = AppRenderer.fourthPage.position
        controller.activePage = 4
        //TODO(padomāt vai nevajag te lietot activate() no klases funkcijām)
        if (AppConfig.DEBUG_MODE) log.debug("mans debug moved up  $position x=$targetPosition ${controller.activePage}")
    }

    private fun movePageDownConditions(velocityX: Float, velocityY: Float): Boolean {
        return (abs(velocityX) < abs(velocityY) && velocityY < 0)
    }

    private fun movePageDown() {
        if (AppConfig.DEBUG_MODE) log.debug("mans debug before moved down $position x=$targetPosition ${controller.activePage}")
        targetPosition = AppRenderer.mainPage.position
        controller.activePage = 1
        if (AppConfig.DEBUG_MODE) log.debug("mans debug moved down $position x=$targetPosition ${controller.activePage}")
    }

    private fun movePageRightConditions(velocityX: Float, velocityY: Float): Boolean {
        return (abs(velocityX) > abs(velocityY) && velocityX < 0 && controller.activePage in 1..3)
    }

    private fun movePageRight() {
        if (AppConfig.DEBUG_MODE) log.debug("mans debug before moved right $position x=$targetPosition ${controller.activePage}")
        if (controller.activePage == AppRenderer.thirdPage.id) {
            controller.activePage = AppRenderer.mainPage.id
        } else {
            controller.activePage++
        }
        targetPosition = pages[controller.activePage]!!
        if (AppConfig.DEBUG_MODE) log.debug("mans debug moved right $position x=$targetPosition ${controller.activePage}")
    }

    private fun movePageLeftConditions(velocityX: Float, velocityY: Float): Boolean {
        return (abs(velocityX) > abs(velocityY) && velocityX > 0 && controller.activePage in 1..3)
    }

    private fun movePageLeft() {
        if (AppConfig.DEBUG_MODE) log.debug("mans debug before moved left $position x=$targetPosition ${controller.activePage}")
        if (controller.activePage == AppRenderer.mainPage.id) {
            controller.activePage = AppRenderer.thirdPage.id
        } else {
            controller.activePage--
        }
        targetPosition = pages[controller.activePage]!!
        if (AppConfig.DEBUG_MODE) log.debug("mans debug moved left $position x=$targetPosition ${controller.activePage}")
    }

    private fun setPosition(x: Float, y: Float) {
        position.set(x, y)
    }

    private fun moveCameraLeft(delta: Float) {
        setPosition(position.x - (cameraMoveSpeed * delta), position.y)
    }

    private fun moveCameraRight(delta: Float) {
        setPosition(position.x + (cameraMoveSpeed * delta), position.y)
    }

    private fun moveCameraUp(delta: Float) {
        setPosition(position.x, position.y + (cameraMoveSpeed * delta))
    }

    private fun moveCameraDown(delta: Float) {
        setPosition(position.x, position.y - (cameraMoveSpeed * delta))
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

    private fun getDelta(): Float {
        delta = Gdx.graphics.deltaTime
        return delta
    }
}
