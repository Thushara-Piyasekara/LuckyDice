package com.example.dicegame_cw1


class HumanPlayer(private val diceList: List<Dice>, private val activity: GameScreen) : Player(diceList) {
    init {
        this.setDiceOnClickListener()
    }

    private fun setDiceOnClickListener() {
        for (dice in diceList) {
            dice.getImgButt().setOnClickListener {
                dice.toggleDiceLock()
            }
        }
    }

    fun reRoll() {
        activity.getScoreButton().isEnabled = (reRollCount != 0)
        if (reRollCount > 1) {
            reRollCount = 0
            activity.startComputerAction()
            throwDices()
        } else {
            throwDices()
        }
    }

    fun disableDiceSelection() {
        for (dice in diceList) {
            dice.getImgButt().isClickable = false
        }
    }

    fun enableDiceSelection() {
        for (dice in diceList) {
            dice.getImgButt().isClickable = true
        }
    }
}