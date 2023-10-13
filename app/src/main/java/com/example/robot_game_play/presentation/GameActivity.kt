package com.example.robot_game_play.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.robot_game_play.R
import com.example.robot_game_play.domain.models.GameState
import com.example.robot_game_play.domain.models.Player
import com.example.robot_game_play.presentation.theme.FoodColor
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameActivity : BaseActivity() {

    private val viewModel: GameViewModel by viewModel()

    @Composable
    override fun Content() {
        Column {
            val state by viewModel.gameState.collectAsState()
            val boardSize = viewModel.getBoardSize()
            state?.let { GameScreen(it, boardSize) }
        }
    }

    @Composable
    fun GameScreen(state: GameState, boardSize: Int) {
        Column(
            modifier = Modifier.padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.let { gameState ->
                BoxWithConstraints(Modifier.padding(16.dp)) {
                    GameStats()
                }
                Board(gameState, boardSize)
                BoxWithConstraints(Modifier.padding(16.dp)) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(gameState.playerList) {
                            Scores(it, gameState.currentPlayerTurn)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Board(state: GameState, boardSize: Int) {
        BoxWithConstraints(Modifier.padding(16.dp)) {
            val tileSize = maxWidth / boardSize
            Box(
                Modifier
                    .size(maxWidth)
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                    .zIndex(1f)
            )
            Box(
                Modifier
                    .offset(
                        x = tileSize * state.foodCoord.first,
                        y = tileSize * state.foodCoord.second
                    )
                    .size(tileSize)
                    .background(
                        FoodColor, RoundedCornerShape(8.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apple_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(4.dp))
            }
            state.playerList.forEach { player ->
                val playerHead = player.body.first()
                player.body.forEach {
                    Box(
                        modifier = Modifier
                            .offset(x = tileSize * it.first, y = tileSize * it.second)
                            .size(tileSize - 3.dp)
                            .background(
                                player.bodyColor, CircleShape
                            )
                    )
                }
                Box(
                    modifier = Modifier
                        .offset(x = tileSize * playerHead.first, y = tileSize * playerHead.second)
                        .size(tileSize - 2.dp)
                        .background(
                            player.headColor, CircleShape
                        )
                )
            }
        }
    }

    @Composable
    fun Scores(player: Player, currentTurn: Int){
        Row(Modifier.padding(4.dp)) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .padding(2.dp)
                    .background(
                        player.headColor, CircleShape
                    )
            )
            var text = " Score: ${player.score}"
            if (currentTurn == player.number) text += "  <<"
            Text(text = text, color = player.headColor, fontWeight = FontWeight.Bold)
        }
    }

    @Composable
    fun GameStats() {
        Column {
            Row {
                TitleLarge(text = "\uD83E\uDD16 Robot Challenge")
            }
        }
    }
    
    @Composable
    fun TitleLarge(modifier: Modifier = Modifier, text: String, textAlign: TextAlign = TextAlign.Start) {
        Text(
            modifier = modifier,
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = textAlign
        )
    }
}