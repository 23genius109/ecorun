package com.example.song

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt
import java.util.Random

class QuizActivity : AppCompatActivity() {
    private var idx: Int = -1
    private val quizList = QuizList.list
    private var quizAnswerList = mutableListOf<Int>() // o: 0, x: 1, notknow: 2
    private var quizRandomList = mutableListOf<Int>()

    private val second: TextView by lazy { findViewById<TextView>(R.id.second) }
    private val index: TextView by lazy { findViewById<TextView>(R.id.index) }
    private val content: TextView by lazy { findViewById<TextView>(R.id.content) }
    private val img: ImageView by lazy { findViewById<ImageView>(R.id.img) }

    private val o: ImageView by lazy { findViewById<ImageView>(R.id.o) }
    private val x: ImageView by lazy { findViewById<ImageView>(R.id.x) }
    private val notknow: TextView by lazy { findViewById<TextView>(R.id.notknow) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
    }

    override fun onStart() {
        super.onStart()

        nextQuiz()

        o.setOnClickListener() {
            setAnswer(0)
        }
        x.setOnClickListener() {
            setAnswer(1)
        }
        notknow.setOnClickListener() {
            setAnswer(2)
        }
    }

    private val mCountDown: CountDownTimer = object : CountDownTimer(15000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val count = millisUntilFinished.toFloat() / 1000.0f
            second.setText(count.roundToInt().toString())
        }

        override fun onFinish() {
            quizAnswerList.add(2)
            nextQuiz()
        }
    }

    private fun nextQuiz() {
        if(idx < 9) {
            idx++
            setRandom()
            index.setText((idx+1).toString().padStart(2, '0'))
            content.setText(quizList[quizRandomList[idx]].content)
            img.setImageResource(quizList[quizRandomList[idx]].photo)
            mCountDown.start()
        } else {
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("answer", quizAnswerList.toString())
            intent.putExtra("random", quizRandomList.toString())
            startActivity(intent)
        }
    }

    private fun setAnswer(select: Int) {
        quizAnswerList.add(select)
        mCountDown.cancel()
        nextQuiz()
    }

    private fun setRandom() {
        val random = Random()
        val num = random.nextInt(50)
        if(quizRandomList.contains(num)) {
            setRandom()
        } else {
            quizRandomList.add(num)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}