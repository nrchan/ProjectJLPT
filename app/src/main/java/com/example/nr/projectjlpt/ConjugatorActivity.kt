package com.example.nr.projectjlpt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_conjugator.*
import kotlinx.android.synthetic.main.activity_individual_grammar_point.*
import kotlinx.android.synthetic.main.activity_setting.*

class ConjugatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conjugator)

        verb_input_editText.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP) {
                if(motionEvent.rawX >= (verb_input_editText.right - verb_input_editText.compoundDrawables[2/*DRAWABLE_RIGHT*/].bounds.width())) {
                    val query = verb_input_editText.text.toString()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    verb_input_editText.clearFocus()

                    if(query.isNullOrBlank()) return@setOnTouchListener false
                    else search(query)
                    true
                }
            }
            false
        }

        verb_input_editText.setOnEditorActionListener { textView, i, keyEvent ->
            val query = verb_input_editText.text.toString()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            verb_input_editText.clearFocus()

            if(query.isNullOrBlank()) return@setOnEditorActionListener false
            else search(query)
            true
        }

        verb_input_editText.setOnFocusChangeListener { view, b ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun search(query : String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("verb")
        var myQuery : Query
        when {
            query.matches(Regex("^[ぁ-んァ-ヴｦ-ﾟ]+$")) ->
                myQuery = docRef.whereEqualTo("stem-kana", queriable(query, 0))
            query.matches(Regex("^[A-Za-z]+$")) ->
                myQuery = docRef.whereEqualTo("stem-romaji", queriable(query, 1))
            else ->
                myQuery = docRef.whereEqualTo("stem-kanji", queriable(query, 0))
        }

        myQuery.get()
            .addOnSuccessListener {
                if(it.isEmpty) {
                    Toast.makeText(applicationContext, "Sorry, I can't find the conjugation for $query", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                it.forEach {
                    Toast.makeText(
                        applicationContext,
                        it.getString("stem-kana"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Sorry, something went wrong...", Toast.LENGTH_SHORT).show()
            }
    }

    //turn the raw query into the form (stem-only form) stored in database
    private fun queriable(query : String, mode : Int) : String{
        var final = query
        when (mode) {
            0 -> if(query.endsWith("う") || query.endsWith("つ") || query.endsWith("る") ||
                query.endsWith("く") || query.endsWith("ぐ") || query.endsWith("ぬ") ||
                query.endsWith("ぶ") || query.endsWith("む") || query.endsWith("す")) {
                final = query.dropLast(1)
            }
            1 -> when {
                query.endsWith("tsu", true) -> {
                    final = query.dropLast(3)
                }
                query.endsWith("ru", true) || query.endsWith("ku", true) ||
                        query.endsWith("gu", true) || query.endsWith("nu", true) ||
                        query.endsWith("gu", true) || query.endsWith("mu", true) ||
                        query.endsWith("su", true) -> {
                    final = query.dropLast(2)
                }
                query.endsWith("u", true) -> {
                    final = query.dropLast(1)
                }
            }
        }
        return final
    }
}
