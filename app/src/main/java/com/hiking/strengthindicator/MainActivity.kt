package com.hiking.strengthindicator

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchButton.setOnClickListener {
            strengthIndicatorView.apply {
                stateIndex = (stateIndex + 1) % states.size
            }
        }
        // runLayoutDemo()
    }

    private fun runLayoutDemo() {
        strengthIndicatorView.post {
            ValueAnimator.ofInt(strengthIndicatorView.width, 0).apply {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 5000
                addUpdateListener {
                    strengthIndicatorView.layoutParams =
                            strengthIndicatorView.layoutParams.apply {
                                width = it.animatedValue as Int
                            }
                }
            }.start()
        }
    }
}
