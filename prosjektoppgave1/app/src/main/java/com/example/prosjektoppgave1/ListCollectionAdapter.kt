package com.example.prosjektoppgave1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prosjektoppgave1.databinding.ListeLayoutBinding
import java.util.*
import kotlin.collections.List as List

// Recycler Adapter
class ListCollectionAdapter(private var lister: List<Lister>, private val onListClicked:(Lister) -> Unit):RecyclerView.Adapter<ListCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding:ListeLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lister: Lister, onListClicked: (Lister) -> Unit) {
            binding.listNameButton.text = lister.listName
            binding.listNameButton.setOnClickListener {
                onListClicked(lister)
            }
        }
    }

    override fun getItemCount(): Int = lister.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val liste = lister[position]
        holder.bind(liste, onListClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun updateLists(newLists: List<Lister>){
        lister = newLists
        notifyDataSetChanged()
    }
}