package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // 一度だけ代入するものはvalを使う
    val handler = Handler()
    // 複数回代入するためvarを使う
    var timeValue = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 一秒ごとに処理を実行
        val runnable = object : Runnable {
            override fun run() {

                timeValue += 30L

                // TextViewを更新3
                // ?.letによりnull出ないときのみ更新
                timeToText(timeValue)?.let {
                    // timeToText(timeValue)の値はletないならitとして扱える
                    timeText.text = it
                }

                handler.postDelayed(this, 30)
            }
        }

        // 各ボタンごとの処理を実装
        // ちゃんと上から順番に並べないとだめみたい

        startButton.setOnClickListener {
            handler.post(runnable)
        }

        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            // timeToTextの引数はデフォルト値が設定されているので、引数省略できる
            timeToText()?.let {
                timeText.text = it
            }
        }

    }

    // 数値を00:00:00に整形する

    private fun timeToText(time: Long = 0L): String? {
        return if (time < 0L){
            null
        } else {
            val m = time / 1000 / 3600 / 60
            val s = time / 1000 % 60
            val ms = time % 1000
            "%1$02d:%2$02d.%3$02d".format(m, s, ms / 10)
        }

    }

}
