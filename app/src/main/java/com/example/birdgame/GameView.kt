package com.example.birdgame

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import com.example.birdgame.models.Apple
import com.example.birdgame.models.Banana
import com.example.birdgame.models.Bomb
import com.example.birdgame.models.Explosion
import com.example.birdgame.models.Mango
import com.example.birdgame.models.Strawberry
import java.util.Random


class GameView(context: Context) : View(context) {
    private var isBirdHit = false
    private var bird3: Bitmap? = null
    private var frameCount = 0
    private val FRAME_RATE = 5
    private var birdFrame : Int = 0
    private val birds = arrayOfNulls<Bitmap>(2)
    private var background: Bitmap? = null
    private var bird : Bitmap ? = null
    private var rectBackground: Rect? = null
    private var gameHandler : Handler
    private var isGameOver = false
    private val UPDATE_MILLIS : Long = 30
    private var runnable : Runnable
    private var textPaint : Paint = Paint()
    private var TEXT_SIZE : Float = 120f

    companion object {
        var dWidth : Int = 0
        var dHeight : Int = 0
    }
    private var random : Random
    private var birdX : Float = 0.0f
    private var birdY : Float = 0.0f
    private var oldY : Float = 0.0f
    private var oldBirdY: Float = 0.0f
    private var bombs : ArrayList<Bomb>
    private var explosions : ArrayList<Explosion>
    private var strawberries: ArrayList<Strawberry>
    private var mangoes: ArrayList<Mango>
    private var bananas: ArrayList<Banana>
    private var apples: ArrayList<Apple>
    private var viewModel : GameViewModel

