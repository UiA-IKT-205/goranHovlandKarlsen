package com.example.prosjektoppgave1

import android.app.DownloadManager
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ListeDepository {

    private lateinit var listCollection: ListeCollection
    //private lateinit var listCollection: MutableList<Lister>

    var onSave:((file: Uri) -> Unit) ?= null
    var onLister: ((List<Lister>) -> Unit) ?= null
    var onListUpdate: ((lister: Lister) -> Unit) ?= null
    val lister: ListeCollection
        get() = this.listCollection


    fun load(){
        val ref = FirebaseStorage.getInstance().reference.child("lists")
        val temp = File.createTempFile("lists", "list")
        var download = ref.getFile(temp)
        download.addOnSuccessListener {
            Log.d("downloadList", "Lists downloaded successfully")
        }.addOnFailureListener{
            Log.e("downloadList", "An error occourd, could not fetch the lists", it)
        }
        onLister?.invoke(listCollection.lister)
    }

    fun addList(lister:Lister){
        //listCollection.add(lister)
        //onLister?.invoke(listCollection)
        listCollection.addList(lister)
        onLister?.invoke(listCollection.lister)
    }

    fun deleteList(lister:Lister){
        listCollection.deleteList(lister)
        onLister?.invoke(listCollection.lister)
    }

    fun saveList(listname: String, content:String){
        val path = MainActivity.instance.getExternalFilesDir(null)
        if (path != null){
            val list = File(path, listname)
            FileOutputStream(list, true).bufferedWriter().use { writer ->
                writer.write(content)
            }
            this.onSave?.invoke(list.toUri())
        }
    }

    companion object{
        val instance = ListeDepository()
    }
}