package com.example.dicegame_cw1

import java.util.concurrent.locks.Condition

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
    private val averageRollScore = ((maxDiceValue + minDiceValue) / 2) * numOfDices // 17.5

    fun makeReRollDecision(humanScore: Int): Boolean {
        return (this.rollScore != maxRollScore)
    }


    fun pickDicesToRoll(lastHumanScore: Int): MutableList<Dice> {
        val lastRoundDifference = lastHumanScore - this.totalScore // last round's difference between the scores of human player and computer player
        val catchUpAverage = lastRoundDifference + averageRollScore // Predicted average amount the computer should catch up
        val catchUpMax = lastRoundDifference + maxRollScore // Predicted max amount the computer should catch up
        val catchUpMin = lastRoundDifference + minRollScore // Predicted min amount the computer should catch up

        var dicesToReroll = mutableListOf<Dice>()

        // Safe - human can't catch up
        if (catchUpMax < 6) {
            for (dice in dices) {
                if (dice.getHeadVal() < maxDiceValue) {
                    dicesToReroll.add(dice)
                }
            }
        }
        // Safe - human might catch up (very low possibility)
        else if (catchUpMax < this.rollScore) {
            for (dice in dices) {
                if (dice.getHeadVal() < averageDiceValue) {
                    dicesToReroll.add(dice)
                }
            }
        }
        // Not safe - human will definitely catch up
        else if (catchUpMin > 30) {
            for (dice in dices) {
                if (dice.getHeadVal() <= maxDiceValue) { // Gambles for the future :)
                    dicesToReroll.add(dice)
                }
            }
        }
        // Not safe - human will catch up if current dice values doesn't increase
        else if (catchUpMax > this.rollScore) {
            for (dice in dices) {
                if (dice.getHeadVal() <= averageDiceValue) {
                    dicesToReroll.add(dice)
                }
            }
        }
        // Not safe - human will catch up if current dice values doesn't increase
        else if (catchUpMax > 6) {
            for (dice in dices) {
                if (dice.getHeadVal() <= averageDiceValue) {
                    dicesToReroll.add(dice)
                }
            }
        }
        return dicesToReroll
    }


}