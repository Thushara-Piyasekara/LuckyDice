package com.example.dicegame_cw1

/**
 * Parent class for both Human player and Dumb player(Computer)
 * Holds the common methods used by both Classes
 * @property dices dice objects owned by the Player
 */
open class Player(private var dices: List<Dice>) {
    private var numOfWins: Int = 0
    protected var totalScore: Int = 0
    protected var rollScore: Int = 0
    protected var reRollCount: Int = 0

    /**
     * Assigns a random value to dices and updates the rollScore
     *
     */
    open fun throwDices() {
        rollScore = 0
        for (dice in dices) {
            dice.roll()
            rollScore += dice.getHeadVal()
        }
        reRollCount++
    }


    fun updateScoreAndResetRerollCount() {
        reRollCount = 0
        totalScore += rollScore
    }

    fun getScore(): Int {
        return this.totalScore
    }

    fun getRerollCount(): Int {
        return reRollCount
    }

    fun addWin() {
        numOfWins++
    }

    fun getWinCount(): Int {
        return numOfWins
    }

    /**
     *
     * @return returns the head values of the dices owned by the object as an array
     */
    fun getDiceValueList(): ArrayList<Int> {
        val diceValues = arrayListOf<Int>()
        for (dice in dices) {
            if (dice.getHeadVal() == 0) {
                diceValues.add(1)
            } else {
                diceValues.add(dice.getHeadVal())
            }
        }
        return diceValues
    }

    /**
     * updates the head values of dices with the values of an given array
     * used to restore the dice objects after a configuration change
     *
     * @param diceValues array holding the head values
     */
    fun setDiceValues(diceValues: ArrayList<Int>) {
        for (i in 0..4) {
            dices[i].restoreDice(diceValues[i])
        }
    }

    fun restoreWinCount(wins: Int) {
        numOfWins = wins
    }

    fun restoreScore(score: Int) {
        totalScore = score
    }

    fun setWinCount(winCount: Int) {
        numOfWins = winCount
    }
}