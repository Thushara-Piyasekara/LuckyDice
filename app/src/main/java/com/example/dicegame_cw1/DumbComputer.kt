package com.example.dicegame_cw1

import java.util.*

/**
 * Class to hold the random Strategy for the computer player
 *@author Thushara Piyasekara
 *
 * @property Dices is the List of dice objects allocated to the computer player
 *
 */
class DumbComputer(private val Dices: List<Dice>)  {

    fun pickDicesToKeep() {

    }

    /**
     *Decides whether to re roll the dices randomly
     *
     * @return Returns a random boolean value
     */
    fun makeReRollDecision():Boolean {
        val random = Random()
        return random.nextBoolean()
    }

}