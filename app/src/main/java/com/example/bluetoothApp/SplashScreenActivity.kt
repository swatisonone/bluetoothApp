package com.example.bluetoothApp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.bluetoothapp.R

class SplashScreenActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val backgroundImg : ImageView = findViewById(R.id.iv_logo)
        val text1 : TextView = findViewById(R.id.text1)
        val text2: TextView = findViewById(R.id.text2)
        val text3 : TextView = findViewById(R.id.text3)
        val sideAnimation = AnimationUtils.loadAnimation(this,R.anim.slide)
        val sideAnimation1 = AnimationUtils.loadAnimation(this,R.anim.slide1)
        val sideAnimation2 = AnimationUtils.loadAnimation(this,R.anim.slide2)
        val sideAnimation3 = AnimationUtils.loadAnimation(this,R.anim.slide3)

        backgroundImg.startAnimation(sideAnimation)
        Handler().postDelayed({
            startActivity(Intent(this,blutoothActivity::class.java))
            finish()
        },3000)
        text1.startAnimation(sideAnimation)
        Handler().postDelayed({
            startActivity(Intent(this,blutoothActivity::class.java))
            finish()
        },3000)
        text2.startAnimation(sideAnimation2)
        Handler().postDelayed({
            startActivity(Intent(this,blutoothActivity::class.java))
            finish()
        },3000)
        text3.startAnimation(sideAnimation3)
        Handler().postDelayed({
            startActivity(Intent(this,blutoothActivity::class.java))
            finish()
        },3000)
    }
}