/*
-------------------------------------------------- Efficient Computer Strategy Documentation----------------------------------------------

# First, the computer decides whether it should re-roll based on the Dice values it gets.
# If it doesn't get perfect 6 for all 5 dices it decides to re-roll

# The rest of strategy is based on the difference between current totals of Human player and the Computer player.
# It calculates the difference by subtracting the total of Human player by the total of Computer player.
   If it is a positive value, --> Human player has the lead
   If it is a negative value, --> Computer player has the lead

# Then two catchup values are calculated by adding the "maximum roll total" and "minimum roll total" separately.
# These two values indicate the predicted maximum and minimum amounts the Computer player needs to roll in
  order to get ahead of the human player.

# Then comparing these two values(catchUpMax and catchUpMin) with the "total of the Computer dices of current roll"(rollScore),
  4 possible scenarios are identified.

   1. Safe - computer is ahead and human can't catch up
   2. Safe - computer is ahead but human might catch up if computer get a lower roll score (very low possibility)
   3. Not safe - human might catch up if current dice values doesn't increase
   4. Not safe - human has a higher score and computer can't catch up (worst case scenario)

# Then respective to these scenarios, dices to re-roll are chosen by the computer

   1. Dices lower than "Maximum Dice Value(6)" - 1,2,3,4,5               <-- makes high risk moves because its in a safest position
   2. Dices lower than "Average Dice Value(3)" - 1,2                     <-- makes a low risk move because its in a fairly safe position
   3. Dices lower than or equal to "Average Dice Value(3)" - 1,2,3       <-- makes a low risk move because it current position is not safe
   4. Dices lower than "Maximum Dice Value(6)" - 1,2,3,4,5               <-- makes a high risk move because its in the worst position possible

# After the first re-roll computer then again uses the same strategy to decide whether to re-roll and choose which dices to re-roll


 ######### Advantages ##########
   1. The strategy considers the future aspects of the game.
   2. High chance of sustaining the lead if computer gets a higher score than the human.
   3. Uses both re-rolls almost every time to utilise the maximum amount of moves

 ######### Disadvantages ##########
   1. High chance of failure if computer loses the lead.
   2. If human gets the lead, computer will make high risk moves consecutively

----------------------------------------------------------------- END -------------------------------------------------------------------
 */


package com.example.dicegame_cw1
import android.os.Handler
import android.os.Looper

/**
 * Class for the efficient strategy for Computer player
 *
 * @property dices Dice objects owned by the computer Player
 * @property activity GameScreen activity used to call certain methods
 */
class SmartComputer(
    private val dices: List<Dice>,
    private val activity: GameScreen
) : DumbComputer(dices, activity) {

    private val maxDiceValue = 6
    private val minDiceValue = 1
    private val averageDiceValue = (maxDiceValue + minDiceValue) / 2
    private val numOfDices = this.dices.size // 5
    private val maxRollScore = maxDiceValue * numOfDices
    private val minRollScore = minDiceValue * numOfDices

    /**
     * Decides whether to re roll in a strategic and efficient manner
     * Decides which dices to re roll and rerolls them
     * Updates the scores after the Re rolls are done
     *
     * reference 1 :- https://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android
     * used the,
     *         Handler(Looper.getMainLooper()).postDelayed({
                    //CODE TO RUN
                }, 500)
     * part from the original code
     * reference 2 :- https://developer.android.com/reference/android/os/Handler
     * Learned about the functionality of Handler Class
     *
     */
    override fun play() {
        val humanScore = activity.getHumanScore()
        if (makeReRollDecision()) {
            throwDices(pickDicesToRoll(humanScore))
            Handler(Looper.getMainLooper()).postDelayed({
                if (makeReRollDecision()) {
                    throwDices(pickDicesToRoll(humanScore))
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
     * Decides whether to reroll or not
     * If even one dice is less than 6, decides to reroll
     *
     * @return boolean value for reRoll decision
     */
    override fun makeReRollDecision(): Boolean {
        return (this.rollScore != maxRollScore)
    }

    /**
     * Picks Dices to reroll based on the difference between player scores and current values of the dices
     * The 4 scenarios are explained above
     *
     * @param lastHumanScore Total Score of the human player upto last round
     * @return Returns a list of dice objects which were chosen worthy to be reRolled
     */
    fun pickDicesToRoll(lastHumanScore: Int): MutableList<Dice> {
        val lastRoundDifference = lastHumanScore - this.totalScore // last round's difference between the scores of human player and computer player
        val catchUpMax = lastRoundDifference + maxRollScore // Predicted max amount the computer should catch up
        val catchUpMin = lastRoundDifference + minRollScore // Predicted min amount the computer should catch up

        var dicesToReroll = mutableListOf<Dice>()

        // Safe - computer is ahead and human can't catch up
        if (catchUpMax < maxDiceValue) {
            for (dice in dices) {
                if (dice.getHeadVal() < maxDiceValue) {
                    dicesToReroll.add(dice)
                }
            }
        }
        // Safe - computer is ahead but human might catch up if computer get a lower roll score (very low possibility)
        // 6 < catchUpMax < this.rollScore
        else if (catchUpMax < this.rollScore) {
            for (dice in dices) {
                if (dice.getHeadVal() < averageDiceValue) {
                    dicesToReroll.add(dice)
                }
            }
        }
        // Not safe - human might catch up if current dice values doesn't increase
        // 6 < this.rollScore < catchUpMax
        else if (catchUpMax > this.rollScore) {
            for (dice in dices) {
                if (dice.getHeadVal() <= averageDiceValue) {
                    dicesToReroll.add(dice)
                }
            }
        }
        // Not safe - human has a higher score and computer can't catch up (worst case scenario)
        //  6 < this.rollScore < 30 < catchUpMax
        else if (catchUpMin > maxRollScore) {
            for (dice in dices) {
                if (dice.getHeadVal() < maxDiceValue) { // Gambles for the future :)
                    dicesToReroll.add(dice)
                }
            }
        }
        return dicesToReroll
    }


}