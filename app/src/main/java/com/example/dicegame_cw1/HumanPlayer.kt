package com.example.dicegame_cw1

/**
 * Class representing the human player
 *
 * @property dices dices owned by the human player Object
 * @property activity used to refer to the GameActivity for calling certain methods
 */
class HumanPlayer(private val dices: List<Dice>, private val activity: GameScreen) : Player(dices) {

    init {
        this.setDiceOnClickListener()
    }

    /**
     * Makes the dice ImageButtons clickable
     * used to provide the option for the player to choose which dices to keep from reRolling
     */
    private fun setDiceOnClickListener() {
        for (dice in dices) {
            dice.getImgButt().setOnClickListener {
                dice.toggleDiceLock()
            }
        }
    }

    /**
     * Re rolls the dices
     * If its the 2nd reroll, Calls the startComputerAction() method to transfer the power to Computer player
     */
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

    /**
     * used to restore after a configuration change
     * @return whether a Dice ImageButton is clicked or not
     */
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

    /**
     * used to restore the click states after a configuration change
     *
     * @param buttonStates if the value is 1 the respective button should be clocked
     */
    fun restoreClickState(buttonStates: ArrayList<Int>) {
        for (i in 0..4) {
            if (buttonStates[i] == 1) {
                dices[i].toggleDiceLock()
            }
        }
    }

    fun setRerollCount(savedCount: Int) {
        reRollCount = savedCount
    }

    /**
     * reverts the all the dices Clicked states into unclicked
     *
     */
    fun clearDiceSelection() {
        for (dice in dices) {
            if (dice.getClicked()) {
                dice.toggleDiceLock()
            }
        }
    }
}