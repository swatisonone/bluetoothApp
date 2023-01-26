package com.example.bluetoothApp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import com.example.bluetoothapp.R

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ControlActivity.m_address = intent.getStringExtra(blutoothActivity.EXTRA_ADDRESS).toString()
        ControlActivity.ConnectToDevice(this).execute()
        fun theme(i:Int){
            if (i==5){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                setTheme(android.R.style.Theme);
                Log.d("theme", "theme: dark")
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setTheme(android.R.style.Theme);
                Log.d("theme", "theme: light")

            }

        }
        setContentView(R.layout.activity_main)

//        var switchBtn = findViewById<Switch>(R.id.mode)
        var mannualControll = findViewById<Button>(R.id.mannualControll)
        var humanDetection = findViewById<Button>(R.id.humanDetection)
        var colorDetection = findViewById<Button>(R.id.colorDetection)
        var ocr = findViewById<Button>(R.id.ocr)
        var image = findViewById<ImageView>(R.id.imageView)
//        var dark = findViewById<Button>(R.id.button)
//        var light = findViewById<Button>(R.id.button2)



        humanDetection.setOnClickListener {
            var intent = Intent(this, faceDetectionActivity::class.java)
            startActivity((intent))
        }

        mannualControll.setOnClickListener {
            var intent = Intent(this, ControlActivity::class.java)
            startActivity((intent))
        }
        ocr.setOnClickListener {
            var intent = Intent(this, ocrActivity::class.java)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            startActivity((intent))
        }
        colorDetection.setOnClickListener {
            var intent = Intent(this, colorDetectionActivity::class.java)
            startActivity((intent))
        }
//        dark.setOnClickListener{
//            theme(5)
////                image.setImageDrawable(black_trf)
////            switchBtn.setText("Dark Mode disnable")
//        }
//        light.setOnClickListener{
//            theme(6)
////            switchBtn.setText("Dark Mode enable")
//        }

//        switchBtn.setOnCheckedChangeListener { _, isChecked ->
//            if (switchBtn.isChecked) {
////                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                theme(5)
////                image.setImageDrawable(black_trf)
//                switchBtn.setText("Dark Mode disnable")
//
//            } else {
////                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
////                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                theme(6)
//                switchBtn.setText("Dark Mode enable")
//            }
////
//        }

    }

    }




