package com.example.nr.projectjlpt

import android.app.Activity
import android.app.TaskStackBuilder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(){
        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            when (preference?.key) {
                "isGrammarDownloaded" -> {
                    Log.d("preference","${preference?.key}")
                    GlobalScope.launch(Dispatchers.IO) {
                        val grammardb = Room.databaseBuilder(
                            activity!!.applicationContext,
                            AppDatabase::class.java, "grammarpoint.db"
                        ).build()
                        grammardb.GrammarPointDao().deleteAll()
                        launch(Dispatchers.Main) {
                            Toast.makeText(activity, "All Downloaded Grammars Refreshed!", Toast.LENGTH_LONG).show()
                            preference.isEnabled = false
                            preference.summary = getString(R.string.grammar_downloaded_summary_not_enabled)
                        }
                        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
                        val editor = sharedPref.edit()
                        editor.putBoolean("isGrammarDownloaded", false)
                        editor.apply()
                    }
                }
            }

            return super.onPreferenceTreeClick(preference)
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}