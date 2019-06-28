package com.example.nr.projectjlpt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grammar_list_activity_btn.setOnClickListener {
            startActivity(Intent(this, GrammarListActivity::class.java))
        }
    }
}
