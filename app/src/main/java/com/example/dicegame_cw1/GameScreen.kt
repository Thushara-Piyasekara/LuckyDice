package com.example.dicegame_cw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)


        val dice1 = Dice(findViewById(R.id.h_dice1))
        val dice2 = Dice(findViewById(R.id.h_dice2))
        val dice3 = Dice(findViewById(R.id.h_dice3))
        val dice4 = Dice(findViewById(R.id.h_dice4))
        val dice5 = Dice(findViewById(R.id.h_dice5))
        val humanDices = listOf<Dice>(dice1, dice2, dice3, dice4, dice5)
        val counterHuman = findViewById<TextView>(R.id.counterHuman)

        val human = HumanPlayer(humanDices,counterHuman)

        val throwButton = findViewById<Button>(R.id.throwButt)
        val scoreButton = findViewById<Button>(R.id.scoreButt)

        throwButton.setOnClickListener {
            human.throwDices()
        }

        scoreButton.setOnClickListener {
            human.scoreDices()
        }
    }
}