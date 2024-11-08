package com.example.song

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnswerActivity : AppCompatActivity() {
    private var idx: Int = -1
    private var score: Int = 0
    private val quizList = QuizList.list
    private var quizAnswerList = mutableListOf<Int>() // o: 0, x: 1, notknow: 2
    private var quizRandomList = mutableListOf<Int>()
    private val answerState = arrayOf("O", "X", "모르겠어요")

    private val index: TextView by lazy { findViewById<TextView>(R.id.index) }
    private val content: TextView by lazy { findViewById<TextView>(R.id.content) }
    private val img: ImageView by lazy { findViewById<ImageView>(R.id.img) }

    private val answer: TextView by lazy { findViewById<TextView>(R.id.answer) }
    private val myAnswer: TextView by lazy { findViewById<TextView>(R.id.my_answer) }
    private val comment: TextView by lazy { findViewById<TextView>(R.id.comment) }
    private val next: TextView by lazy { findViewById<TextView>(R.id.next) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
    }

    override fun onStart() {
        super.onStart()
        if(intent.hasExtra("answer") && intent.hasExtra("random")) {
            val quiz = intent.getStringExtra("answer") ?: "[2, 2, 2, 2, 2, 2, 2, 2, 2, 2]"
            val trimmedStringQuiz = quiz.replace("[", "").replace("]", "")

            // Step 2: Split the string by ", " to get individual number strings
            val numberStringsQuiz = trimmedStringQuiz.split(", ")

            // Step 3: Convert each number string to integer and add to a list
            val numbersQuiz = numberStringsQuiz.map { it.toInt() }

            val random = intent.getStringExtra("random") ?: "[2, 2, 2, 2, 2, 2, 2, 2, 2, 2]"
            val trimmedStringRandom = random.replace("[", "").replace("]", "")

            // Step 2: Split the string by ", " to get individual number strings
            val numberStringsRandom = trimmedStringRandom.split(", ")

            // Step 3: Convert each number string to integer and add to a list
            val numbersRandom = numberStringsRandom.map { it.toInt() }

            quizAnswerList.addAll(numbersQuiz)
            quizRandomList.addAll(numbersRandom)
        } else {
            val intent = Intent(this, QuizActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }

        nextQuiz()

        next.setOnClickListener() {
            nextQuiz()
        }
    }

    private fun nextQuiz() {
        if(idx < 9) {
            idx++
            index.setText((idx+1).toString().padStart(2, '0'))
            content.setText(quizList[quizRandomList[idx]].content)
            img.setImageResource(quizList[quizRandomList[idx]].photo)
            answer.setText("정답: ${answerState[quizList[quizRandomList[idx]].answer]}")
            myAnswer.setText("내가 고른 답: ${answerState[quizAnswerList[idx]]}")
            comment.setText(quizList[quizRandomList[idx]].comment)
            if(quizList[quizRandomList[idx]].answer == quizAnswerList[idx]) score += 100
        } else {
            val intent = Intent(this, RankingActivity::class.java)
            intent.putExtra("score", score.toString())
            finishAffinity()
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}