package com.example.nr.projectjlpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_individual_grammar_point.*

class IndividualGrammarPointActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_grammar_point)

        val grammar = GrammarPoint(
            null,
            intent.getStringExtra("pattern"),
            intent.getStringExtra("kana"),
            intent.getStringExtra("romaji"),
            intent.getStringExtra("meaning"),
            intent.getIntExtra("level", 0),
            intent.getStringExtra("explanation"),
            intent.getStringExtra("usage"),
            intent.getStringExtra("example"),
            intent.getStringExtra("synonym"),
            intent.getStringExtra("antonym"),
            intent.getStringExtra("confusing")
        )

        patternText.text = grammar.pattern
        romajiText.text = grammar.romaji
        kanaText.text = if(!grammar.kana.isNullOrBlank()) {
            "(${grammar.kana})"
        } else {
            ""
        }
        meaningText.text = grammar.meaning
        usageText.text = grammar.printUsage()
        explanationText.text = grammar.printExplanation()
        exampleText.text = grammar.printExample()

        if(grammar.confusing.isNullOrBlank()){
            confusing.visibility = TextView.GONE
            confusingText.visibility = TextView.GONE
        }else{
            confusing.visibility = TextView.VISIBLE
            confusingText.visibility = TextView.VISIBLE
            confusingText.text = grammar.printConfusing()
        }

        if(grammar.synonym.isNullOrBlank()){
            synonym.visibility = TextView.GONE
            synonymText.visibility = TextView.GONE
        }else{
            synonym.visibility = TextView.VISIBLE
            synonymText.visibility = TextView.VISIBLE
            synonymText.text = grammar.printSynonym()
        }

        if(grammar.antonym.isNullOrBlank()){
            antonym.visibility = TextView.GONE
            antonymText.visibility = TextView.GONE
        }else{
            antonym.visibility = TextView.VISIBLE
            antonymText.visibility = TextView.VISIBLE
            antonymText.text = grammar.printAntonym()
        }

    }
}
