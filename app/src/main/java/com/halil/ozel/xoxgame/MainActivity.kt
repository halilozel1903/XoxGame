package com.halil.ozel.xoxgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                XoxGameScreen()
            }
        }
    }
}

enum class Player { X, O }

@Composable
fun XoxGameScreen() {
    var board by remember { mutableStateOf(List(3) { MutableList<Player?>(3) { null } }) }
    var currentPlayer by remember { mutableStateOf(Player.X) }
    var winner by remember { mutableStateOf<Player?>(null) }
    var isDraw by remember { mutableStateOf(false) }

    fun resetGame() {
        board = List(3) { MutableList<Player?>(3) { null } }
        winner = null
        isDraw = false
        currentPlayer = Player.X
    }

    fun checkWinner(): Player? {
        // Rows & Columns
        for (i in 0..2) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0]
            if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return board[0][i]
        }
        // Diagonals
        if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return board[0][0]
        if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return board[0][2]
        return null
    }

    fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it != null } }
    }

    LaunchedEffect(board) {
        val win = checkWinner()
        if (win != null) {
            winner = win
        } else if (isBoardFull()) {
            isDraw = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "XOX Game",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222)
        )
        Spacer(modifier = Modifier.height(24.dp))
        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .padding(4.dp)
                            .background(
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(
                                enabled = (board[row][col] == null && winner == null && !isDraw)
                            ) {
                                if (board[row][col] == null && winner == null && !isDraw) {
                                    board = board.toMutableList().apply {
                                        this[row] = this[row].toMutableList().apply {
                                            this[col] = currentPlayer
                                        }
                                    }
                                    currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = board[row][col]?.name ?: "",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (board[row][col] == Player.X) Color(0xFF1976D2) else Color(0xFFD32F2F)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        when {
            winner != null -> Text(
                text = "Kazanan: ${winner!!.name}",
                color = Color(0xFF43A047),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
            isDraw -> Text(
                text = "Berabere!",
                color = Color(0xFFFB8C00),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
            else -> Text(
                text = "Sıradaki: ${currentPlayer.name}",
                color = Color(0xFF616161),
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { resetGame() }) {
            Text("Yeniden Başlat")
        }
    }
}