package com.halil.ozel.xoxgame.presentation.ui

/**
 * Created by halilozel1903 on 22.06.2025.
 */
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halil.ozel.xoxgame.presentation.viewmodel.GameViewModel
import com.halil.ozel.xoxgame.domain.model.Player

@Composable
fun XoxGameScreen(viewModel: GameViewModel) {
    val board = viewModel.board
    val winner = viewModel.winner
    val isDraw = viewModel.isDraw
    val currentPlayer = viewModel.currentPlayer

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
                                enabled = (board.cells[row][col] == null && winner == null && !isDraw)
                            ) {
                                viewModel.onCellClicked(row, col)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = board.cells[row][col]?.name ?: "",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (board.cells[row][col] == Player.X) Color(0xFF1976D2) else Color(0xFFD32F2F)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        when {
            winner != null -> Text(
                text = "Kazanan: ${winner.name}",
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
        Button(onClick = { viewModel.resetGame() }) {
            Text("Yeniden Başlat")
        }
    }
}