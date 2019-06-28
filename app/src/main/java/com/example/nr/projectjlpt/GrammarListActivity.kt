package com.example.nr.projectjlpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlinx.android.synthetic.main.activity_grammar_list.*

class GrammarListActivity : AppCompatActivity() {

    private val grammars = ArrayList<GrammarPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar_list)

        val db = FirebaseFirestore.getInstance()
        db.collection("grammar")
            .addSnapshotListener {querySnapShot, e->
                if(e != null){
                    Log.d("FireStore", "Failed to fetch from cloud")
                } else {
                    for (docs in querySnapShot!!.documentChanges) {
                        if(docs.type == DocumentChange.Type.ADDED) {
                            grammars.add(
                                GrammarPoint(
                                    docs.document.getString("pattern"),
                                    docs.document.getString("kana"),
                                    docs.document.getString("romaji"),
                                    docs.document.getString("meaning"),
                                    docs.document.getField("level"),
                                    docs.document.getString("explanation"),
                                    docs.document.getString("usage")
                                )
                            )
                        }
                    }
                }

                grammar_list.layoutManager = LinearLayoutManager(this)
                grammar_list.adapter = GrammarPointAdapter(grammars, this)
            }
    }
}
