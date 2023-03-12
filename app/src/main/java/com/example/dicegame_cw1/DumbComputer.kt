package com.example.dicegame_cw1

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.util.*

/**
 * Class to hold the random Strategy for the computer player
 *@author Thushara Piyasekara
 *
 * @property dices is the List of dice objects allocated to the computer player
 *
 */
class DumbComputer(
    private val dices: List<Dice>,
    private val counter: TextView,
    private val activity: GameScreen
) : Player(dices, counter) {

    /**
     * Makes a decision to re roll and picks Dices to keep from re rolling upto two times
     *
     */
    fun play() {
        if (makeReRollDecision()) {
            throwDices(pickDicesToRoll())
            Handler(Looper.getMainLooper()).postDelayed({
                if (makeReRollDecision()) {
                    throwDices(pickDicesToRoll())
                    Handler(Looper.getMainLooper()).postDelayed({
                        activity.updateScoreAndEnableButtons()
                    }, 1000)
                } else {
                    activity.updateScoreAndEnableButtons()
                }
            }, 1000)
        } else {
            activity.updateScoreAndEnableButtons()
        }
    }

    private fun makeReRollDecision(): Boolean {
        val random = Random()
//        return random.nextBoolean()
        return true
    }

    private fun pickDicesToRoll(): List<Dice> {
        val random = Random()
        val numberOfDices = random.nextInt(5)
        return dices.shuffled().take(numberOfDices)
    }

    private fun throwDices(dicesToRoll: List<Dice>) {
        rollScore = 0
        for (dice in dicesToRoll) {
            dice.roll()
        }
        for (dice in dices) {
            rollScore += dice.getHeadVal()
        }
        reRollCount++
    }

}