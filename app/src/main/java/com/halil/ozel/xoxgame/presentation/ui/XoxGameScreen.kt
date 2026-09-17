package com.halil.ozel.xoxgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.halil.ozel.xoxgame.R
import com.halil.ozel.xoxgame.di.AppModule
import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player
import com.halil.ozel.xoxgame.presentation.viewmodel.GameViewModel

@Composable
fun XoxGameScreen(
    viewModel: GameViewModel = viewModel(factory = AppModule.gameViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val winner = uiState.winner
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(winner, uiState.isDraw) {
        if (winner != null || uiState.isDraw) {
            showDialog = true
        }
    }

    if (showDialog && (winner != null || uiState.isDraw)) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    viewModel.resetGame()
                }) {
                    Text(stringResource(R.string.play_again))
                }
            },
            title = {
                Text(
                    text = if (winner != null) {
                        stringResource(R.string.game_over)
                    } else {
                        stringResource(R.string.draw_title)
                    }
                )
            },
            text = {
                Text(
                    text = when {
                        winner != null -> stringResource(R.string.winner_message, winner.name)
                        uiState.isDraw -> stringResource(R.string.draw_message)
                        else -> ""
                    }
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(UiConstants.BoardBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.game_title),
            fontSize = 32.sp,
            color = UiConstants.TextDark
        )
        Spacer(modifier = Modifier.height(24.dp))
        for (row in 0 until Board.SIZE) {
            Row {
                for (col in 0 until Board.SIZE) {
                    val occupant = uiState.board.cell(row, col)
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .padding(4.dp)
                            .background(
                                color = UiConstants.CellBackground,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(
                                enabled = occupant == null && winner == null && !uiState.isDraw
                            ) {
                                viewModel.onCellClicked(row, col)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = occupant?.name.orEmpty(),
                            fontSize = 36.sp,
                            color = when (occupant) {
                                Player.X -> UiConstants.TextBlue
                                Player.O -> UiConstants.TextRed
                                null -> UiConstants.TextDark
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        when {
            winner != null -> Text(
                text = stringResource(R.string.winner_status, winner.name),
                color = UiConstants.TextGreen,
                fontSize = 22.sp
            )
            uiState.isDraw -> Text(
                text = stringResource(R.string.draw_status),
                color = UiConstants.TextOrange,
                fontSize = 22.sp
            )
            else -> Text(
                text = stringResource(R.string.next_player, uiState.currentPlayer.name),
                color = UiConstants.TextGray,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { viewModel.resetGame() },
            enabled = uiState.isResetEnabled
        ) {
            Text(stringResource(R.string.restart))
        }
    }
}
