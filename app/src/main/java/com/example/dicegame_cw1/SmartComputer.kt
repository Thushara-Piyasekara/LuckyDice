package com.example.dicegame_cw1

import android.widget.TextView

class SmartComputer(
    private val dices: List<Dice>,
    private val activity: GameScreen
) : DumbComputer(dices, activity) {

    

}