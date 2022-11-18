package utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport

fun Viewport.drawDebugGrid(renderer: ShapeRenderer, cellSize: Int = 1) {

//    copy old renderer color
    val oldColor = renderer.color.cpy()

//    get world height and with from Viewport class
    val doubleWorldWidth = worldWidth * 2
    val doubleWorldHeight = worldHeight * 2

//    viwport apply gadījumā, ja tiks izmantoti vairāki viewport
//    apply()

    renderer.begin(ShapeRenderer.ShapeType.Line)
    renderer.color = Color.WHITE

//    draw Y axis lines
    var x: Float = -doubleWorldWidth
    while (x <= doubleWorldWidth) {
        renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight)
        x += cellSize
    }

//    draw X axis lines
    var y: Float = -doubleWorldHeight
    while (y <= doubleWorldHeight) {
        renderer.line(-doubleWorldHeight, y, doubleWorldHeight, y)
        y += cellSize
    }

//    x axis
    renderer.color = Color.RED
    renderer.line(-doubleWorldHeight, 0f, doubleWorldWidth, 0f)
//    y axis
    renderer.color = Color.GREEN
    renderer.line(0f, -doubleWorldWidth, 0f, doubleWorldWidth)

//    draw world bounds
    renderer.color = Color.YELLOW
    renderer.line(0f, worldHeight, worldWidth, worldHeight)
    renderer.line(worldWidth, worldHeight, worldWidth, 0f)


    renderer.end()
}