package com.example.temperatureconverter

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var celsiusSeekBar: SeekBar
    private lateinit var fahrenheitSeekBar: SeekBar
    private lateinit var interestingMessageTextView: TextView
    private lateinit var currentCelsiusTextView: TextView
    private lateinit var currentFahrenheitTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        celsiusSeekBar = findViewById(R.id.celsiusSeekBar)
        fahrenheitSeekBar = findViewById(R.id.fahrenheitSeekBar)
        interestingMessageTextView = findViewById(R.id.interestingMessageTextView)
        currentCelsiusTextView = findViewById(R.id.currentCelsiusTextView)
        currentFahrenheitTextView = findViewById(R.id.currentFahrenheitTextView)

        celsiusSeekBar.max = 100
        fahrenheitSeekBar.max = 212
        /*Used answers from https://stackoverflow.com/questions/8956218/android-seekbar-setonseekbarchangelistener,
        https://stackoverflow.com/questions/70759500/how-to-get-value-of-seekbar-and-use-it-on-another-class-or-method,
        as well as https://developer.android.com/reference/android/widget/SeekBar
        for understanding seekbar's setOnSeekBarChangeListener *
           */
        celsiusSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val fahrenheit = progress * 9/5 + 32
                    fahrenheitSeekBar.progress = fahrenheit
                    updateTemperatures(progress, fahrenheit)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    fahrenheitSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val celsius = (progress - 32) * 5/9
                    celsiusSeekBar.progress = celsius
                    updateTemperatures(celsius, progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateTemperatures(celsius: Int, fahrenheit: Int) {
        currentCelsiusTextView.text = "${celsius}°C"
        currentFahrenheitTextView.text = "${fahrenheit}°F"
        interestingMessageTextView.text = if (celsius < 20) {
            "I wish it were warmer."
        } else {
            "I wish it were colder."
        }
    }
}
