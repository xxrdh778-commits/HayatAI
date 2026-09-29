package com.hayat.ai

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = TextView(this).apply {
            text = "Hayat AI\n\nApp is running."
            textSize = 24f
            setPadding(40, 80, 40, 40)
        }

        setContentView(text)
    }
}
