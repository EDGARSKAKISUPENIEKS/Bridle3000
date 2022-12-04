package utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport

private var oldColor = Color.BLACK
private var totalWorldWidth: Float = 0f
private var totalWorldHeight: Float = 0f
private var currentXLine: Float = 0f
private var currentYLine: Float = 0f
private var pageBoundAdjustment = 0.04f


fun Viewport.drawDebugGrid(renderer: ShapeRenderer, cellSize: Int = 1) {

//    copy old renderer color
    oldColor = renderer.color.cpy()


    //    get world height and with from Viewport class
    totalWorldWidth = worldWidth * 3 // trīs lapas horizontāli
    totalWorldHeight = worldHeight * 2 // divas lapas vertikāli

//    viwport apply gadījumā, ja tiks izmantoti vairāki viewport
    apply()

    renderer.begin(ShapeRenderer.ShapeType.Line)
    renderer.color = Color.WHITE

//    draw Y axis lines
    currentXLine = 0f
    while (currentXLine <= totalWorldWidth) {
        when {

            currentXLine <= worldWidth -> renderer.line(
                currentXLine,
                0f,
                currentXLine,
                totalWorldHeight
            )

            currentXLine >= worldWidth -> renderer.line(
                currentXLine,
                0f,
                currentXLine,
                worldHeight
            )
        }
        currentXLine += cellSize
    }

//    draw X axis lines
    currentYLine = 0f
    while (currentYLine <= totalWorldHeight) {
        when {

            currentYLine <= worldHeight -> renderer.line(
                0f,
                currentYLine,
                totalWorldWidth,
                currentYLine
            )

            currentYLine >= worldHeight -> renderer.line(
                0f,
                currentYLine,
                worldWidth,
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
        worldHeight - pageBoundAdjustment,
        worldWidth - pageBoundAdjustment,
        worldHeight - pageBoundAdjustment
    )
//    labā mala
    renderer.line(
        worldWidth - pageBoundAdjustment,
        worldHeight - pageBoundAdjustment,
        worldWidth - pageBoundAdjustment,
        0f + pageBoundAdjustment
    )
//    apakšmala
    renderer.line(
        worldWidth - pageBoundAdjustment,
        0f + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        0f + pageBoundAdjustment
    )
//    kreisā mala
    renderer.line(
        0f + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        worldHeight - pageBoundAdjustment
    )


//    OTRĀ LAPA
//    augšmala
    renderer.line(
        worldWidth + pageBoundAdjustment,
        worldHeight - pageBoundAdjustment,
        worldWidth * 2 - pageBoundAdjustment,
        worldHeight - pageBoundAdjustment
    )

//    labā lapa
    renderer.line(
        worldWidth * 2 - pageBoundAdjustment,
        worldHeight - pageBoundAdjustment,
        worldWidth * 2 - pageBoundAdjustment,
        0f + pageBoundAdjustment
    )
//    apakšmala
    renderer.line(
        worldWidth * 2 - pageBoundAdjustment,
        0f + pageBoundAdjustment,
        worldWidth + pageBoundAdjustment,
        0f + pageBoundAdjustment
    )
//    kreisā mala
    renderer.line(
        worldWidth + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        worldWidth + pageBoundAdjustment,
        worldHeight - pageBoundAdjustment
    )

//    TREŠĀ LAPA
//    augšmala
    renderer.line(
        worldWidth * 2 + pageBoundAdjustment,
        worldHeight - pageBoundAdjustment,
        worldWidth * 3 - pageBoundAdjustment,
        worldHeight - pageBoundAdjustment
    )

//    labā mala
    renderer.line(
        worldWidth * 3 - pageBoundAdjustment,
        worldHeight - pageBoundAdjustment,
        worldWidth * 3 - pageBoundAdjustment,
        0f + pageBoundAdjustment
    )

//    apakšmala
    renderer.line(
        worldWidth * 3 - pageBoundAdjustment,
        0f + pageBoundAdjustment,
        worldWidth * 2 + pageBoundAdjustment,
        0f + pageBoundAdjustment
    )

//    kreisā mala
    renderer.line(
        worldWidth * 2 + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        worldWidth * 2 + pageBoundAdjustment,
        worldHeight - pageBoundAdjustment
    )

//    CETURTĀ LAPA
// otrā stāva augša četras lapas horizontāli, bet otrajā stāvā tikai viena lapa (ceturtās lapas augša)
//    augšmala
    renderer.line(
        0f + pageBoundAdjustment,
        worldHeight * 2 - pageBoundAdjustment,
        worldWidth - pageBoundAdjustment,
        worldHeight * 2 - pageBoundAdjustment
    )

//    labā mala
    renderer.line(
        worldWidth - pageBoundAdjustment,
        worldHeight * 2 - pageBoundAdjustment,
        worldWidth - pageBoundAdjustment,
        worldHeight + pageBoundAdjustment
    )

//    apakšmala
    renderer.line(
        worldWidth - pageBoundAdjustment,
        worldHeight + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        worldHeight + pageBoundAdjustment
    )

//    kreisā mala
    renderer.line(
        0f + pageBoundAdjustment,
        worldHeight + pageBoundAdjustment,
        0f + pageBoundAdjustment,
        worldHeight * 2 - pageBoundAdjustment
    )

    renderer.end()
}