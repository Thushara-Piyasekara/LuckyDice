package com.example.dicegame_cw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView

class GameScreen : AppCompatActivity() {
    private lateinit var humanPlayer: HumanPlayer
    private lateinit var computerPlayer: DumbComputer
    private lateinit var throwButton: Button
    private lateinit var scoreButton: Button

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
        humanPlayer = HumanPlayer(humanDices, counterHuman, this)

        val dice6 = Dice(findViewById(R.id.c_dice1))
        val dice7 = Dice(findViewById(R.id.c_dice2))
        val dice8 = Dice(findViewById(R.id.c_dice3))
        val dice9 = Dice(findViewById(R.id.c_dice4))
        val dice10 = Dice(findViewById(R.id.c_dice5))

        val computerDices = listOf(dice6, dice7, dice8, dice9, dice10)
        val counterComputer = findViewById<TextView>(R.id.counterComputer)
        computerPlayer = DumbComputer(computerDices, counterComputer,this)

        throwButton = findViewById(R.id.throwButt)
        scoreButton = findViewById(R.id.scoreButt)

        throwButton.setOnClickListener {
            if (humanPlayer.getRerollCount() == 0) {
                humanPlayer.throwDices()
                computerPlayer.throwDices()
            } else {
                humanPlayer.reRoll()
            }
        }
        scoreButton.setOnClickListener {
            startComputerAction()
        }
    }

    fun startComputerAction() {
        throwButton.isEnabled = false
        scoreButton.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            computerPlayer.play()
        }, 2000)
    }

    fun updateScoreAndEnableButtons() {
        humanPlayer.updateScore()
        computerPlayer.updateScore()
        throwButton.isEnabled = true
        scoreButton.isEnabled = true
    }
}