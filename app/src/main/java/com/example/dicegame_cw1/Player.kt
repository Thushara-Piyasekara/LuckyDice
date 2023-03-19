package com.example.dicegame_cw1

open class Player(private var dices: List<Dice>) {
    private var numOfWins: Int = 0
    protected var totalScore: Int = 0
    protected var rollScore: Int = 0
    protected var reRollCount: Int = 0

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

    fun getDiceValueList(): ArrayList<Int> {
        val diceValues = arrayListOf<Int>()
        for (dice in dices) {
            diceValues.add(dice.getHeadVal())
        }
        return diceValues
    }

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

}