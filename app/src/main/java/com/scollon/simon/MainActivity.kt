package com.scollon.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var seq_pos = 0
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
        }


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, seq)
        lista.adapter = adapter



    }

    fun klik(view: View){

        when(view.id){

            R.id.button -> Toast.makeText(this, "L G", Toast.LENGTH_LONG).show()
            R.id.button2 -> Toast.makeText(this, "P G", Toast.LENGTH_LONG).show()
            R.id.button3 -> Toast.makeText(this, "L D", Toast.LENGTH_LONG).show()
            R.id.button4 -> Toast.makeText(this, "P D", Toast.LENGTH_LONG).show()

        }

    }

    fun getButton(btn_pushed: Int){

        for(i in 0..seq_pos){
            if(seq[seq_pos] == btn_pushed){
                seq_pos++
                //poka guziora
            }else{
                Toast.makeText(this, "źle", Toast.LENGTH_LONG).show()

            }

        }

    }

    fun seqShow(){

            CoroutineScope(Dispatchers.IO).launch    {
                for (i in 0..seq.size-1){
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