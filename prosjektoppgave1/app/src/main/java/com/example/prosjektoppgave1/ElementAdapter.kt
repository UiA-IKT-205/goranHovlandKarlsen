package com.example.prosjektoppgave1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prosjektoppgave1.databinding.ElementsLayoutBinding
import java.util.*

class ElementAdapter(private var elementer: List<Elementer>):RecyclerView.Adapter<ElementAdapter.ViewHolder>() {
        class ViewHolder(val binding: ElementsLayoutBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(elementer: Elementer) {
                binding.currentElement.text = elementer.elementName
            }
        }

    override fun getItemCount(): Int = elementer.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = elementer[position]
        holder.bind(element)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementAdapter.ViewHolder {
        return ElementAdapter.ViewHolder(
            ElementsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}