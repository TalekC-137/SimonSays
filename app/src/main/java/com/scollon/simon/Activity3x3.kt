package com.scollon.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_activity4x4.*
import kotlinx.android.synthetic.main.activity_main.*
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

class Activity3x3 : AppCompatActivity() {


    var seq_pos = 0 // number of rounds played
    var rounds = 1
    var seq = listOf<Int>()

    private val bloczki by lazy { arrayOf(
        findViewById<Button>(R.id.btn1), findViewById(R.id.btn2),
        findViewById(R.id.btn3),findViewById(R.id.btn4),
        findViewById(R.id.btn5),findViewById(R.id.btn6),
        findViewById(R.id.btn7),findViewById(R.id.btn8),
        findViewById(R.id.btn9),
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity4x4)
        btn1.setBackgroundResource(R.drawable.blue)

        basicColor()
        clickableFalse()

        for(i in 0..100){
            seq += (1..9).random()
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

            R.id.btn1 -> getButton(1)
            R.id.btn2 -> getButton(2)
            R.id.btn3 -> getButton(3)
            R.id.btn4 -> getButton(4)
            R.id.btn5 -> getButton(5)
            R.id.btn6 -> getButton(6)
            R.id.btn7 -> getButton(7)
            R.id.btn8 -> getButton(8)
            R.id.btn9 -> getButton(9)

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

        if(btn == 1){
            CoroutineScope(Dispatchers.IO).launch    {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    btn1.setBackgroundResource(R.drawable.blue);

                }
            }
            btn1.setBackgroundResource(R.drawable.white);
        }else if(btn == 2){
            CoroutineScope(Dispatchers.IO).launch    {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    btn2.setBackgroundResource(R.drawable.blue);

                }
            }
            btn2.setBackgroundResource(R.drawable.white);
        }else if(btn == 3){

            CoroutineScope(Dispatchers.IO).launch    {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    btn3.setBackgroundResource(R.drawable.blue);

                }
            }
            btn3.setBackgroundResource(R.drawable.white);
        }else if(btn == 4) {

            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    btn4.setBackgroundResource(R.drawable.blue);
                }
            }
            btn4.setBackgroundResource(R.drawable.white);
        }else if(btn == 5) {

            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    btn5.setBackgroundResource(R.drawable.blue);
                }
            }
            btn5.setBackgroundResource(R.drawable.white);
        }else if(btn == 6) {

            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    btn6.setBackgroundResource(R.drawable.blue);
                }
            }
            btn6.setBackgroundResource(R.drawable.white);
        }else if(btn == 7) {

            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    btn7.setBackgroundResource(R.drawable.blue);
                }
            }
            btn7.setBackgroundResource(R.drawable.white);
        }else if(btn == 8) {

            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                withContext(Dispatchers.Main) {
                    // this is called after 3 secs
                    btn8.setBackgroundResource(R.drawable.blue);
                }
            }
            btn8.setBackgroundResource(R.drawable.white);
        }else if(btn == 9){

                                CoroutineScope(Dispatchers.IO).launch    {
                                    delay(TimeUnit.MILLISECONDS.toMillis(500))
                                    withContext(Dispatchers.Main) {
                                        // this is called after 3 secs
                                        btn9.setBackgroundResource(R.drawable.blue);
                                    }
                                }
                                btn9.setBackgroundResource(R.drawable.white);
        }else{
            Toast.makeText(this, "idk how you did this but you broke the game", Toast.LENGTH_LONG).show()
        }

    }

    private fun addRecord() {
        val score = tv_score.text.toString()
        val scoreInt = Integer.parseInt(score)
        val databaseHandler3: DatabaseHandler3 = DatabaseHandler3(this)
        if (!score.isEmpty()) {
            val status =
                databaseHandler3.addEmployee(RecordModel(0, scoreInt))
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
        //creating the instance of DatabaseHandler class
        val databaseHandler3: DatabaseHandler3 = DatabaseHandler3(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val empList: ArrayList<RecordModel> = databaseHandler3.viewEmployee()

        return empList
    }

    private fun lastRecord() {
        val databaseHandler3: DatabaseHandler3 = DatabaseHandler3(this)


        if(databaseHandler3.getBmiCount() != 0){

            var RecordNum = databaseHandler3.getBmiCount()

            var modelik: RecordModel? = databaseHandler3.getOne(RecordNum)

            var a:Int = modelik?.score ?: 0

            tv_lastScoreView.text = a.toString()

        }


    }

    private fun highScore(){
        val databaseHandler3: DatabaseHandler3 = DatabaseHandler3(this)
        if(databaseHandler3.getBmiCount() != 0){

            var rekord = databaseHandler3.getBiggestInTheColumn()
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