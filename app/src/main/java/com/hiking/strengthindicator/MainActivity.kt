package com.hiking.strengthindicator

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var layoutWidth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun populateViewWidth(progress: Int) {
            strengthIndicatorView.layoutParams =
                    strengthIndicatorView.layoutParams.apply {
                        width = layoutWidth * progress / 100
                    }
        }
        widthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                widthTextView.text = progress.toString()
                if (layoutWidth > 0) populateViewWidth(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        strengthIndicatorView.post {
            layoutWidth = strengthIndicatorView.width
            populateViewWidth(widthSeekBar.progress)
        }

        textSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                textSizeTextView.text = progress.toString()
                strengthIndicatorView.textSize = progress * resources.displayMetrics.scaledDensity
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        barHeightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                barHeightTextView.text = progress.toString()
                strengthIndicatorView.barHeight = progress * resources.displayMetrics.density
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        barGapSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                barGapTextView.text = progress.toString()
                strengthIndicatorView.barGap = progress * resources.displayMetrics.density
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        textBarGapSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                textBarGapTextView.text = progress.toString()
                strengthIndicatorView.textToBarGap = progress * resources.displayMetrics.density
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        stateSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                stateTextView.text = progress.toString()
                strengthIndicatorView.stateIndex = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        stateSeekBar.apply {
            max = strengthIndicatorView.states.size - 1
        }
    }
}
