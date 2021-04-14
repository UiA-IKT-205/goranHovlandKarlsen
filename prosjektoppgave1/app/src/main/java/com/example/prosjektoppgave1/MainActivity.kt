package com.example.prosjektoppgave1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prosjektoppgave1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.liste_layout.view.*
import java.io.File
import java.io.FileOutputStream


const val EXTRA_LISTE_INFO: String = "com.example.prosjektoppgave1.selectedList.info"

class ListHolder{
    companion object{
        var clickedList:Lister? = null
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var pageSwap:Intent
    private lateinit var auth:FirebaseAuth
    var onSave:((file: Uri) -> Unit) ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.listeRecyclerView.adapter = ListCollectionAdapter(emptyList<Lister>(), this::onListClicked)

        auth = Firebase.auth
        signInAnonymously()

        ListeDepository.instance.onLister = {
            (binding.listeRecyclerView.adapter as ListCollectionAdapter).updateLists(it)
        }

        ListeDepository.instance.load()

        binding.addNewListButton.setOnClickListener{
            var adder = binding.addListTextBox.text.toString()
            Log.d("abcdefg", adder)

            if (adder.isNotEmpty()){
                addList(adder)
                ListeDepository.instance.onSave = {
                    this.uploadList(it)
                }
                binding.addListTextBox.setText("")
            }
            else{
                Toast.makeText(this, "Listname can't be empty", Toast.LENGTH_SHORT).show()
            }

            val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun onListClicked(lister: Lister):Unit {
        ListHolder.clickedList = lister
        pageSwap = Intent(this, selectedList::class.java).apply {
            putExtra(EXTRA_LISTE_INFO, lister)
        }
        startActivity(pageSwap)
    }

    private fun addList(listname:String){
        val liste = Lister(listname, true)
        ListeDepository.instance.addList(liste)
    }

    private fun signInAnonymously(){
        auth.signInAnonymously().addOnSuccessListener {
            Log.d("LoginListener", "Login success ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e("LoginListener", "Login failed", it)
        }
    }

    private fun uploadList(list: Uri){
        Log.d("uploadList", "Uploaded $list")

        val ref = FirebaseStorage.getInstance().reference.child("lists/${list.lastPathSegment}")
        var upload = ref.putFile(list)

        upload.addOnSuccessListener {
            Log.d("uploadList", "Saved list $list")
        }.addOnFailureListener{
            Log.e("uploadList", "An error occourd, could not save the list", it)
        }
    }
    companion object {
        val instance = MainActivity()
    }
}