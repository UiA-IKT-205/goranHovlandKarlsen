package com.example.prosjektoppgave2

import android.content.Intent
import android.util.Log
import com.example.prosjektoppgave2.App.Companion.context
import com.example.prosjektoppgave2.api.GameService
import com.example.prosjektoppgave2.api.GameServiceCallback
import com.example.prosjektoppgave2.api.data.Game
import com.example.prosjektoppgave2.api.data.GameState

typealias GameCallback = (game:Game?) -> Unit

object GameManager {

    var player: String? = null
    var state: GameState? = null
    val gameId: String? = null
    private lateinit var pageSwap:Intent

    // Sets the starting values to 0
    private val StartingGameState: GameState = listOf(
            mutableListOf('0', '0', '0'),
            mutableListOf('0', '0', '0'),
            mutableListOf('0', '0', '0')
    )


    fun createGame(player: String) {

        GameService.createGame(player, StartingGameState) { game: Game?, err: Int? ->
            if (err != null) {
                Log.e("createGameTag", "Something went wrong. Coult not create the game: $game")
            } else {
                println("$player created $game! Good luck have fun!")
                pageSwap = Intent(context, StartGame::class.java)
                pageSwap.putExtra("game", game)
                context.startActivity(pageSwap)
            }
        }
    }

    fun joinGame(player: String, gameId: String) {

        GameService.joinGame(player, gameId) { game: Game?, err: Int? ->
            if (err != null) {
                Log.e("joinGameTag", "Error occurred while trying to join the current game $gameId")
            } else {
                println("$player joined $game! Good luck, both!")
                pageSwap = Intent(context, JoinGame::class.java)
                pageSwap.putExtra("game", game)
                context.startActivity(pageSwap)
            }
        }
    }

    fun updateGame(gameId: String, state: GameState) {
        GameService.updateGame(gameId, state) { game: Game?, err: Int? ->
            if (err != null) {
                Log.e("updateGameTag", "Error occurred while trying to update the current game $gameId")
            }
        }
    }

    fun pollGame(gameId: String, callback: GameCallback) {
        GameService.pollGame(gameId){ game: Game?, err: Int? ->
            if(err != null){
                Log.e("pollGameTag", "Error polling game $gameId")
            }
        }
    }

}