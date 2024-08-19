package com.example.birdgame.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.birdgame.R
import java.util.Random

class Strawberry(context: Context, private val dWidth: Int, private val dHeight: Int, private val birdX: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.strawberry)
    var strawberryX = 0
    var strawberryY = 0
    private var strawberryVelocity = 0
    private val random: Random = Random()

    init {
        resetPosition(birdX)
    }

    fun getWidth(): Int {
        return bitmap.width
    }

    fun getHeight(): Int {
        return bitmap.height
    }

    fun resetPosition(birdX: Int) {
        strawberryX = birdX + random.nextInt(dWidth - birdX) // Random X position in front of the bird
        strawberryY = random.nextInt(dHeight) // Random Y position
        strawberryVelocity = 10 + random.nextInt(10) // Velocity for horizontal movement
    }

    fun updatePosition() {
        strawberryX -= strawberryVelocity // Move the strawberry horizontally
        if (strawberryX + getWidth() < 0) { // If the strawberry is out of the screen
            resetPosition(birdX) // Reset the strawberry's position
        }
    }
}