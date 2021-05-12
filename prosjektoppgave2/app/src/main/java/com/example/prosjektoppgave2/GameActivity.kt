package com.example.prosjektoppgave2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.prosjektoppgave2.api.data.Game
import com.example.prosjektoppgave2.api.data.GameState
import com.example.prosjektoppgave2.databinding.ActivityGameBinding
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private lateinit var binding:ActivityGameBinding
    private lateinit var player1:String
    private lateinit var player2:String
    private lateinit var gameState: GameState
    var player1turn: Boolean = true
    var player2turn: Boolean = false
    var player1sign = 'X'
    var player2sign = 'O'
    var player1win: Boolean = false
    var player2win: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val game: Game? = intent.getParcelableExtra("game")

        // Makes the game update the button texts
        game?.let {
            getCurrentPlayers(game)
        } ?.let {
            setUpdatedState(game.state)
        }

        // Checks if player 1 or player 2 has won and print their names if won
        gameStatus(gameState, player1sign, player2sign, player1, player2)
        buttonChecker(game)
    }

    // Sets the text on the buttons to X or O
    private fun setUpdatedState(state: GameState) {
        binding.b11.text = state[1][1].toString()
        binding.b12.text = state[1][2].toString()
        binding.b13.text = state[1][3].toString()
        binding.b21.text = state[2][1].toString()
        binding.b22.text = state[2][2].toString()
        binding.b23.text = state[2][3].toString()
        binding.b31.text = state[3][1].toString()
        binding.b32.text = state[3][2].toString()
        binding.b33.text = state[3][3].toString()
    }

    // Updates the button texts
    private fun buttonChecker(game: Game?){
        binding.b11.setOnClickListener {
            playerPicks(game, 1, 1)
        }
        binding.b12.setOnClickListener {
            playerPicks(game, 1, 2)
        }
        binding.b13.setOnClickListener {
            playerPicks(game, 1, 3)
        }
        binding.b21.setOnClickListener {
            playerPicks(game, 2, 1)
        }
        binding.b22.setOnClickListener {
            playerPicks(game, 2, 2)
        }
        binding.b23.setOnClickListener {
            playerPicks(game, 2, 3)
        }
        binding.b31.setOnClickListener {
            playerPicks(game, 3, 1)
        }
        binding.b32.setOnClickListener {
            playerPicks(game, 3, 2)
        }
        binding.b33.setOnClickListener {
            playerPicks(game, 3, 3)
        }
    }

    // Inserts the values to the button which the player picks
    private fun playerPicks(game: Game?, row: Int, column: Int) {
        // Checks if the player is player1. If so, puts his sign, 'X'
        if (player1turn) {
            // Checks if the game exists and that the current row is currently not taken
            if (game != null && game.state[row][column] == '0') {
                // Adds player1's sign to the given row and column
                game.state[row][column] = player1sign
                game.state.let {
                    GameManager.updateGame(game.gameId, it)
                }

                // Swaps player
                player1turn = false
                player2turn = true
            }
        }
        // Checks if the player is player2. If so, puts his sign, 'O'
        if (player2turn) {
            // Checks if the game exists and that the current row is currently not taken
            if (game != null && game.state[row][column] == '0') {
                // Adds player2's sign to the given row and column
                game.state[row][column] = player2sign
                game.state.let {
                    GameManager.updateGame(game.gameId, it)
                }
            }

            // Swaps player
            player2turn = false
            player1turn = true
        }
    }


    private fun gameStatus(state: GameState, player1sign: Char, player2sign: Char, player1: String, player2: String){

        // Checks if player 1 has won

        if (state[1][1] == player1sign && state[1][2] == player1sign && state[1][3] == player1sign){
            player1win = true
        }
        if (state[2][1] == player1sign && state[2][2] == player1sign && state[2][3] == player1sign){
            player1win = true
        }
        if (state[3][1] == player1sign && state[3][2] == player1sign && state[3][3] == player1sign){
            player1win = true
        }
        if (state[1][1] == player1sign && state[2][1] == player1sign && state[3][1] == player1sign){
            player1win = true
        }
        if (state[1][2] == player1sign && state[2][2] == player1sign && state[3][2] == player1sign){
            player1win = true
        }
        if (state[1][3] == player1sign && state[2][3] == player1sign && state[3][3] == player1sign){
            player1win = true
        }
        if (state[1][1] == player1sign && state[2][2] == player1sign && state[3][3] == player1sign){
            player1win = true
        }
        if (state[1][3] == player1sign && state[2][2] == player1sign && state[3][1] == player1sign){
            player1win = true
        }



        // Checks if player 2 has won

        if (state[1][2] == player2sign && state[1][2] == player2sign && state[1][3] == player2sign){
            player2win = true
        }
        if (state[2][1] == player2sign && state[2][2] == player2sign && state[2][3] == player2sign){
            player2win = true
        }
        if (state[3][1] == player2sign && state[3][2] == player2sign && state[3][3] == player2sign){
            player2win = true
        }
        if (state[1][1] == player2sign && state[2][1] == player2sign && state[3][1] == player2sign){
            player2win = true
        }
        if (state[1][2] == player2sign && state[2][2] == player2sign && state[3][2] == player2sign){
            player2win = true
        }
        if (state[1][3] == player2sign && state[2][3] == player2sign && state[3][3] == player2sign){
            player2win = true
        }
        if (state[1][1] == player2sign && state[2][2] == player2sign && state[3][3] == player2sign){
            player2win = true
        }
        if (state[1][3] == player2sign && state[2][2] == player2sign && state[3][1] == player2sign){
            player2win = true
        }


        if (player1win){
            Toast.makeText(this, "$player1 won!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
        if (player2win){
            Toast.makeText(this, "$player2 won!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getCurrentPlayers(game: Game){
        if (game.players.size < 2){
            println("Waiting for players to join")
        }
        // Sets first joined player as player1 and next as player2
        if (game.players.size == 2) {
            player1 = game.players[0]
            player2 = game.players[1]
        }
    }
}