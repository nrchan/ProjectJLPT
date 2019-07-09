package com.example.nr.projectjlpt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.grammar_list_item.view.*

class GrammarPointAdapter(private val grammars: ArrayList<GrammarPoint>, private val context: Activity) : RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.grammar_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return grammars.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pattern = grammars[position].pattern + " / "
        holder.grammarPattern.text = pattern
        holder.grammarMeaning.text = grammars[position].meaning
        holder.grammarRomaji.text = grammars[position].romaji
        holder.itemView.setOnClickListener {
            val intent = Intent(context, IndividualGrammarPointActivity::class.java)
            intent.putExtra("pattern", grammars[position].pattern)
            intent.putExtra("kana", grammars[position].kana)
            intent.putExtra("romaji", grammars[position].romaji)
            intent.putExtra("meaning", grammars[position].meaning)
            intent.putExtra("level", grammars[position].level)
            intent.putExtra("explanation", grammars[position].explanation)
            intent.putExtra("usage", grammars[position].usage)
            intent.putExtra("example", grammars[position].example)
            context.startActivity(intent)
        }
    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val grammarPattern : TextView = view.grammar_pattern
    val grammarMeaning : TextView = view.grammar_meaning
    val grammarRomaji : TextView = view.grammar_romaji
}