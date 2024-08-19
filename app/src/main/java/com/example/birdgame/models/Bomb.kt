package com.example.birdgame.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import com.example.birdgame.GameView
import com.example.birdgame.R
import java.util.Random

class Bomb(context: Context) : View(context) {
    var bombFrame : Int = 0
    var bombX : Int = 0
    var bombY : Int = 0
    var bombVelocity : Int = 0
    val bombs = arrayOfNulls<Bitmap>(4)
    private var random : Random

    init {
        bombs[0] = BitmapFactory.decodeResource(resources, R.drawable.bomb0)
        bombs[1] = BitmapFactory.decodeResource(resources, R.drawable.bomb1)
        bombs[2] = BitmapFactory.decodeResource(resources, R.drawable.bomb2)
        bombs[3] = BitmapFactory.decodeResource(resources, R.drawable.bomb3)
        random = Random()
        resetPosition()
    }

    fun resetPosition() {
        bombFrame = random.nextInt(4)
        bombX = GameView.dWidth
        bombY = random.nextInt(GameView.dHeight)
        bombVelocity = 10 + random.nextInt(10)
    }
}