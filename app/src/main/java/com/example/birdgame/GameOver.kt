package com.example.birdgame

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity() {

    private var tvPoints : TextView? = null
    private var tvHighScore : TextView? = null
    private var sharedPreferences : SharedPreferences? = null
    private lateinit var isNewHighest : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.game_over)
        tvPoints = findViewById(R.id.tvPoints)
        tvHighScore = findViewById(R.id.tvHighScore)
        isNewHighest = findViewById(R.id.trophy)

        val points = intent.extras?.getInt("points")
        tvPoints?.text = points.toString()
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
        val highScore = sharedPreferences?.getInt("highScore", 0)

        if (points!! > highScore!!) {
            sharedPreferences?.edit()?.putInt("highScore", points)?.apply()
            tvHighScore?.text = points.toString()
            isNewHighest.setImageResource(R.drawable.trophy) // Set trophy image
            isNewHighest.visibility = ImageView.VISIBLE
        } else {
            tvHighScore?.text = highScore.toString()
            isNewHighest.setImageResource(R.drawable.star) // Set star image
            isNewHighest.visibility = ImageView.VISIBLE
        }
    }

    fun restart (view : View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    fun exit(view : View) {
        finish()
    }
}