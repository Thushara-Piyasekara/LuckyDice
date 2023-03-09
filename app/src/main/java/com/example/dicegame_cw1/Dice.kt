package com.example.dicegame_cw1

import android.graphics.Color
import android.widget.ImageButton
import kotlin.random.Random

//Constructor for the Dice object
class Dice(private val imageButt: ImageButton) {
    private var numOfSides: Int = 6
    private var headVal: Int = 0
    private var count: Int = 0

    private val diceImagesArr = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    init {
        this.imageButt.setImageResource(R.drawable.dice_1)
        resetDiceBackground()
        setDiceOnClickListner()
    }

    //Assigns a random number between 1-6 to the Dice object
    fun roll() {
        resetDiceBackground()
        headVal = Random.nextInt(1, numOfSides + 1)
        this.imageButt.setImageResource(diceImagesArr[headVal - 1])
        count += headVal
    }

    //Returns the image button of the respective Dice object
    fun getImgButt(): ImageButton {
        return this.imageButt
    }

    fun getHeadVal(): Int {
        return headVal
    }

    private fun setDiceOnClickListner() {
        this.imageButt.setOnClickListener {
            this.imageButt.setBackgroundColor(Color.GREEN)
        }
    }

    private fun resetDiceBackground() {
        this.imageButt.setBackgroundColor(Color.TRANSPARENT)

    }

}