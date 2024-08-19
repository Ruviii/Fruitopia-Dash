package com.example.birdgame.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.birdgame.R
import java.util.Random

class Apple(context: Context, private val dWidth: Int, private val dHeight: Int, private val birdX: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.apple)
    var appleX = 0
    var appleY = 0
    private var appleVelocity = 0
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
        appleX = birdX + random.nextInt(dWidth - birdX) // Random X position in front of the bird
        appleY = random.nextInt(dHeight) // Random Y position
        appleVelocity = 10 + random.nextInt(10) // Velocity for horizontal movement
    }

    fun updatePosition() {
        appleX -= appleVelocity // Move the apple horizontally
        if (appleX + getWidth() < 0) { // If the apple is out of the screen
            resetPosition(birdX) // Reset the apple's position
        }
    }
}