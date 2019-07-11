package com.example.nr.projectjlpt

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@SettingActivity)
        val editor = sharedPref.edit()

        //filling in chosen choice
        when(sharedPref.getInt("level", -1)){
            0 -> levelNo.isChecked = true
            1 -> levelN1.isChecked = true
            2 -> levelN2.isChecked = true
            3 -> levelN3.isChecked = true
            4 -> levelN4.isChecked = true
            5 -> levelN5.isChecked = true
        }
        name_input_editText.setText(sharedPref.getString("name", null))
        address_spinner.setSelection(sharedPref.getInt("addressing_index", 0))

        //bunch of listener
        level_radio.setOnCheckedChangeListener { _, i ->
            when(findViewById<RadioButton>(i).text.toString()) {
                getString(R.string.level_N1) -> editor.putInt("level", 1)
                getString(R.string.level_N2) -> editor.putInt("level", 2)
                getString(R.string.level_N3) -> editor.putInt("level", 3)
                getString(R.string.level_N4) -> editor.putInt("level", 4)
                getString(R.string.level_N5) -> editor.putInt("level", 5)
                getString(R.string.level_No) -> editor.putInt("level", 0)
                else -> editor.putInt("level", -1)
            }
            editor.apply()
        }

        name_btn.setOnClickListener {
            val name = name_input_editText.text.toString()
            editor.putString("name", name)
            editor.putString("addressing", address_spinner.selectedItem.toString())
            editor.putInt("addressing_index", address_spinner.selectedItemPosition)
            editor.apply()
            Snackbar.make(
                name_btn,
                when(!name.isBlank()){
                    true -> R.string.name_snackbar
                    else -> R.string.name_not_set
                },
                Snackbar.LENGTH_LONG
            ).show()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        refreshGrammarBtn.setOnClickListener {
            val grammardb = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "grammarpoint.db"
            ).build()
            GlobalScope.launch {
                grammardb.GrammarPointDao().deleteAll()
                val db = FirebaseFirestore.getInstance()
                db.collection("grammar")
                    .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        if (firebaseFirestoreException != null) {
                            Log.d("FireStore", "Failed to fetch from cloud")
                        } else {
                            for (docs in querySnapshot!!.documentChanges) {
                                Log.d("Firestore", "Saving ${docs.document.getString("pattern")} to database.")
                                GlobalScope.launch(Dispatchers.IO) {
                                    grammardb.GrammarPointDao().insertAll(
                                        GrammarPoint(
                                            null,
                                            docs.document.getString("pattern"),
                                            docs.document.getString("kana"),
                                            docs.document.getString("romaji"),
                                            docs.document.getString("meaning"),
                                            docs.document.getField("level"),
                                            docs.document.getString("explanation"),
                                            docs.document.getString("usage"),
                                            docs.document.getString("example")
                                        )
                                    )
                                }
                            }
                            editor.putBoolean("isGrammarDownloaded", true)
                            editor.apply()
                            Snackbar.make(
                                refreshGrammarBtn,
                                R.string.grammar_downloaded_snackbar,
                                Snackbar.LENGTH_LONG
                            ).setAction(R.string.grammar_downloaded_snackbar_button_text)
                            {startActivity(Intent(this@SettingActivity, GrammarListActivity::class.java))}
                                .show()
                        }
                    }
            }
        }
    }
}
