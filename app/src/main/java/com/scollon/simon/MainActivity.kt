package com.scollon.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var seq_pos = 0 // number of rounds played
    var rounds = 1
    var seq = listOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setBackgroundResource(R.drawable.kafelek);
        button2.setBackgroundResource(R.drawable.kafelek);
        button3.setBackgroundResource(R.drawable.kafelek);
        button4.setBackgroundResource(R.drawable.kafelek);


        for(i in 0..10){
            seq += (1..4).random()
        }

        btn_run.setOnClickListener(){
         seqShow()
            btn_run.visibility  = View.GONE
        }


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, seq)
        lista.adapter = adapter







    }

    fun klik(view: View){

        when(view.id){

            R.id.button -> getButton(1)
            R.id.button2 -> getButton(2)
            R.id.button3 -> getButton(3)
            R.id.button4 -> getButton(4)

        }

    }

    fun getButton(btn_pushed: Int){



//player got it right
            if (seq[seq_pos] == btn_pushed) {
                seq_pos++
                btnShow(btn_pushed)

                if(seq_pos == rounds){
                    //player got the whole sequence right
                    Toast.makeText(this, "brawo", Toast.LENGTH_LONG).show()
                    seq_pos =0
                   rounds++
                    seqShow()
                }

            } else {
                // player made a mistake
                Toast.makeText(this, "źle", Toast.LENGTH_LONG).show()
            }



    }

    fun seqShow(){
    tv_rounds.text = rounds.toString()
            CoroutineScope(Dispatchers.IO).launch    {
                for (i in 0..rounds-1){   // it only shows the sequence to the set number of round that is currently being played
                    delay(TimeUnit.SECONDS.toMillis(1))
                withContext(Dispatchers.Main) {
                  // this is called after 3 secs
                    btnShow(seq[i])
                }
            }
        //    this is called immediately

        }

    }

fun btnShow(btn: Int){

    if(btn == 1){
        CoroutineScope(Dispatchers.IO).launch    {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            withContext(Dispatchers.Main) {
                // this is called after 3 secs
                button.setBackgroundResource(R.drawable.kafelek);

            }
        }
        button.setBackgroundResource(R.drawable.kafelek2);
    }else if(btn == 2){
        CoroutineScope(Dispatchers.IO).launch    {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            withContext(Dispatchers.Main) {
                // this is called after 3 secs
                button2.setBackgroundResource(R.drawable.kafelek);

            }
        }
        button2.setBackgroundResource(R.drawable.kafelek2);
    }else if(btn == 3){

        CoroutineScope(Dispatchers.IO).launch    {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            withContext(Dispatchers.Main) {
                // this is called after 3 secs
                button3.setBackgroundResource(R.drawable.kafelek);

            }
        }
        button3.setBackgroundResource(R.drawable.kafelek2);
    }else if(btn == 4){

        CoroutineScope(Dispatchers.IO).launch    {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            withContext(Dispatchers.Main) {
                // this is called after 3 secs
                button4.setBackgroundResource(R.drawable.kafelek);
            }
        }
        button4.setBackgroundResource(R.drawable.kafelek2);
    }else{
        Toast.makeText(this, "idk how you did this but you broke the game", Toast.LENGTH_LONG).show()
    }

 }

}