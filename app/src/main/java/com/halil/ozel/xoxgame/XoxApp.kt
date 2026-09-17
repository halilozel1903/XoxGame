package com.halil.ozel.xoxgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.halil.ozel.xoxgame.presentation.ui.XoxGameScreen
import com.halil.ozel.xoxgame.ui.theme.XoxGameTheme

class XoxApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XoxGameTheme {
                XoxGameScreen()
            }
        }
    }
}
