package com.scollon.simon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_the_main.*

class TheMain : AppCompatActivity() {

    lateinit var adViewTopMain : AdView
    lateinit var adViewBottom : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_main)

        MobileAds.initialize(this) {}

        adViewTopMain = findViewById(R.id.adViewTopMain)
        val adRequest = AdRequest.Builder().build()
        adViewTopMain.loadAd(adRequest)

        adViewBottom = findViewById(R.id.adViewBottom)
        val adRequest2 = AdRequest.Builder().build()
        adViewBottom.loadAd(adRequest2)


        btn_2x2.setOnClickListener(){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        btn_3x3.setOnClickListener(){
            val i = Intent(this, Activity3x3::class.java)
            startActivity(i)
        }
        btn_4x4.setOnClickListener(){
            val i = Intent(this, Activity4x4::class.java)
            startActivity(i)
        }

    }
}