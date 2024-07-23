package com.example.song

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RankingActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    private val first_name: TextView by lazy { findViewById<TextView>(R.id.first_name) }
    private val first_score: TextView by lazy { findViewById<TextView>(R.id.first_score) }
    private val second_name: TextView by lazy { findViewById<TextView>(R.id.second_name) }
    private val second_score: TextView by lazy { findViewById<TextView>(R.id.second_score) }
    private val third_name: TextView by lazy { findViewById<TextView>(R.id.third_name) }
    private val third_score: TextView by lazy { findViewById<TextView>(R.id.third_score) }
    private val forth_name: TextView by lazy { findViewById<TextView>(R.id.forth_name) }
    private val forth_score: TextView by lazy { findViewById<TextView>(R.id.forth_score) }
    private val fifth_name: TextView by lazy { findViewById<TextView>(R.id.fifth_name) }
    private val fifth_score: TextView by lazy { findViewById<TextView>(R.id.fifth_score) }

    private val rankList: List<List<TextView>> by lazy {
        listOf(
            listOf(first_name, first_score),
            listOf(second_name, second_score),
            listOf(third_name, third_score),
            listOf(forth_name, forth_score),
            listOf(fifth_name, fifth_score)
        )
    }

    private val my_name: TextView by lazy { findViewById<TextView>(R.id.my_name) }
    private val my_score: TextView by lazy { findViewById<TextView>(R.id.my_score) }

    private val next: TextView by lazy { findViewById<TextView>(R.id.next) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
    }

    override fun onStart() {
        super.onStart()

        if(intent.hasExtra("score")) {
            val score = intent.getStringExtra("score")!!
            my_score.setText("내 점수: ${score}점")
            my_name.setText("(${QuizList.name})")

            uploadRanking(QuizList.name, score)
            loadRanking()
        } else {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }

        next.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadRanking() {
        db.collection("rank").orderBy("score", Query.Direction.DESCENDING).limit(5).get().addOnSuccessListener { documents ->
            var idx: Int = 0
            for (document in documents) {
                val name = document.getString("name")
                val score = document.getLong("score")

                rankList[idx][0].setText("${idx+1}. ${name}")
                rankList[idx][1].setText("(${score}점)")
                idx++
            }
        }
    }

    private fun uploadRanking(name: String, score: String) {
        val user = hashMapOf(
            "name" to name,
            "score" to score.toLong()
        )
        db.collection("rank").add(user)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}