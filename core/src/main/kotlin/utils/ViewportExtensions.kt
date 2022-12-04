package utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import screen.AppScreen

private var oldColor = Color.BLACK

private var currentWorldWidth = 0f
private var currentWorldHeight = 0f
private var totalWorldWidth: Float = 0f
private var totalWorldHeight: Float = 0f
private var currentXLine: Float = 0f
private var currentYLine: Float = 0f
private var pageBoundAdjustment = 0.04f


fun Viewport.drawDebugGrid(renderer: ShapeRenderer, cellSize: Int = 1) {

//    copy old renderer color
    oldColor = renderer.color.cpy()

    currentWorldWidth = AppScreen.controller.worldWidth
    currentWorldHeight = AppScreen.controller.worldHeight
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
    renderer.color = Color.YELLOW
//    renderer.line(0f, worldHeight, worldWidth, worldHeight)
//    renderer.line(worldWidth, worldHeight, worldWidth, 0f)

//    PIRMĀ LAPA
//    augšmala
    renderer.line(
        0f + pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment,
        currentWorldWidth - pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment
    )
//    labā mala
    renderer.line(
        currentWorldWidth - pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment,
        currentWorldWidth - pageBoundAdjustment,
        0f + pageBoundAdjustment
    )
//    apakšmala
    renderer.line(
        currentWorldWidth - pageBoundAdjustment,
        0f + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        0f + pageBoundAdjustment
    )
//    kreisā mala
    renderer.line(
        0f + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment
    )


//    OTRĀ LAPA
//    augšmala
    renderer.line(
        currentWorldWidth + pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment,
        currentWorldWidth * 2 - pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment
    )

//    labā lapa
    renderer.line(
        currentWorldWidth * 2 - pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment,
        currentWorldWidth * 2 - pageBoundAdjustment,
        0f + pageBoundAdjustment
    )
//    apakšmala
    renderer.line(
        currentWorldWidth * 2 - pageBoundAdjustment,
        0f + pageBoundAdjustment,
        currentWorldWidth + pageBoundAdjustment,
        0f + pageBoundAdjustment
    )
//    kreisā mala
    renderer.line(
        currentWorldWidth + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        currentWorldWidth + pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment
    )

//    TREŠĀ LAPA
//    augšmala
    renderer.line(
        currentWorldWidth * 2 + pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment,
        currentWorldWidth * 3 - pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment
    )

//    labā mala
    renderer.line(
        currentWorldWidth * 3 - pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment,
        currentWorldWidth * 3 - pageBoundAdjustment,
        0f + pageBoundAdjustment
    )

//    apakšmala
    renderer.line(
        currentWorldWidth * 3 - pageBoundAdjustment,
        0f + pageBoundAdjustment,
        currentWorldWidth * 2 + pageBoundAdjustment,
        0f + pageBoundAdjustment
    )

//    kreisā mala
    renderer.line(
        currentWorldWidth * 2 + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        currentWorldWidth * 2 + pageBoundAdjustment,
        currentWorldHeight - pageBoundAdjustment
    )

//    CETURTĀ LAPA
// otrā stāva augša četras lapas horizontāli, bet otrajā stāvā tikai viena lapa (ceturtās lapas augša)
//    augšmala
    renderer.line(
        0f + pageBoundAdjustment,
        currentWorldHeight * 2 - pageBoundAdjustment,
        currentWorldWidth - pageBoundAdjustment,
        currentWorldHeight * 2 - pageBoundAdjustment
    )

//    labā mala
    renderer.line(
        currentWorldWidth - pageBoundAdjustment,
        currentWorldHeight * 2 - pageBoundAdjustment,
        currentWorldWidth - pageBoundAdjustment,
        currentWorldHeight + pageBoundAdjustment
    )

//    apakšmala
    renderer.line(
        currentWorldWidth - pageBoundAdjustment,
        currentWorldHeight + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        currentWorldHeight + pageBoundAdjustment
    )

//    kreisā mala
    renderer.line(
        0f + pageBoundAdjustment,
        currentWorldHeight + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        currentWorldHeight * 2 - pageBoundAdjustment
    )

    renderer.end()
}