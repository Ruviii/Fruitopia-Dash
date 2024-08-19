package com.example.birdgame.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.birdgame.R
import java.util.Random

class Banana(context: Context, private val dWidth: Int, private val dHeight: Int,private val birdX: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.banana)
    var bananaX = 0
    var bananaY = 0
    private var bananaVelocity = 0
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
        bananaX = birdX + random.nextInt(dWidth - birdX) // Random X position in front of the bird
        bananaY = random.nextInt(dHeight) // Random Y position
        bananaVelocity = 10 + random.nextInt(10) // Velocity for horizontal movement
    }

    fun updatePosition() {
        bananaX -= bananaVelocity // Move the banana horizontally
        if (bananaX + getWidth() < 0) { // If the banana is out of the screen
            resetPosition(birdX) // Reset the banana's position
        }
    }
}