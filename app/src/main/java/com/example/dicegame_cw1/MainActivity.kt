package com.example.dicegame_cw1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_menu)

        //Setting actionListener for "About" button
        val aboutButt = findViewById<Button>(R.id.aboutButt)
        aboutButt.setOnClickListener {
            showPopUpWindow(aboutButt)
        }

        //Setting actionListener for "New Game" button
        val newGameButt = findViewById<Button>(R.id.newGameButt)

        newGameButt.setOnClickListener {
            val intent = Intent(this, GameScreen::class.java)
            startActivity(intent)
        }

    }


    //Makes the ABOUT popup window visible
    @SuppressLint("InflateParams")
    private fun showPopUpWindow(view: View) {
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


}