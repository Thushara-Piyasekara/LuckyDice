package com.example.dicegame_cw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView


class GameScreen : AppCompatActivity() {
    private lateinit var humanPlayer: HumanPlayer
    private lateinit var computerPlayer: DumbComputer
    private lateinit var throwButton: Button
    private lateinit var scoreButton: Button
    private lateinit var scoreBoard: TextView
    private lateinit var winCounterBoard: TextView
    private var winScore: Int = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        //Dices for Human Player
        val dice1 = Dice(findViewById(R.id.h_dice1))
        val dice2 = Dice(findViewById(R.id.h_dice2))
        val dice3 = Dice(findViewById(R.id.h_dice3))
        val dice4 = Dice(findViewById(R.id.h_dice4))
        val dice5 = Dice(findViewById(R.id.h_dice5))

        val humanDices = listOf(dice1, dice2, dice3, dice4, dice5)
        humanPlayer = HumanPlayer(humanDices, this)
        humanPlayer.disableDiceSelection()

        //Dices for Computer Player
        val dice6 = Dice(findViewById(R.id.c_dice1))
        val dice7 = Dice(findViewById(R.id.c_dice2))
        val dice8 = Dice(findViewById(R.id.c_dice3))
        val dice9 = Dice(findViewById(R.id.c_dice4))
        val dice10 = Dice(findViewById(R.id.c_dice5))

        val computerDices = listOf(dice6, dice7, dice8, dice9, dice10)
        computerPlayer = DumbComputer(computerDices, this)


        throwButton = findViewById(R.id.throwButt)
        scoreButton = findViewById(R.id.scoreButt)
        scoreButton.isEnabled = false

        scoreBoard = findViewById(R.id.scoreBoard)
        winCounterBoard = findViewById(R.id.winCount)

        throwButton.setOnClickListener {
            if (humanPlayer.getRerollCount() == 0) {
                humanPlayer.throwDices()
                computerPlayer.throwDices()
                Handler(Looper.getMainLooper()).postDelayed({
                    scoreButton.isEnabled = true
                    humanPlayer.enableDiceSelection()
                }, 1000)

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
        }, 500)
    }

    fun updateScoreAndEnableButtons() {
        humanPlayer.updateScoreAndResetRerollCount()
        computerPlayer.updateScoreAndResetRerollCount()
        scoreBoard.text = "${humanPlayer.getScore()} / ${computerPlayer.getScore()}"
        throwButton.isEnabled = true
        checkWinner()
        humanPlayer.disableDiceSelection()
    }

    fun getScoreButton(): Button {
        return scoreButton
    }

    private fun checkWinner() {
        if (humanPlayer.getScore() > winScore && humanPlayer.getScore() > computerPlayer.getScore()) {
            humanPlayer.addWin()
            showWinPopUp()
            throwButton.isEnabled = false
        } else if (computerPlayer.getScore() > winScore && computerPlayer.getScore() > humanPlayer.getScore()) {
            computerPlayer.addWin()
            throwButton.isEnabled = false
            showLosePopUp()
        }
        winCounterBoard.text =
            "H: ${humanPlayer.getWinCount()} / C: ${computerPlayer.getWinCount()}"
    }

    private fun showWinPopUp() {
        val aboutPopup = layoutInflater.inflate(R.layout.win_popup_layout, null)
        val popupWindow = PopupWindow(
            aboutPopup,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val popupCloseButt = aboutPopup.findViewById<Button>(R.id.popupClose)

        //Setting OnClickListener for the close button in the about popup
        popupCloseButt.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.showAtLocation(findViewById(R.id.c_dice3), Gravity.CENTER, 0, 0)
    }

    private fun showLosePopUp() {
        val aboutPopup = layoutInflater.inflate(R.layout.lose_popup_layout, null)
        val popupWindow = PopupWindow(
            aboutPopup,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val popupCloseButt = aboutPopup.findViewById<Button>(R.id.popupClose)

        //Setting OnClickListener for the close button in the about popup
        popupCloseButt.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.showAtLocation(findViewById(R.id.c_dice3), Gravity.CENTER, 0, 0)
    }


}