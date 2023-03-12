package com.example.dicegame_cw1
import android.widget.TextView
import java.util.concurrent.locks.ReentrantLock
import kotlin.math.acos


class HumanPlayer(private val diceList: List<Dice>, private val counterHuman: TextView,private val activity: GameScreen):Player(diceList,counterHuman) {
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


    override fun throwDices() {
        super.throwDices()
        if (reRollCount > 2) {
            activity.startComputerAction()
        }
    }
}