package com.example.prosjektoppgave1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListeCollection(val listname:String):Parcelable {
    var lister:MutableList<Lister> = mutableListOf<Lister>()
        get() {return field}
        private set

    constructor(listname:String, lister:List<Lister>) : this(listname){
        this.lister = lister.toMutableList()
    }

    fun readCount():Int {
        return lister.count { it.read } ?: 0
    }

    fun addList(list: Lister){
        if (!lister.contains(lister)){
            lister.add(list)
        }
    }

    fun deleteList(list:Lister){
        lister.remove(list)
    }
}


@Parcelize
data class Lister(val listName: String, var read:Boolean):Parcelable