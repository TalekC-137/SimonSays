package com.scollon.simon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_the_main.*

class TheMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_main)


        btn_2x2.setOnClickListener(){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        btn_3x3.setOnClickListener(){
            val i = Intent(this, Activity3x3::class.java)
            startActivity(i)
        }

    }
}