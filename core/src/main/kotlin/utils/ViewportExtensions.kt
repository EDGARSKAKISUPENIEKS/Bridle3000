package utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport

var oldColor = Color.BLACK
var currentWorldWidth: Float = 0f
var currentWorldHeight: Float = 0f
var currentXLine: Float = 0f
var currentYLine: Float = 0f


fun Viewport.drawDebugGrid(renderer: ShapeRenderer, cellSize: Int = 1) {

//    copy old renderer color
    oldColor = renderer.color.cpy()


    //    get world height and with from Viewport class
    currentWorldWidth = worldWidth * 2
    currentWorldHeight = worldHeight * 2

//    viwport apply gadījumā, ja tiks izmantoti vairāki viewport
    apply()

    renderer.begin(ShapeRenderer.ShapeType.Line)
    renderer.color = Color.WHITE

//    draw Y axis lines
    currentXLine = -currentWorldWidth
    while (currentXLine <= currentWorldWidth) {
        renderer.line(currentXLine, -currentWorldHeight, currentXLine, currentWorldHeight)
        currentXLine += cellSize
    }

//    draw X axis lines
    currentYLine = -currentWorldHeight
    while (currentYLine <= currentWorldHeight) {
        renderer.line(-currentWorldHeight, currentYLine, currentWorldHeight, currentYLine)
        currentYLine += cellSize
    }

//    x axis
    renderer.color = Color.RED
    renderer.line(-currentWorldHeight, 0f, currentWorldWidth, 0f)
//    y axis
    renderer.color = Color.GREEN
    renderer.line(0f, -currentWorldWidth, 0f, currentWorldWidth)

//    draw world bounds
    renderer.color = Color.YELLOW
    renderer.line(0f, worldHeight, worldWidth, worldHeight)
    renderer.line(worldWidth, worldHeight, worldWidth, 0f)


    renderer.end()
}