package com.example.prosjektoppgave2

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prosjektoppgave2.api.GameService
import com.example.prosjektoppgave2.api.data.Game
import com.example.prosjektoppgave2.databinding.ActivityStartGameBinding
import java.util.*


class StartGame : AppCompatActivity() {

    private lateinit var binding: ActivityStartGameBinding
    private lateinit var pageSwap: Intent
    private lateinit var player1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Redirects to game
        binding.createGameBtn.setOnClickListener {
            player1 = binding.playerNameTextView.text.toString()
            GameManager.createGame(player1)
            pageSwap = Intent(this, GameActivity::class.java)
            startActivity(pageSwap)
        }

        // Redirects back to main menu
        binding.createGameBackBtn.setOnClickListener {
            pageSwap = Intent(this, MainActivity::class.java)
            startActivity(pageSwap)
        }
    }
}