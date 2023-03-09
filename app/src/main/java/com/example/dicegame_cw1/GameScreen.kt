package com.example.dicegame_cw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class GameScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)


        val dice1 = Dice(findViewById<ImageButton>(R.id.dice1))
        val dice2 = Dice(findViewById<ImageButton>(R.id.dice2))
        val dice3 = Dice(findViewById<ImageButton>(R.id.dice3))
        val dice4 = Dice(findViewById<ImageButton>(R.id.dice4))
        val dice5 = Dice(findViewById<ImageButton>(R.id.dice5))
        val humanDices = listOf<Dice>(dice1, dice2, dice3, dice4, dice5)


        val throwButt = findViewById<Button>(R.id.throwButt)
        val scoreButt = findViewById<Button>(R.id.scoreButt)
        var counterHuman = findViewById<TextView>(R.id.counterHuman)

        throwButt.setOnClickListener {
            throwDices(humanDices)
        }

        scoreButt.setOnClickListener {
            scoreDices(humanDices, counterHuman)
        }
    }

    fun throwDices(humanDices: List<Dice>) {
        val rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
        for (dice in humanDices) {
            dice.getImgButt().startAnimation(rotateAnim)
            Handler(Looper.getMainLooper()).postDelayed({
                dice.roll()
            }, 1000)
        }
    }

    fun scoreDices(humanDices: List<Dice>, counterHuman: TextView) {
        var newScore: Int = counterHuman.text.toString().toInt()
        for (dice in humanDices) {
            newScore = newScore + dice.getHeadVal()
        }
        counterHuman.text = newScore.toString()
    }
}