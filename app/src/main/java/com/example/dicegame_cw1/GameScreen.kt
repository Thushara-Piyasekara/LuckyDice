package com.example.dicegame_cw1

import android.content.Intent
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
    private var winScore: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        val gameIntent = intent
        winScore = gameIntent.getIntExtra("winScore", 101)
        val optimiseStrategy = gameIntent.getBooleanExtra("optimiseStrategy", false)

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

        //ComputerPlayer initialisation
        computerPlayer = if (optimiseStrategy) {
            SmartComputer(computerDices, this)
        } else
            DumbComputer(computerDices, this)

        throwButton = findViewById(R.id.throwButt)
        scoreButton = findViewById(R.id.scoreButt)
        scoreButton.isEnabled = false

        scoreBoard = findViewById(R.id.scoreBoard)
        winCounterBoard = findViewById(R.id.winCount)
        updateWinCounts(gameIntent)

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
            humanPlayer.clearDiceSelection()
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

    fun getHumanScore(): Int {
        return humanPlayer.getScore()
    }

    fun getScoreButton(): Button {
        return scoreButton
    }

    private fun checkWinner() {
        if (humanPlayer.getScore() >= winScore && humanPlayer.getScore() > computerPlayer.getScore()) {
            humanPlayer.addWin()
            showWinPopUp()
            throwButton.isEnabled = false
        } else if (computerPlayer.getScore() >= winScore && computerPlayer.getScore() > humanPlayer.getScore()) {
            computerPlayer.addWin()
            throwButton.isEnabled = false
            showLosePopUp()
        } else if (humanPlayer.getScore() >= winScore && computerPlayer.getScore() == humanPlayer.getScore()){
            throwButton.setOnClickListener {
                humanPlayer.throwDices()
                computerPlayer.throwDices()
                Handler(Looper.getMainLooper()).postDelayed({
                    checkWinner()
                }, 1002)

            }
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

    override fun onSaveInstanceState(outState: Bundle) {
        //Number of Wins and Scores of both player
        outState.putInt("human_score", humanPlayer.getScore())
        outState.putInt("computer_score", computerPlayer.getScore())
        outState.putInt("human_wins", humanPlayer.getWinCount())
        outState.putInt("computer_wins", computerPlayer.getWinCount())

        //Number of rerolls taken by human player
        outState.putInt("human_reroll_count", humanPlayer.getRerollCount())

        //Values of the dices of each player
        outState.putIntegerArrayList("human_dice_values", humanPlayer.getDiceValueList())
        outState.putIntegerArrayList("computer_dice_values", computerPlayer.getDiceValueList())

        //Whether the dices were clicked or not by the user
        outState.putIntegerArrayList("dice_clicked_state", humanPlayer.getClickedStates())

        //Whether the buttons were enabled or disabled
        outState.putBoolean("throw_button",throwButton.isEnabled)
        outState.putBoolean("score_button",scoreButton.isEnabled)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        //Restore win and score counters
        val savedHumanScore = savedInstanceState.getInt("human_score")
        val savedComputerScore = savedInstanceState.getInt("computer_score")
        val savedHumanWins = savedInstanceState.getInt("human_wins")
        val savedComputerWins = savedInstanceState.getInt("computer_wins")
        restoreScoresAndWins(savedHumanScore, savedComputerScore, savedHumanWins, savedComputerWins)

        //Restore reroll count
        val humanRerollCount = savedInstanceState.getInt("human_reroll_count")
        humanPlayer.setRerollCount(humanRerollCount)
//        restoreGameButtons(humanRerollCount)

        //Restore Dice values of each player
        val humanDiceValues = savedInstanceState.getIntegerArrayList("human_dice_values")
        val computerDiceValues = savedInstanceState.getIntegerArrayList("computer_dice_values")
        humanPlayer.setDiceValues(humanDiceValues as ArrayList<Int>)
        computerPlayer.setDiceValues(computerDiceValues as ArrayList<Int>)

        //Restore Clicked State of each dice
        val humanDiceClickState = savedInstanceState.getIntegerArrayList("dice_clicked_state")
        humanPlayer.restoreClickState(humanDiceClickState as ArrayList<Int>)

        //Restore game buttons isEnabled
        throwButton.isEnabled = savedInstanceState.getBoolean("throw_button")
        scoreButton.isEnabled = savedInstanceState.getBoolean("score_button")

        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun restoreScoresAndWins(
        savedHumanScore: Int,
        savedComputerScore: Int,
        savedHumanWins: Int,
        savedComputerWins: Int
    ) {
        scoreBoard.text = "$savedHumanScore / $savedComputerScore"
        humanPlayer.restoreScore(savedHumanScore)
        computerPlayer.restoreScore(savedComputerScore)
        winCounterBoard.text =
            "H: ${humanPlayer.getWinCount()} / C: ${computerPlayer.getWinCount()}"
        humanPlayer.restoreWinCount(savedHumanWins)
        computerPlayer.restoreWinCount(savedComputerWins)
    }


    override fun onBackPressed() {
        val backIntent = Intent(this, MainActivity::class.java)
        backIntent.putExtra("humanWins", humanPlayer.getWinCount())
        backIntent.putExtra("computerWins", computerPlayer.getWinCount())
        startActivity(backIntent)
        finish()
    }

    fun updateWinCounts(intent: Intent) {
        humanPlayer.setWinCount(intent.getIntExtra("humanWins", 33))
        computerPlayer.setWinCount(intent.getIntExtra("computerWins", 0))
        winCounterBoard.text =
            "H: ${humanPlayer.getWinCount()} / C: ${computerPlayer.getWinCount()}"
    }
}