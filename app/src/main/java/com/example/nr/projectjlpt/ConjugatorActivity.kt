package com.example.nr.projectjlpt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_conjugator.*
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

            if(query.isNullOrBlank()) return@setOnEditorActionListener false
            else search(query)
            true
        }
    }

    private fun search(query : String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("verb").document(query)
        docRef.get()
        Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
    }
}
