package com.example.prosjektoppgave1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.prosjektoppgave1.databinding.ActivityAddListBinding
import com.example.prosjektoppgave1.databinding.ActivityMainBinding

class addList : AppCompatActivity() {

    private lateinit var binding: ActivityAddListBinding
    private lateinit var pageSwap: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addbutton.setOnClickListener {
            var adder = binding.addListTextBox.text.toString()
            Log.d("abcdefg", adder)

            if (adder.isNotEmpty()){
                addList(adder)

                val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

                pageSwap = Intent(this, MainActivity::class.java)
                startActivity(pageSwap)
            }
            else{
                Toast.makeText(this, "Listname can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun addList(listname:String){
        val liste = Lister(listname, true)
        ListeDepository.instance.addList(liste)
    }
}