package com.halil.ozel.xoxgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.halil.ozel.xoxgame.presentation.ui.XoxGameScreen
import com.halil.ozel.xoxgame.presentation.viewmodel.GameViewModel

/**
 * Created by halilozel1903 on 22.06.2025.
 */
class XoxApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XoxGameScreen(viewModel = GameViewModel())
        }
    }
}