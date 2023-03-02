package com.example.dicegame_cw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_menu)

        val aboutPopup = layoutInflater.inflate(R.layout.about_popup_layout, null)
        val popupWindow = PopupWindow(
            aboutPopup,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val popupCloseButt = aboutPopup.findViewById<Button>(R.id.popupClose)
        popupCloseButt.setOnClickListener {
            popupWindow.dismiss()
        }

        val aboutButt = findViewById<Button>(R.id.aboutButt)

        aboutButt.setOnClickListener {
            popupWindow.showAtLocation(aboutButt, Gravity.CENTER, 0, 0)
        }

    }
}