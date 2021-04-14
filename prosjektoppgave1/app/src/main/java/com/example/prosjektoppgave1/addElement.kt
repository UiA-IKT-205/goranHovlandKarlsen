package com.example.prosjektoppgave1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.prosjektoppgave1.databinding.ActivityAddElementBinding
import com.example.prosjektoppgave1.databinding.ActivitySelectedListBinding

class addElement : AppCompatActivity() {

    private lateinit var binding:ActivityAddElementBinding
    private lateinit var pageSwap: Intent
    //private lateinit var elements:MutableList<Elementer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddElementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addElementBtn.setOnClickListener {
            val item = binding.addElementString.text.toString()
            if (item.isNotEmpty()){
                selectedList.instance.addObject(item)
                pageSwap = Intent(this, selectedList::class.java)
                startActivity(pageSwap)
            }
            else {
                Toast.makeText(this, "Items can't be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}