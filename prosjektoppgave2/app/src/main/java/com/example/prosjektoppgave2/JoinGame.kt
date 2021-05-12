package com.example.prosjektoppgave2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prosjektoppgave2.databinding.ActivityJoinGameBinding

class JoinGame : AppCompatActivity() {

    private lateinit var binding: ActivityJoinGameBinding
    private lateinit var pageSwap: Intent
    private lateinit var player2: String
    private lateinit var gameID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Redirects to the game
        binding.joinGameBtn2.setOnClickListener {
            player2 = binding.joinGamePlayerName.text.toString()
            gameID = binding.joinGameGameID.text.toString()
            GameManager.joinGame(player2, gameID)

            pageSwap = Intent(this, GameActivity::class.java)
            startActivity(pageSwap)
        }

        // Redirects back to main menu
        binding.joinGameBackBtn.setOnClickListener {
            pageSwap = Intent(this, MainActivity::class.java)
            startActivity(pageSwap)
        }
    }
}