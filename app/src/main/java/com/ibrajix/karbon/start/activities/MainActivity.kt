package com.ibrajix.karbon.start.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.ibrajix.core.utils.Utility.SPLASH_SCREEN_TIME
import com.ibrajix.core.utils.Utility.whiteStatusBar
import com.ibrajix.karbon.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        whiteStatusBar()
        initView()
    }

    private fun initView(){
        startSplashAnimation()
        goToNextActivity()
    }

    private fun startSplashAnimation(){

        YoYo.with(Techniques.RotateInDownLeft)
            .duration(3000)
            .repeat(0)
            .playOn(binding.imgCarbonLogo)

    }

    private fun goToNextActivity(){

        //delay for 5 seconds and move to next activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ContainerActivity::class.java)
            startActivity(intent)
            finish()}, SPLASH_SCREEN_TIME)

    }

}