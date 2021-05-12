package com.example.prosjektoppgave2

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.prosjektoppgave2.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_join_game.*
import kotlinx.android.synthetic.main.activity_start_game.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var pageSwap: Intent
    val player1 = playerNameTextView.text.toString()
    val player2 = joinGamePlayerName.text.toString()
    val gameId = joinGameGameID.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGameBtn.setOnClickListener {
            createGame()
        }

        binding.joinGameBtn.setOnClickListener {
            joinGame()
        }

    }

    private fun createGame(){
        GameManager.createGame(player1)
    }

    private fun joinGame(){
        GameManager.joinGame(player2, gameId)
    }


}