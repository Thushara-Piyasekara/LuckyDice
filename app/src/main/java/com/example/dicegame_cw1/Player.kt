package com.example.dicegame_cw1

open class Player(private val diceList: List<Dice>) {
    private var numOfWins: Int = 0
    private var totalScore: Int = 0
    protected var rollScore: Int = 0
    protected var reRollCount: Int = 0


    open fun throwDices() {
        rollScore = 0
        for (dice in diceList) {
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

    fun addWin() {
        numOfWins++
    }

    fun getRerollCount(): Int {
        return reRollCount
    }
    fun getWinCount(): Int {
        return numOfWins
    }

}