package com.example.dicegame_cw1

import android.widget.TextView


class HumanPlayer(private val diceList: List<Dice>, private val counterHuman: TextView) {
    private var numOfWins: Int = 0
    private var totalScore: Int = 0
    private var rollScore: Int = 0
    private var reRollCount: Int = 0

    init {
        this.setDiceOnClickListener()
    }

    fun throwDices() {
        rollScore = 0
        for (dice in diceList) {
            dice.roll()
            rollScore = +dice.getHeadVal()
        }
    }

    fun scoreDices() {
        totalScore = +rollScore
        counterHuman.text = totalScore.toString()
    }

    fun setDiceOnClickListener() {
        for (dice in diceList) {
            dice.getImgButt().setOnClickListener {
                dice.toggleDiceRolling()
            }
        }
    }

    fun getScore(): Int {
        return this.totalScore
    }

    fun addWin() {
        numOfWins++
    }

}