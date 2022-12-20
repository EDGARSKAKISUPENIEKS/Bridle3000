package utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import screen.AppScreen.Companion.controller

private var oldColor = Color.BLACK

private var currentWorldWidth = 0f
private var currentWorldHeight = 0f
private var totalWorldWidth: Float = 0f
private var totalWorldHeight: Float = 0f
private var currentXLine: Float = 0f
private var currentYLine: Float = 0f


fun Viewport.drawDebugGrid(renderer: ShapeRenderer, cellSize: Int = 1) {

//    copy old renderer color
    oldColor = renderer.color.cpy()

    currentWorldWidth = controller.worldWidth
    currentWorldHeight = controller.worldHeight
    totalWorldWidth = currentWorldWidth * 3 // trīs lapas horizontāli
    totalWorldHeight = currentWorldHeight * 2 // divas lapas vertikāli


//    viwport apply gadījumā, ja tiks izmantoti vairāki viewport
    apply()

    renderer.begin(ShapeRenderer.ShapeType.Line)
    renderer.color = Color.WHITE

//    draw Y axis lines
    currentXLine = 0f
    while (currentXLine <= totalWorldWidth) {
        when {

            currentXLine <= currentWorldWidth -> renderer.line(
                currentXLine,
                0f,
                currentXLine,
                totalWorldHeight
            )

            currentXLine >= currentWorldWidth -> renderer.line(
                currentXLine,
                0f,
                currentXLine,
                currentWorldHeight
            )
        }
        currentXLine += cellSize
    }

//    draw X axis lines
    currentYLine = 0f
    while (currentYLine <= totalWorldHeight) {
        when {

            currentYLine <= currentWorldHeight -> renderer.line(
                0f,
                currentYLine,
                totalWorldWidth,
                currentYLine
            )

            currentYLine >= currentWorldHeight -> renderer.line(
                0f,
                currentYLine,
                currentWorldWidth,
                currentYLine
            )
        }
        currentYLine += cellSize
    }

//    x axis
    renderer.color = Color.RED
    renderer.line(0f, 0f, totalWorldWidth, 0f)
//    y axis
    renderer.color = Color.GREEN
    renderer.line(0f, 0f, 0f, totalWorldHeight)

//    draw contours of pages
//    renderer.line(0f, worldHeight, worldWidth, worldHeight)
//    renderer.line(worldWidth, worldHeight, worldWidth, 0f)
    renderer.color = oldColor


    renderer.end()
}