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
     * used to delay the action of computer in order to make it visible for the user
     * reference 1 :- https://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android
     * used the,
     *         Handler(Looper.getMainLooper()).postDelayed({
                  //CODE TO RUN
               }, 500)
     * part from the original code
     * reference 2 :- https://developer.android.com/reference/android/os/Handler
     * Learned about the functionality of Handler Class
     */
    open fun play() {
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

    /**
     * Decides whether to reroll or not randomly
     *
     * @return boolean value for reRoll decision
     */
    open fun makeReRollDecision(): Boolean {
        return listOf(true, false).random()
    }

    /**
     * pickes a random number of dices in a random order to re roll
     *
     * @return Returns a list of dice objects which were chosen worthy to be reRolled
     */
    private fun pickDicesToRoll(): List<Dice> {
        val numberOfDices = (0..4).random()
        return dices.shuffled().take(numberOfDices)
    }

    /**
     * throws the given list of dices and updates the total score for that round
     *
     * @param dicesToRoll dices that should be rolled
     */
    protected fun throwDices(dicesToRoll: List<Dice>) {
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