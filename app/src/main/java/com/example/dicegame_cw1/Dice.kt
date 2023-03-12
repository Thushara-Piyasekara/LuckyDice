package com.example.dicegame_cw1

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import kotlin.random.Random

/**
 * Represents a Dice in the application
 *
 * @property imageButt ImageButton connected to the Dice
 */
class Dice(private val imageButt: ImageButton) {
    private var numOfSides: Int = 6
    private var headVal: Int = 0
    private var clicked: Boolean = false

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
    }

    //Rotates the dice and assigns a random number between 1-6
    fun roll() {
        resetDiceBackground()
        if (!clicked) {
            val rotateAnim =
                AnimationUtils.loadAnimation(this.imageButt.context, R.anim.rotate_anim)
            this.imageButt.startAnimation(rotateAnim)

            headVal = Random.nextInt(1, numOfSides + 1)
            Handler(Looper.getMainLooper()).postDelayed({
                this.imageButt.setImageResource(diceImagesArr[headVal - 1])
            }, 1000)
        } else {
            clicked = false
        }
    }

    //Toggles the Dice from rolling
    fun toggleDiceRolling() {
        if (clicked) {
            resetDiceBackground()
            clicked = false
        } else {
            this.imageButt.setBackgroundColor(Color.GREEN)
            clicked = true
        }
    }

    //Resets the background of a Dice ImageButton to TRANSPARENT
    private fun resetDiceBackground() {
        this.imageButt.setBackgroundColor(Color.TRANSPARENT)
    }

    //Returns the image button of the respective Dice object
    fun getImgButt(): ImageButton {
        return this.imageButt
    }

    //Returns the head value of the Dice object
    fun getHeadVal(): Int {
        return headVal
    }
}