package com.example.dicegame_cw1

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var winScore: Int = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_menu)

        //Setting actionListener for win count change button
        val editWinCountButton = findViewById<Button>(R.id.editWinCountButton)
        editWinCountButton.setOnClickListener {
            updateWinScore()
        }
        //Setting actionListener for optimise switch
        val optimiseSwitch = findViewById<Switch>(R.id.optimiseSwitch)
        var optimiseStrategy = false
        optimiseSwitch.setOnCheckedChangeListener{ _,isClicked ->
            optimiseStrategy = isClicked
        }

        //Setting actionListener for "About" button
        val aboutButt = findViewById<Button>(R.id.aboutButt)
        aboutButt.setOnClickListener {
            showAboutPopUp(aboutButt)
        }

        //Setting actionListener for "New Game" button
        val newGameButt = findViewById<Button>(R.id.newGameButt)
        newGameButt.setOnClickListener {
            val gameIntent = Intent(this, GameScreen::class.java)
            gameIntent.putExtra("winScore",winScore)
            gameIntent.putExtra("optimiseStrategy",optimiseStrategy)
            startActivity(gameIntent)
        }

    }

    //Makes the ABOUT popup window visible
    private fun showAboutPopUp(view: View) {
        val aboutPopup = layoutInflater.inflate(R.layout.about_popup_layout, null)
        val popupWindow = PopupWindow(
            aboutPopup,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val popupCloseButt = aboutPopup.findViewById<Button>(R.id.popupClose)

        //Setting OnClickListener for the close button in the about popup
        popupCloseButt.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun updateWinScore() {
        val winScoreEditText = findViewById<EditText>(R.id.winScoreEdit)
        if (winScoreEditText.text.isNotBlank()) {
            winScore = winScoreEditText.text.toString().toInt()
            val winScoreTextView = findViewById<TextView>(R.id.currentWinScore)
            winScoreTextView.text = "Current Win Score : $winScore"
        }
    }
}