package com.halil.ozel.xoxgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halil.ozel.xoxgame.presentation.viewmodel.GameViewModel
import com.halil.ozel.xoxgame.domain.model.Player

@Composable
fun XoxGameScreen(viewModel: GameViewModel) {
    val uiState = viewModel.uiState

    // Dialog state
    var showDialog by remember { mutableStateOf(false) }

    // Show dialog if game is over
    LaunchedEffect(uiState.winner, uiState.isDraw) {
        if (uiState.winner != null || uiState.isDraw) {
            showDialog = true
        }
    }

    if (showDialog && (uiState.winner != null || uiState.isDraw)) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    viewModel.resetGame()
                }) {
                    Text("Play Again")
                }
            },
            title = {
                Text(text = if (uiState.winner != null) "Game Over" else "Draw")
            },
            text = {
                Text(
                    text = when {
                        uiState.winner != null -> "Winner: ${uiState.winner.name}\nWould you like to play again?"
                        uiState.isDraw -> "It's a draw!\nWould you like to play again?"
                        else -> ""
                    }
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(UiConstants.BoardBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tic-Tac-Toe",
            fontSize = 32.sp,
            color = UiConstants.TextDark
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
                                color = UiConstants.CellBackground,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(
                                enabled = (uiState.board.cells[row][col] == null && uiState.winner == null && !uiState.isDraw)
                            ) {
                                viewModel.onCellClicked(row, col)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.board.cells[row][col]?.name ?: "",
                            fontSize = 36.sp,
                            color = when (uiState.board.cells[row][col]) {
                                Player.X -> UiConstants.TextBlue
                                Player.O -> UiConstants.TextRed
                                else -> UiConstants.TextDark
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        when {
            uiState.winner != null -> Text(
                text = "Winner: ${uiState.winner.name}",
                color = UiConstants.TextGreen,
                fontSize = 22.sp
            )
            uiState.isDraw -> Text(
                text = "It's a draw!",
                color = UiConstants.TextOrange,
                fontSize = 22.sp
            )
            else -> Text(
                text = "Next: ${uiState.currentPlayer.name}",
                color = UiConstants.TextGray,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { viewModel.resetGame() },
            enabled = uiState.isResetEnabled
        ) {
            Text("Restart")
        }
    }
}