package com.example.nr.projectjlpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            intent.getStringExtra("example")
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

    }
}
