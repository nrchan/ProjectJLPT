package com.example.nr.projectjlpt

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.grammar_list_item.view.*

class GrammarPointAdapter(private val grammars: ArrayList<GrammarPoint>, private val context: Context) : RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("GrammarPointAdapter", "In onCreateViewHolder")
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.grammar_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return grammars.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.grammarPattern.text = grammars[position].pattern
        holder.grammarRomaji.text = grammars[position].romaji
    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val grammarPattern : TextView = view.grammar_pattern
    val grammarRomaji : TextView = view.grammar_romaji
}