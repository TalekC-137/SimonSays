package com.scollon.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var activationSpeed: Long = 500 // speed at which the blocks will disapear
    var seq_pos = 0 // number of rounds played
    var rounds = 1
    var seq = listOf<Int>()
    private val bloczki by lazy { arrayOf(
        findViewById<Button>(R.id.button), findViewById(R.id.button2),
        findViewById(R.id.button3),findViewById(R.id.button4)
    ) }
    lateinit var adViewTop : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adViewTop = findViewById(R.id.adViewTop)
        val adRequest = AdRequest.Builder().build()
        adViewTop.loadAd(adRequest)


        button.setBackgroundResource(R.drawable.blue)
        button2.setBackgroundResource(R.drawable.blue)
        button3.setBackgroundResource(R.drawable.blue)
        button4.setBackgroundResource(R.drawable.blue)
        button.isClickable = false
        button2.isClickable = false
        button3.isClickable = false
        button4.isClickable = false

        for(i in 0..100){
            seq += (1..4).random()
        }

        btn_run.setOnClickListener(){
         seqShow()
            btn_run.visibility  = View.GONE
        }

        btn_again.setOnClickListener(){
            finish()
            startActivity(getIntent())
            overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        }

        btn_save.setOnClickListener(){
            addRecord()
        }


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, seq)
        lista.adapter = adapter

    }

    fun klik(view: View){
        activationSpeed = 250
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
                   // Toast.makeText(this, "brawo", Toast.LENGTH_LONG).show()
                    seq_pos =0
                   rounds++
                    seqShow()
                }

            } else {
                // player made a mistake
               // Toast.makeText(this, "better luck next time", Toast.LENGTH_LONG).show()
                var beatenScore = rounds-1
                tv_score.text = beatenScore.toString()
               cimno.visibility = View.VISIBLE
                end_game.visibility = View.VISIBLE
                lastRecord()
                highScore()
            }



    }

    fun seqShow(){
        activationSpeed = 500
        button.isClickable = false
        button2.isClickable = false
        button3.isClickable = false
        button4.isClickable = false

    tv_rounds.text = (rounds-1).toString()
            CoroutineScope(Dispatchers.IO).launch    {
                for (i in 0..rounds-1){   // it only shows the sequence to the set number of round that is currently being played
                    delay(TimeUnit.SECONDS.toMillis(1))
                withContext(Dispatchers.Main) {
                  // this is called after 1 sec
                    btnShow(seq[i])
                    if(i == rounds-1){
                        CoroutineScope(Dispatchers.IO).launch    {
                            delay(TimeUnit.MILLISECONDS.toMillis(500))
                            withContext(Dispatchers.Main) {
                                // this is called after 0.5 sec
                                button.isClickable = true
                                button2.isClickable = true
                                button3.isClickable = true
                                button4.isClickable = true
                            }
                        }
                    }
                }
            }
        }
    }

fun btnShow(btn: Int){

    for (i in bloczki.indices){


        if(i==btn-1) {
            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.MILLISECONDS.toMillis(activationSpeed))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    bloczki[i].setBackgroundResource(R.drawable.blue);

                }
            }
            bloczki[i].setBackgroundResource(R.drawable.white);

        }
    }

    //told ya I can loop it
/*
    if(btn == 1){
        CoroutineScope(Dispatchers.IO).launch    {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            withContext(Dispatchers.Main) {
                // this is called after 3 secs
                button.setBackgroundResource(R.drawable.blue);

            }
        }
        button.setBackgroundResource(R.drawable.white);
    }else if(btn == 2){
        CoroutineScope(Dispatchers.IO).launch    {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            withContext(Dispatchers.Main) {
                // this is called after 3 secs
                button2.setBackgroundResource(R.drawable.blue);

            }
        }
        button2.setBackgroundResource(R.drawable.white);
    }else if(btn == 3){

        CoroutineScope(Dispatchers.IO).launch    {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            withContext(Dispatchers.Main) {
                // this is called after 3 secs
                button3.setBackgroundResource(R.drawable.blue);

            }
        }
        button3.setBackgroundResource(R.drawable.white);
    }else if(btn == 4){

        CoroutineScope(Dispatchers.IO).launch    {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            withContext(Dispatchers.Main) {
                // this is called after 3 secs
                button4.setBackgroundResource(R.drawable.blue);
            }
        }
        button4.setBackgroundResource(R.drawable.white);
    }else{
        Toast.makeText(this, "idk how you did this but you broke the game", Toast.LENGTH_LONG).show()
    }
*/

 }

    private fun addRecord() {
        val score = tv_score.text.toString()
        val scoreInt = Integer.parseInt(score)
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if (!score.isEmpty()) {
            val status =
                databaseHandler.addEmployee(RecordModel(0, scoreInt))
            if (status > -1) {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "you messed up fool",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private fun getItemsList(): ArrayList<RecordModel> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val empList: ArrayList<RecordModel> = databaseHandler.viewEmployee()

        return empList
    }

    private fun lastRecord() {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)


        if(databaseHandler.getBmiCount() != 0){

            var RecordNum = databaseHandler.getBmiCount()

            var modelik: RecordModel? = databaseHandler.getOne(RecordNum)

         var a:Int = modelik?.score ?: 0

            tv_lastScoreView.text = a.toString()

        }


    }

    private fun highScore(){
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if(databaseHandler.getBmiCount() != 0){

            var rekord = databaseHandler.getBiggestInTheColumn()
            tv_HighscoreView.text = rekord.toString()
        }

    }


}