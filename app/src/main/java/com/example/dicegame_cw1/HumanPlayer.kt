package com.example.dicegame_cw1

import android.widget.TextView

class HumanPlayer(private val diceList: List<Dice>, counterHuman: TextView, private val activity: GameScreen) : Player(diceList, counterHuman) {
    init {
        this.setDiceOnClickListener()
    }

    private fun setDiceOnClickListener() {
        for (dice in diceList) {
            dice.getImgButt().setOnClickListener {
                dice.toggleDiceRolling()
            }
        }
    }


    fun reRoll() {
        if (reRollCount > 1) {
            reRollCount = 0
            activity.startComputerAction()
            throwDices()
        } else {
            throwDices()
        }
    }

    fun scoreDices() {

    }

}