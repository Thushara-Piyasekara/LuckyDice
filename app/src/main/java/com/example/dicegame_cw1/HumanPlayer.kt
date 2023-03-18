package com.example.dicegame_cw1


class HumanPlayer(private val dices: List<Dice>, private val activity: GameScreen) : Player(dices) {

    init {
        this.setDiceOnClickListener()
    }

    private fun setDiceOnClickListener() {
        for (dice in dices) {
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
        for (dice in dices) {
            dice.getImgButt().isClickable = false
        }
    }

    fun enableDiceSelection() {
        for (dice in dices) {
            dice.getImgButt().isClickable = true
        }
    }

    fun getClickedStates(): ArrayList<Int> {
        val clickedStates = arrayListOf<Int>()
        for (dice in dices) {
            if (dice.getClicked()) {
                clickedStates.add(1)
            } else {
                clickedStates.add(0)
            }
        }
        return clickedStates
    }

    fun restoreClickState(buttonStates: ArrayList<Int>) {
        for (i in 0..4) {
            if (buttonStates[i] == 1) {
                dices[i].toggleDiceLock()
            }
        }
    }

    override fun restoreRerollCount(savedCount: Int) {
        activity.restoreGameButtons(savedCount)

    }

}