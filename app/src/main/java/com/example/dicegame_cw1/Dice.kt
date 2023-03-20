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

    //Array for saving all the dice images
    private val diceImagesArr = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )


    /**
     * Checks if the Dice image button is clicked, if not clicked,
     * Rotates the dice and assigns a random number between 1-6
     *
     * used to delay the action of computer in order to make it visible for the user
     * reference 1 :- https://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android
     * used the,
     *           Handler(Looper.getMainLooper()).postDelayed({
                      //CODE TO RUN
                 }, 500)
     * part from the original code
     * reference 2 :- https://developer.android.com/reference/android/os/Handler
     * Learned about the functionality of Handler Class
     */
    fun roll() {
        resetDiceBackground()
        if (!clicked) {
                val rotateAnim =
                    AnimationUtils.loadAnimation(this.imageButt.context, R.anim.rotate_anim)
                this.imageButt.startAnimation(rotateAnim)

                headVal = Random.nextInt(1, numOfSides + 1)
//                headVal = 6
                Handler(Looper.getMainLooper()).postDelayed({
                    this.imageButt.setImageResource(diceImagesArr[headVal - 1])
                }, 500)
        } else {
            clicked = false
        }
    }

    //Toggles the Dice from rolling
    fun toggleDiceLock() {
        if (clicked) {
            resetDiceBackground()
            clicked = false
        } else {
            this.imageButt.setBackgroundColor(Color.parseColor("#FFC4D8"))
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

    fun getClicked():Boolean {
        return clicked
    }

    fun restoreDice(value: Int) {
        headVal = value
        imageButt.setImageResource(diceImagesArr[value-1])
    }
}