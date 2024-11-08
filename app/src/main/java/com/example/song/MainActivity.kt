package com.example.song

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val nickname: EditText by lazy { findViewById<EditText>(R.id.nickname) }
    private val start: TextView by lazy { findViewById<TextView>(R.id.start) }
    private val bg: LinearLayout by lazy { findViewById<LinearLayout>(R.id.bg) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        bg.setOnClickListener() {
            imm.hideSoftInputFromWindow(nickname.getWindowToken(), 0)
        }
        start.setOnClickListener() {
            setNext()
        }
        nickname.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                setNext()
                true
            } else {
                false
            }
        }
    }

    private fun setNext() {
        QuizList.name = nickname.text.toString()

        val intent = Intent(this, QuizActivity::class.java)
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(nickname.getWindowToken(), 0)
        finishAffinity()
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}