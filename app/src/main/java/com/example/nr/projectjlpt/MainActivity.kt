package com.example.nr.projectjlpt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Typeface
import androidx.preference.PreferenceManager
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity_title.text = greetingText()

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

    override fun onResume() {
        super.onResume()
        main_activity_title.text = greetingText()
    }

    private fun greetingText() : String {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPref.getString("name", null)
        val addressing = sharedPref.getString("addressing", null)
        val needPolite = when(addressing) {
            "くん", "ちゃん", "たん", "後輩" -> false
            else -> true
        }
        val time = Calendar.getInstance()
        val greeting = when(time.get(Calendar.HOUR_OF_DAY)){
            in 5..10 ->
                if(needPolite) getString(R.string.main_activity_title_morning_polite)
                else getString(R.string.main_activity_title_morning)
            in 11..17 -> getString(R.string.main_activity_title_afternoon)
            in 18..22 -> getString(R.string.main_activity_title_evening)
            else ->
                if(needPolite) getString(R.string.main_activity_title_welcome_back_polite)
                else getString(R.string.main_activity_title_welcome_back)
        }
        return when(!name.isNullOrBlank()){
            true -> name + addressing + "、" + System.getProperty("line.separator") + greeting
            else -> greeting
        }
    }
}
