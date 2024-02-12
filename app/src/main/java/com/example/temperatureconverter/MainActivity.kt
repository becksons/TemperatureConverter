package com.example.temperatureconverter

import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

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
        val tempGifImageView = findViewById<ImageView>(R.id.tempGif)
        Glide.with(this)
            .asGif()
            .load(R.drawable.freezing_emoji) // Replace this with your GIF URL
            .into(tempGifImageView)

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
        val lowerBound = 40 // 10 degrees below the midpoint
        val upperBound = 60 // 10 degrees above the midpoint

        interestingMessageTextView.text = when {
            celsius < lowerBound -> "I wish it were warmer."
            celsius > upperBound -> "I wish it were colder."
            else -> "Just right"
        }
        updateTemperatureGif(celsius)
    }

    private fun updateTemperatureGif(celsius: Int) {
        val gifResource = when {
            celsius <= 0 -> R.drawable.freezing_emoji
            celsius in 1..10 -> R.drawable.freezing_emoji
            celsius in 11 .. 30 -> R.drawable.happy_emoji
            else -> R.drawable.sweaty_gif
        }

        Glide.with(this)
            .asGif()
            .load(gifResource)
            .into(findViewById(R.id.tempGif))
    }
}

