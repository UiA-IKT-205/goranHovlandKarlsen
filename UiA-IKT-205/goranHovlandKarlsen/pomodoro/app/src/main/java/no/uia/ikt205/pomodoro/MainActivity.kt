package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var countdownDisplay:TextView

    lateinit var timer30:Button
    lateinit var timer60:Button
    lateinit var timer90:Button
    lateinit var timer120:Button
    private lateinit var stopButton:Button
    var currentlyRunning:Boolean = false

    var timeToCountDownInMs = 0L
    val timeTicks = 1000L
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seek = findViewById<SeekBar>(R.id.seekBar_15_300)
        seek?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                findViewById<TextView>(R.id.seekbar_textview).text = progress.toString()
                val timeInMs = seek.progress * 60 * 1000
                if (!currentlyRunning) {
                    timeToCountDownInMs = timeInMs.toLong()
                    updateCountDownDisplay(timeToCountDownInMs)
                }

            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                Toast.makeText(this@MainActivity,"Endrer arbeidstid", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                val timeInMs = seek.progress * 60 * 1000
                if (!currentlyRunning) {
                    timeToCountDownInMs = timeInMs.toLong()
                    updateCountDownDisplay(timeToCountDownInMs)
                }
            }
        })

        val pause_seek = findViewById<SeekBar>(R.id.pause_seekBar)
        pause_seek?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(pause_seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                findViewById<TextView>(R.id.pause_textview).text = progress.toString()
                val pausetidInMs = pause_seek.progress * 60 * 1000
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                Toast.makeText(this@MainActivity,"Endrer pausetid", Toast.LENGTH_SHORT).show()
                val pausetidInMs = pause_seek.progress * 60 * 1000
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                val pausetidInMs = pause_seek.progress * 60 * 1000
            }
        })

        val repetitions = findViewById<EditText>(R.id.editRepetitions).text.toString().toIntOrNull()
        if (repetitions != null){
            for (x in 1..repetitions){
                timeToCountDownInMs = seek.progress.toLong()
                updateCountDownDisplay(timeToCountDownInMs)
                if (timeToCountDownInMs == 0L){
                    Toast.makeText(this@MainActivity,"Arbeidsøkt ferdig", Toast.LENGTH_SHORT).show()
                }

                timeToCountDownInMs = pause_seek.progress.toLong()
                updateCountDownDisplay(timeToCountDownInMs)
                if (timeToCountDownInMs == 0L){
                    Toast.makeText(this@MainActivity,"Pause ferdig", Toast.LENGTH_SHORT).show()
                }
            }
        }


        startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           if (!currentlyRunning) {
               startCountDown(it)
               currentlyRunning = true
           }
       }
       countdownDisplay = findViewById<TextView>(R.id.countDownView)


        timer30 = findViewById(R.id.timer30)
        timer30.setOnClickListener(){
            if (!currentlyRunning) {
                timeToCountDownInMs = 1800000L
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }

        timer60 = findViewById(R.id.timer60)
        timer60.setOnClickListener(){
            if (!currentlyRunning) {
                timeToCountDownInMs = 3600000L
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }

        timer90 = findViewById(R.id.timer90)
        timer90.setOnClickListener(){
            if (!currentlyRunning) {
                timeToCountDownInMs = 5400000L
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }

        timer120 = findViewById(R.id.timer120)
        timer120.setOnClickListener() {
            if (!currentlyRunning) {
                timeToCountDownInMs = 7200000L
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }

        stopButton = findViewById(R.id.stopCountdownButton)
        stopButton.setOnClickListener {
            if (currentlyRunning) {
                timeToCountDownInMs = 0L
                updateCountDownDisplay(timeToCountDownInMs)
                currentlyRunning = false
                timer.cancel()
            }

            // makes it possible to set timer to zero without starting countdown
            else {
                timeToCountDownInMs = 0L
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }
    }

    fun startCountDown(v: View){
        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }
}