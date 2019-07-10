package com.example.nr.projectjlpt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                            val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@SettingActivity)
                            val editor = sharedPref.edit()
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
