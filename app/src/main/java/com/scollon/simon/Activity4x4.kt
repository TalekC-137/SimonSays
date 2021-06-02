package com.scollon.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_activity3x3.*
import kotlinx.android.synthetic.main.activity_activity4x4.*
import kotlinx.android.synthetic.main.activity_main.btn_again
import kotlinx.android.synthetic.main.activity_main.btn_run
import kotlinx.android.synthetic.main.activity_main.btn_save
import kotlinx.android.synthetic.main.activity_main.cimno
import kotlinx.android.synthetic.main.activity_main.end_game
import kotlinx.android.synthetic.main.activity_main.lista
import kotlinx.android.synthetic.main.activity_main.tv_HighscoreView
import kotlinx.android.synthetic.main.activity_main.tv_lastScoreView
import kotlinx.android.synthetic.main.activity_main.tv_rounds
import kotlinx.android.synthetic.main.activity_main.tv_score
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class Activity4x4 : AppCompatActivity() {


    var seq_pos = 0 // number of rounds played
    var rounds = 1
    var seq = listOf<Int>()

    private val bloczki by lazy { arrayOf(
        findViewById<Button>(R.id.button), findViewById(R.id.button2),
        findViewById(R.id.button3),findViewById(R.id.button4),
        findViewById(R.id.button5),findViewById(R.id.button6),
        findViewById(R.id.button7),findViewById(R.id.button8),
        findViewById(R.id.button9),findViewById(R.id.button10),
        findViewById(R.id.button11),findViewById(R.id.button12),
        findViewById(R.id.button13),findViewById(R.id.button14),
        findViewById(R.id.button15),findViewById(R.id.button16)
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity4x4)

        basicColor()
        clickableFalse()

        for(i in 0..100){
            seq += (1..16).random()
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

        when(view.id){

            R.id.button -> getButton(1)
            R.id.button2 -> getButton(2)
            R.id.button3 -> getButton(3)
            R.id.button4 -> getButton(4)
            R.id.button5 -> getButton(5)
            R.id.button6 -> getButton(6)
            R.id.button7 -> getButton(7)
            R.id.button8 -> getButton(8)
            R.id.button9 -> getButton(9)
            R.id.button10 -> getButton(10)
            R.id.button11 -> getButton(11)
            R.id.button12 -> getButton(12)
            R.id.button13 -> getButton(13)
            R.id.button14 -> getButton(14)
            R.id.button15 -> getButton(15)
            R.id.button16 -> getButton(16)

        }

    }

    fun getButton(btn_pushed: Int){



//player got it right
        if (seq[seq_pos] == btn_pushed) {
            seq_pos++
            btnShow(btn_pushed)

            if(seq_pos == rounds){
                //player got the whole sequence right
                //    Toast.makeText(this, "brawo", Toast.LENGTH_LONG).show()
                seq_pos =0
                rounds++
                seqShow()
            }

        } else {
            // player made a mistake
            //  Toast.makeText(this, "źle", Toast.LENGTH_LONG).show()
            var beatenScore = rounds-1
            tv_score.text = beatenScore.toString()
            cimno.visibility = View.VISIBLE
            end_game.visibility = View.VISIBLE
            lastRecord()
            highScore()
        }



    }

    fun seqShow(){

        clickableFalse()

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
                                // this is called after 3 secs
                                clickableTrue()
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
                    delay(TimeUnit.MILLISECONDS.toMillis(500))
                    withContext(Dispatchers.Main) {
                        // this is called after 3 secs
                        bloczki[i].setBackgroundResource(R.drawable.blue);

                    }
                }
                bloczki[i].setBackgroundResource(R.drawable.white);

            }
        }




    }

    private fun addRecord() {
        val score = tv_score.text.toString()
        val scoreInt = Integer.parseInt(score)
        val databaseHandler4: DatabaseHandler4 = DatabaseHandler4(this)
        if (!score.isEmpty()) {
            val status =
                databaseHandler4.addEmployee(RecordModel(0, scoreInt))
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
        val databaseHandler4: DatabaseHandler4 = DatabaseHandler4(this)
        val empList: ArrayList<RecordModel> = databaseHandler4.viewEmployee()

        return empList
    }

    private fun lastRecord() {
        val databaseHandler4: DatabaseHandler4 = DatabaseHandler4(this)


        if(databaseHandler4.getBmiCount() != 0){

            var RecordNum = databaseHandler4.getBmiCount()

            var modelik: RecordModel? = databaseHandler4.getOne(RecordNum)

            var a:Int = modelik?.score ?: 0

            tv_lastScoreView.text = a.toString()

        }


    }

    private fun highScore(){
        val databaseHandler4: DatabaseHandler4 = DatabaseHandler4(this)
        if(databaseHandler4.getBmiCount() != 0){

            var rekord = databaseHandler4.getBiggestInTheColumn()
            tv_HighscoreView.text = rekord.toString()
        }

    }

    fun basicColor(){

        for(i in bloczki.indices) {
            bloczki[i].setBackgroundResource(R.drawable.blue)
        }
    }
    fun clickableFalse(){

        for(i in bloczki.indices) {
            bloczki[i].isClickable = false
        }
    }
    fun clickableTrue(){
        for(i in bloczki.indices) {
            bloczki[i].isClickable = true
        }
    }


}