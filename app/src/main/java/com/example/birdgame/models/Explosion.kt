package com.example.birdgame.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.birdgame.R

class Explosion(context: Context) {
    val explosion = arrayOfNulls<Bitmap>(4)
    var explosionFrame = 0
    var explosionX = 0
    var explosionY = 0

    init {
        explosion[0] = BitmapFactory.decodeResource(context.resources, R.drawable.explode0)
        explosion[1] = BitmapFactory.decodeResource(context.resources, R.drawable.explode1)
        explosion[2] = BitmapFactory.decodeResource(context.resources, R.drawable.explode2)
        explosion[3] = BitmapFactory.decodeResource(context.resources, R.drawable.explode3)
    }

    fun getExplosion(explosionFrame: Int): Bitmap? {
        return explosion[explosionFrame]
    }
}