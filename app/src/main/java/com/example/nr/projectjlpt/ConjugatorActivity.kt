package com.example.nr.projectjlpt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
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

        conjugation_result_view.visibility = View.GONE

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
        conjugation_result_view.apply {
            animate().alpha(0f)
            visibility = View.GONE
        }
        progressBar.apply {
            visibility = View.VISIBLE
            animate().alpha(1f)
        }
        progressText.apply {
            text = getString(R.string.conjugator_activity_progressText_search)
            visibility = View.VISIBLE
            animate().alpha(1f)
        }

        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("verb")
        var myQuery = when {
            queriable(query, 0).matches(Regex("^[ぁ-んァ-ヴｦ-ﾟ]+$")) ->
                docRef.whereEqualTo("stem-kana", queriable(query, 0))
            queriable(query, 1).matches(Regex("^[A-Za-z]+$")) ->
                docRef.whereEqualTo("stem-romaji", queriable(query, 1))
            else ->
                docRef.whereEqualTo("stem-kanji", queriable(query, 0))
        }

        myQuery.get()
            .addOnSuccessListener {
                if(it.isEmpty) {
                    Toast.makeText(applicationContext, getString(R.string.conjugation_not_found, query), Toast.LENGTH_SHORT).show()
                    progressBar.apply {
                        animate().alpha(0f)
                        visibility = View.GONE
                    }
                    progressText.apply {
                        animate().alpha(0f)
                        visibility = View.GONE
                    }
                    return@addOnSuccessListener
                }

                //populate the conjugation_result_view
                val verb = Verb(
                    it.elementAt(0).getString("stem-kanji"),
                    it.elementAt(0).getString("stem-kana"),
                    it.elementAt(0).getString("stem-romaji"),
                    it.elementAt(0).getString("meaning"),
                    it.elementAt(0).getString("group")
                )
                var stemJa : String? = verb.stemKanji
                if (stemJa.isNullOrBlank()) stemJa = verb.stemKana
                var stemRomaji : String? = verb.stemRomaji

                myVerb.text = getString(R.string.conjugator_activity_myVerb, stemJa, verb.dict())
                if(verb.group == "くる") myVerb.text = verb.dict()
                myRomaji.text = getString(R.string.conjugator_activity_myRomaji, verb.stemRomaji, verb.dictRomaji())
                if(verb.group == "くる") myRomaji.text = verb.dictRomaji()
                myMeaning.text = getString(R.string.conjugator_activity_myMeaning, verb.meaning)
                myGroup.text = when(verb.group) {
                    "う" -> getString(R.string.conjugator_activity_myGroup_group1, "u")
                    "つ" -> getString(R.string.conjugator_activity_myGroup_group1, "tsu")
                    "る" -> getString(R.string.conjugator_activity_myGroup_group1, "ru")
                    "く" -> getString(R.string.conjugator_activity_myGroup_group1, "ku")
                    "ぐ" -> getString(R.string.conjugator_activity_myGroup_group1, "gu")
                    "ぬ" -> getString(R.string.conjugator_activity_myGroup_group1, "nu")
                    "ぶ" -> getString(R.string.conjugator_activity_myGroup_group1, "bu")
                    "む" -> getString(R.string.conjugator_activity_myGroup_group1, "mu")
                    "す" -> getString(R.string.conjugator_activity_myGroup_group1, "su")
                    "いる" -> getString(R.string.conjugator_activity_myGroup_group2, "iru")
                    "える" -> getString(R.string.conjugator_activity_myGroup_group2, "eru")
                    "する" -> getString(R.string.conjugator_activity_myGroup_group3, "suru")
                    "くる" -> getString(R.string.conjugator_activity_myGroup_group3, "kuru")
                    else -> ""
                }

                present_plain_a.text = verb.presentPlainA()
                present_plain_n.text = verb.presentPlainN()
                present_polite_a.text = verb.presentPoliteA()
                present_polite_n.text = verb.presentPoliteN()
                past_plain_a.text = verb.pastPlainA()
                past_plain_n.text = verb.pastPlainN()
                past_polite_a.text = verb.pastPoliteA()
                past_polite_n.text = verb.pastPoliteN()

                dict_stemJa.text = verb.JA()
                dict_conjugation.text = verb.dict()
                dict_stemRomaji.text = verb.RO()
                dict_conjugationRomaji.text = verb.dictRomaji()

                neg_stemJa.text = verb.JA()
                neg_conjugation.text = verb.neg()
                neg_stemRomaji.text = verb.RO()
                neg_conjugationRomaji.text = verb.negRomaji()

                masu_stemJa.text = verb.JA()
                masu_conjugation.text = verb.masu()
                masu_stemRomaji.text = verb.RO()
                masu_conjugationRomaji.text = verb.masuRomaji()

                te_stemJa.text = verb.JA()
                te_conjugation.text = verb.te()
                te_stemRomaji.text = verb.RO()
                te_conjugationRomaji.text = verb.teRomaji()

                ta_stemJa.text = verb.JA()
                ta_conjugation.text = verb.ta()
                ta_stemRomaji.text = verb.RO()
                ta_conjugationRomaji.text = verb.taRomaji()

                pot_stemJa.text = verb.JA()
                pot_conjugation.text = verb.pot()
                pot_stemRomaji.text = verb.RO()
                pot_conjugationRomaji.text = verb.potRomaji()

                con_stemJa.text = verb.JA()
                con_conjugation.text = verb.con()
                con_stemRomaji.text = verb.RO()
                con_conjugationRomaji.text = verb.conRomaji()

                vol_stemJa.text = verb.JA()
                vol_conjugation.text = verb.vol()
                vol_stemRomaji.text = verb.RO()
                vol_conjugationRomaji.text = verb.volRomaji()

                proh_stemJa.text = verb.JA()
                proh_conjugation.text = verb.proh()
                proh_stemRomaji.text = verb.RO()
                proh_conjugationRomaji.text = verb.prohRomaji()

                imp_stemJa.text = verb.JA()
                imp_conjugation.text = verb.imp()
                imp_stemRomaji.text = verb.RO()
                imp_conjugationRomaji.text = verb.impRomaji()

                cau_stemJa.text = verb.JA()
                cau_conjugation.text = verb.cau()
                cau_stemRomaji.text = verb.RO()
                cau_conjugationRomaji.text = verb.cauRomaji()

                pas_stemJa.text = verb.JA()
                pas_conjugation.text = verb.pas()
                pas_stemRomaji.text = verb.RO()
                pas_conjugationRomaji.text = verb.pasRomaji()

                caupas_stemJa.text = verb.JA()
                caupas_conjugation.text = verb.caupas()
                caupas_stemRomaji.text = verb.RO()
                caupas_conjugationRomaji.text = verb.caupasRomaji()

                conjugation_result_view.apply {
                    alpha = 0f
                    scrollTo(0,0)
                    visibility = View.VISIBLE
                    animate().alpha(1f)
                }
                progressBar.apply {
                    animate().alpha(0f)
                    visibility = View.GONE
                }
                progressText.apply {
                    animate().alpha(0f)
                    visibility = View.GONE
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, R.string.conjugation_error, Toast.LENGTH_SHORT).show()
                progressBar.apply {
                    animate().alpha(0f)
                    visibility = View.GONE
                }
                progressText.apply {
                    animate().alpha(0f)
                    visibility = View.GONE
                }
            }
    }

    //turn the raw query into the form (stem-only form) stored in database
    private fun queriable(query : String, mode : Int) : String{
        var final = query.replace(" ", "").replace("　","")
        when (mode) {
            0 -> if(final.endsWith("う") || final.endsWith("つ") || final.endsWith("る") ||
                final.endsWith("く") || final.endsWith("ぐ") || final.endsWith("ぬ") ||
                final.endsWith("ぶ") || final.endsWith("む") || final.endsWith("す")) {
                final = final.dropLast(1)
            }
            1 -> when {
                final.endsWith("tsu", true) -> {
                    final = final.dropLast(3)
                }
                final.endsWith("ru", true) || final.endsWith("ku", true) ||
                        final.endsWith("gu", true) || final.endsWith("nu", true) ||
                        final.endsWith("gu", true) || final.endsWith("mu", true) ||
                        final.endsWith("su", true) -> {
                    final = final.dropLast(2)
                }
                final.endsWith("u", true) -> {
                    final = final.dropLast(1)
                }
            }
        }
        return final
    }
}
