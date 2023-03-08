package com.example.dicegame_cw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton

class GameScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        val throwButt = findViewById<Button>(R.id.throwButt)
        val rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)

        val dice1= Dice(findViewById<ImageButton>(R.id.dice1))
        val dice2= Dice(findViewById<ImageButton>(R.id.dice2))
        val dice3= Dice(findViewById<ImageButton>(R.id.dice3))
        val dice4= Dice(findViewById<ImageButton>(R.id.dice4))
        val dice5= Dice(findViewById<ImageButton>(R.id.dice5))

        val humanDices = listOf<Dice>(dice1,dice2,dice3,dice4,dice5)


        throwButt.setOnClickListener {
            for (dice in humanDices) {
                dice.getImgButt().startAnimation(rotateAnim)
                Handler(Looper.getMainLooper()).postDelayed({
                    dice.roll()
                }, 1000)
            }
        }
    }
}