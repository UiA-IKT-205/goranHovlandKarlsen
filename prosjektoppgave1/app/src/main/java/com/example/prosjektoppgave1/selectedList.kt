package com.example.prosjektoppgave1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prosjektoppgave1.databinding.ActivitySelectedListBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_selected_list.*
import kotlinx.android.synthetic.main.elements_layout.*
import kotlinx.android.synthetic.main.elements_layout.view.*
import kotlinx.android.synthetic.main.liste_layout.*

class selectedList : AppCompatActivity() {

    private lateinit var binding:ActivitySelectedListBinding
    private lateinit var pageSwap: Intent
    private lateinit var list:Lister
    private lateinit var elements:MutableList<Elementer>
    var onElementer: ((List<Elementer>) -> Unit) ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectedListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.selectedListRecyclerView.adapter = ElementAdapter(emptyList<Elementer>())

        val getList = intent.getParcelableExtra<Lister>(EXTRA_LISTE_INFO)
        binding.currentListName.text = getList?.listName

        if (getList != null){
            list = getList
        } else{
            finish()
            return
        }

        progressBar()

        binding.deleteCurrentListButton.setOnClickListener{

            ListeDepository.instance.deleteList(list)

            pageSwap = Intent(this@selectedList, MainActivity::class.java)
            startActivity(pageSwap)
        }

        binding.addElementCurrentListButton.setOnClickListener {
            pageSwap = Intent(this, addElement::class.java)
            startActivity(pageSwap)
        }
    }

    fun progressBar(){
        var checked:Int = 0
        elements.forEach(){
            if (elementCheckBox.isChecked){
                checked += 1
            }
        }
        return currentListProgressBar.setProgress((checked/elements.size) * 100, true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // bruke setResult for å sende ting tilbake
        finish()
    }

    fun addObject(item:String){
        elements.add(Elementer(item))
        onElementer?.invoke(elements)
    }

    companion object {
        val instance = selectedList()
    }
}