package com.example.nr.projectjlpt

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlinx.android.synthetic.main.activity_grammar_list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.CoroutineContext

class GrammarListActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar_list)

        val grammardb = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "grammarpoint.db"
        ).build()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val isGrammarDownloaded = sharedPref.getBoolean("isGrammarDownloaded", false)

        if (!isGrammarDownloaded) {
            progressText.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            val db = FirebaseFirestore.getInstance()
            db.collection("grammar")
                .addSnapshotListener { querySnapShot, e ->
                    if (e != null) {
                        Log.d("FireStore", "Failed to fetch from cloud")
                    } else {
                        GlobalScope.launch(Dispatchers.IO) {
                            for (docs in querySnapShot!!.documentChanges) {
                                Log.d("Firestore", "Saving ${docs.document.getString("pattern")} to database.")
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
                            launch(Dispatchers.IO) {
                                Log.d("Coroutine", "linking adapter")
                                val grammars = ArrayList(grammardb.GrammarPointDao().getAll())
                                grammars.sortByDescending { it.level }
                                withContext(Dispatchers.Main) {
                                    grammar_list.layoutManager = LinearLayoutManager(this@GrammarListActivity)
                                    grammar_list.adapter = GrammarPointAdapter(grammars, this@GrammarListActivity)
                                    progressText.visibility = View.GONE
                                    progressBar.visibility = View.GONE
                                }
                            }
                        }
                        Log.d("Coroutine", "data fetched")
                        val editor = sharedPref.edit()
                        editor.putBoolean("isGrammarDownloaded", true)
                        editor.apply()
                    }
                }

        } else {
            launch(Dispatchers.IO) {
                Log.d("Coroutine", "linking adapter")
                val grammars = ArrayList(grammardb.GrammarPointDao().getAll())
                withContext(Dispatchers.Main) {
                    grammar_list.layoutManager = LinearLayoutManager(this@GrammarListActivity)
                    grammar_list.adapter = GrammarPointAdapter(grammars, this@GrammarListActivity)
                }
            }
            progressText.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }
}
