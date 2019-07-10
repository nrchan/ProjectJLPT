package com.example.nr.projectjlpt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Typeface

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grammar_list_activity_btn.setOnClickListener {
            startActivity(Intent(this, GrammarListActivity::class.java))
        }

        conjugation_activity_btn.setOnClickListener {
            //startActivity(Intent(this, ConjugationActivity::class.java))
        }

        setting_btn.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }
}