    init {
        val dpValue = 160
        val scale = resources.displayMetrics.density
        val pixelValue = (dpValue * scale + 0.5f).toInt()
        birdX = pixelValue.toFloat()
        birds[0] = BitmapFactory.decodeResource(resources, R.drawable.bird1)
        birds[1] = BitmapFactory.decodeResource(resources, R.drawable.bird2)
        bird3 = BitmapFactory.decodeResource(resources, R.drawable.bird3)
        background = BitmapFactory.decodeResource(resources, R.drawable.background)
        bird = BitmapFactory.decodeResource(resources, R.drawable.bird1)
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        dWidth = displayMetrics.widthPixels
        dHeight = displayMetrics.heightPixels
        rectBackground = Rect(0, 0, dWidth, dHeight)
        gameHandler = Handler(Looper.getMainLooper())
        runnable = Runnable { invalidate() }
        textPaint.setColor(Color.rgb(0, 0, 0))
        textPaint.textSize = TEXT_SIZE
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.digitalt))
        random = Random()
        bombs = ArrayList<Bomb>()
        explosions = ArrayList<Explosion>()
        for(i in 1..3) {
            val bomb = Bomb(context)
            bombs.add(bomb)
        }
        strawberries = ArrayList<Strawberry>()
        mangoes = ArrayList<Mango>()
        bananas = ArrayList<Banana>()
        apples = ArrayList<Apple>()
        viewModel = GameViewModel()
        for(i in 1..3) {
            val apple = Apple(context, dWidth, dHeight, birdX.toInt())
            apples.add(apple)
            val strawberry = Strawberry(context, dWidth, dHeight, birdX.toInt())
            strawberries.add(strawberry)
            val mango = Mango(context, dWidth, dHeight, birdX.toInt())
            mangoes.add(mango)
            val banana = Banana(context, dWidth, dHeight, birdX.toInt())
            bananas.add(banana)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background!!, null, rectBackground!!, null)
        canvas.drawBitmap(bird!!, birdX, birdY, null)

        if (frameCount % FRAME_RATE == 0) {
            bird = birds[birdFrame]
            birdFrame = (birdFrame + 1) % birds.size
        }
        frameCount++

        if (!isBirdHit) {
            if (frameCount % FRAME_RATE == 0) {
                bird = birds[birdFrame]
                birdFrame = (birdFrame + 1) % birds.size
            }
            frameCount++
        }

        for (strawberry in strawberries) {
            canvas.drawBitmap(strawberry.bitmap, strawberry.strawberryX.toFloat(), strawberry.strawberryY.toFloat(), null)
            strawberry.updatePosition()
            if (birdX < strawberry.strawberryX + strawberry.getWidth() && birdX + bird!!.width > strawberry.strawberryX && birdY < strawberry.strawberryY + strawberry.getHeight() && birdY + bird!!.height > strawberry.strawberryY) {
                viewModel.addPoints()
                strawberry.resetPosition(birdX.toInt())
            }
            if (strawberry.strawberryX + strawberry.getWidth() < 0) { // If the strawberry is out of the screen
                strawberry.resetPosition(birdX.toInt()) // Reset the strawberry's position
            }
        }

        for (mango in mangoes) {
            canvas.drawBitmap(mango.bitmap, mango.mangoX.toFloat(), mango.mangoY.toFloat(), null)
            mango.updatePosition()
            if (birdX < mango.mangoX + mango.getWidth() && birdX + bird!!.width > mango.mangoX && birdY < mango.mangoY + mango.getHeight() && birdY + bird!!.height > mango.mangoY) {
                viewModel.addPoints()
                mango.resetPosition(birdX.toInt())
            }
            if (mango.mangoX + mango.getWidth() < 0) { // If the mango is out of the screen
                mango.resetPosition(birdX.toInt()) // Reset the mango's position
            }
        }

        for (banana in bananas) {
            canvas.drawBitmap(banana.bitmap, banana.bananaX.toFloat(), banana.bananaY.toFloat(), null)
            banana.updatePosition()
            if (birdX < banana.bananaX + banana.getWidth() && birdX + bird!!.width > banana.bananaX && birdY < banana.bananaY + banana.getHeight() && birdY + bird!!.height > banana.bananaY) {
                viewModel.addPoints()
                banana.resetPosition(birdX.toInt())
            }
            if (banana.bananaX + banana.getWidth() < 0) { // If the banana is out of the screen
                banana.resetPosition(birdX.toInt()) // Reset the banana's position
            }
        }

        for (apple in apples) {
            canvas.drawBitmap(apple.bitmap, apple.appleX.toFloat(), apple.appleY.toFloat(), null)
            apple.updatePosition()
            if (birdX < apple.appleX + apple.getWidth() && birdX + bird!!.width > apple.appleX && birdY < apple.appleY + apple.getHeight() && birdY + bird!!.height > apple.appleY) {
                viewModel.addPoints()
                apple.resetPosition(birdX.toInt())
            }
            if (apple.appleX + apple.getWidth() < 0) { // If the apple is out of the screen
                apple.resetPosition(birdX.toInt()) // Reset the apple's position
            }
        }

        for(bomb in bombs) {
            canvas.drawBitmap(bomb.bombs[bomb.bombFrame]!!, bomb.bombX.toFloat(), bomb.bombY.toFloat(), null)
            bomb.bombX -= bomb.bombVelocity // Move the bomb horizontally
            if (bomb.bombX + bomb.bombs[bomb.bombFrame]!!.width < 0) { // If the bomb is out of the screen
                bomb.resetPosition() // Reset the bomb's position
            }
        }

        for (bomb in bombs) {
            if (!isGameOver && birdX < bomb.bombX + bomb.width && birdX + bird!!.width > bomb.bombX && birdY < bomb.bombY + bomb.height && birdY + bird!!.height > bomb.bombY) {
                val explosion = Explosion(context)
                explosion.explosionX = ((birdX + bomb.bombX.toFloat()) / 2).toInt() // Calculate midpoint for explosionX
                explosion.explosionY = ((birdY + bomb.bombY.toFloat()) / 2).toInt() // Calculate midpoint for explosionY
                explosions.add(explosion)
                isBirdHit = true
                bird = bird3 // Change bird image to bird3
                gameHandler.removeCallbacks(runnable)
                isGameOver = true
                val intent = Intent(context, GameOver::class.java)
                intent.putExtra("points", viewModel.points.value)
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }

        val iterator = explosions.iterator()

        while (iterator.hasNext()) {
            val explosion = iterator.next()
            val explosionBitmap = explosion.getExplosion(explosion.explosionFrame)
            if (explosionBitmap != null) {
                canvas.drawBitmap(explosionBitmap, explosion.explosionX.toFloat(), explosion.explosionY.toFloat(), null)
            }
            if (frameCount % FRAME_RATE == 0) { // Use frameCount and FRAME_RATE to control the explosion animation speed
                explosion.explosionFrame++
            }
            if(explosion.explosionFrame >= explosion.explosion.size) {
                explosion.explosionFrame = 0
                iterator.remove()
            }
        }
        frameCount++

        canvas.drawText("Points: ${viewModel.points.value}", 50F, 100F, textPaint)
        gameHandler.postDelayed(runnable, UPDATE_MILLIS)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action
        if (action == MotionEvent.ACTION_DOWN) {
            oldY = event.y
            oldBirdY = birdY
        }
        if (action == MotionEvent.ACTION_MOVE) {
            val shiftY = oldY - event.y
            val newBirdY = oldBirdY - shiftY

            birdY = if (newBirdY <= 0) {
                0F
            } else if (newBirdY >= dHeight - (bird?.height ?: 0)) {
                (dHeight - (bird?.height ?: 0)).toFloat()
            } else {
                newBirdY
            }
        }
        return true
    }

}