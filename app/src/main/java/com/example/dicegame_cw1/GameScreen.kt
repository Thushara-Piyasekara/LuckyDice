package com.example.dicegame_cw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameScreen : AppCompatActivity() {
    private lateinit var humanPlayer:HumanPlayer
    private lateinit var computerPlayer:DumbComputer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        val dice1 = Dice(findViewById(R.id.h_dice1))
        val dice2 = Dice(findViewById(R.id.h_dice2))
        val dice3 = Dice(findViewById(R.id.h_dice3))
        val dice4 = Dice(findViewById(R.id.h_dice4))
        val dice5 = Dice(findViewById(R.id.h_dice5))

        val humanDices = listOf(dice1, dice2, dice3, dice4, dice5)
        val counterHuman = findViewById<TextView>(R.id.counterHuman)
        humanPlayer = HumanPlayer(humanDices, counterHuman,this)

        val dice6 = Dice(findViewById(R.id.c_dice1))
        val dice7 = Dice(findViewById(R.id.c_dice2))
        val dice8 = Dice(findViewById(R.id.c_dice3))
        val dice9 = Dice(findViewById(R.id.c_dice4))
        val dice10 = Dice(findViewById(R.id.c_dice5))

        val computerDices = listOf(dice6,dice7,dice8,dice9,dice10)
        val counterComputer = findViewById<TextView>(R.id.counterComputer)
        computerPlayer = DumbComputer(computerDices,counterComputer)

        val throwButton = findViewById<Button>(R.id.throwButt)
        val scoreButton = findViewById<Button>(R.id.scoreButt)

        throwButton.setOnClickListener {
            humanPlayer.throwDices()
        }
        scoreButton.setOnClickListener {
            humanPlayer.updateScore()
        }

    }

    fun startComputerAction() {
        computerPlayer.throwDices()
        humanPlayer.updateScore()
    }
}