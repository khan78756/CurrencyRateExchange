package com.example.currencyrate.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.currencyrate.R
import com.example.currencyrate.admob.appopen.SplashOpenApp
import com.example.currencyrate.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        SplashOpenApp(this@SplashActivity).fetchAd {
            if(it){
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            }else{
                binding.btnStart.visibility = View.VISIBLE
            }
        }
        binding.setListener()
    }
    private fun ActivitySplashBinding.setListener(){
        btnStart.setOnClickListener {
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            finish()
        }
    }
}