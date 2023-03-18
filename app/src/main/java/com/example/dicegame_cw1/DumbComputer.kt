package com.example.dicegame_cw1

import android.os.Handler
import android.os.Looper



/**
 * Class to hold the random Strategy for the computer player
 * @author Thushara Piyasekara
 *
 * @property dices is the List of dice objects allocated to the computer player
 * @property activity
 */
open class DumbComputer(
    private val dices: List<Dice>,
    private val activity: GameScreen
) : Player(dices) {

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

    protected open fun makeReRollDecision(): Boolean {
        return listOf(true, false).random()
    }

    protected open fun pickDicesToRoll(): List<Dice> {
        val numberOfDices = (0..4).random()
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